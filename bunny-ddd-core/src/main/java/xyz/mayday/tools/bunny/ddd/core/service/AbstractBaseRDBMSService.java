package xyz.mayday.tools.bunny.ddd.core.service;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;
import lombok.Setter;

import org.javers.core.Javers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.history.Revisions;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.transaction.annotation.Transactional;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDAO;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.query.QuerySpecification;
import xyz.mayday.tools.bunny.ddd.core.query.visit.BaseQuerySpecVisitor;
import xyz.mayday.tools.bunny.ddd.core.query.visit.ExtraCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.query.visit.FieldCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.query.visit.MultipleValueCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.utils.QueryUtils;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.page.PageInfo;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import com.google.common.collect.Lists;

/** @author gejunwen */
@NoArgsConstructor
@Setter
public abstract class AbstractBaseRDBMSService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends AbstractBaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> {
    
    @Autowired(required = false)
    PersistenceServiceFactory serviceFactory;
    
    @Autowired(required = false)
    IdGenerator<String> idGenerator;
    
    @Autowired
    Javers javers;
    
    public AbstractBaseRDBMSService(GenericConverter converter, PrincipalService principalService, PersistenceServiceFactory serviceFactory,
            IdGenerator<String> idGenerator, GenericConverter genericConverter) {
        super(converter, principalService);
        this.serviceFactory = serviceFactory;
        this.idGenerator = idGenerator;
    }
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return getRepository().findById(id).map(this::convertToDto);
    }
    
    @Override
    public List<DTO> findItemsByIds(List<ID> id) {
        return getRepository().findAllById(id).stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    @Override
    public PageableData<DTO> findItems(DTO dto, CommonQueryParam queryParam) {
        dto = Optional.ofNullable(dto).orElse(ReflectionUtils.newInstance(getDtoClass()));
        
        QuerySpecification<DAO> querySpecification = buildQuerySpecification(dto);
        
        Page<DAO> pageResult = serviceFactory.getRepository(getDaoClass()).findAll(querySpecification, QueryUtils.buildPageRequest(queryParam));
        return PageableData.<DTO> builder().pageInfo(PageInfo.fromPage(pageResult))
                .records(pageResult.get().map(this::convertToDto).collect(Collectors.toList())).build();
    }
    
    private QuerySpecification<DAO> buildQuerySpecification(DTO dto) {
        QuerySpecification<DAO> querySpecification = new QuerySpecification<>();
        Collection<BaseQuerySpecVisitor> visitors = Lists.newArrayList(new FieldCriteriaVisitorImpl(), new MultipleValueCriteriaVisitorImpl(),
                new ExtraCriteriaVisitorImpl());
        visitors.forEach(dto::accept);
        visitors.forEach(visitor -> querySpecification.addAll(visitor.getSearchCriteria()));
        return querySpecification;
    }
    
    @Override
    public Long countItems(DTO example) {
        return getRepository().count(buildQuerySpecification(example));
    }
    
    @Override
    public List<DTO> findAll(DTO example) {
        return findItems(example, new CommonQueryParam().withPageSize(Integer.MAX_VALUE)).getRecords();
    }
    
    @Override
    public List<DTO> findHistoriesById(ID id) {
        Revisions<Integer, DAO> revisions = serviceFactory.getRevisionRepository(getDaoClass()).findRevisions(id);
        return revisions.getContent().stream().map(revision -> {
            DTO dto = convertToDto(revision.getEntity());
            dto.setRevision(revision.getRevisionNumber().orElse(0));
            dto.setOperationType(revision.getMetadata().getRevisionType().name());
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public Stream<DTO> findStream(DTO example) {
        return null;
    }
    
    @Transactional
    @Override
    public DTO insert(DTO dto) {
        DAO dao = convertToDao(dto);
        dao.setId(convertIdType(idGenerator.generate()));
        auditWhenInsert(dao);
        DAO saved = getRepository().save(dao);
        javers.commit(saved.getCreatedBy(), saved);
        return convertToDto(saved);
    }
    
    @Override
    public List<DTO> bulkInsert(List<DTO> dtos) {
        return null;
    }
    
    @Transactional
    @Override
    public DTO update(DTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw new BusinessException();
        }
        DAO inputOne = convertToDao(dto);
        DAO dbOne = getRepository().findById(dto.getId()).orElseThrow(BusinessException::new);
        BeanUtils.copyProperties(inputOne, dbOne);
        auditWhenUpdate(dbOne);
        DAO saved = getRepository().save(dbOne);
        javers.commit(saved.getUpdatedBy(), saved);
        return convertToDto(saved);
    }
    
    @Override
    public List<DTO> bulkUpdate(List<DTO> dtos) {
        return null;
    }
    
    @Override
    public DTO save(DTO dto) {
        return null;
    }
    
    @Transactional
    @Override
    public DTO delete(ID id) {
        DAO dao = getRepository().findById(id).orElseThrow(BusinessException::new);
        getRepository().deleteById(id);
        return convertToDto(dao);
    }
    
    @Transactional
    @Override
    public List<DTO> bulkDeleteById(List<ID> ids) {
        
        // List<DTO> itemsToBeDelete = findItemsByIds(ids);
        // Set<Attribute<? super DAO, ?>> attributes =
        // serviceFactory.getEntityManager().getMetamodel().entity(getDaoClass()).getAttributes();
        
        return Collections.emptyList();
    }
    
    @Transactional
    @Override
    public List<DTO> deleteAll() {
        List<DAO> all = getRepository().findAll();
        getRepository().deleteAll();
        return all.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    
    protected JpaRepositoryImplementation<DAO, ID> getRepository() {
        return serviceFactory.getRepository(getDaoClass());
    }
}
