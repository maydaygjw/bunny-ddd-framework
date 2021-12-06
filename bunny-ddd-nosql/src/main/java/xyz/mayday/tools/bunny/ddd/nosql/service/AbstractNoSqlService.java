package xyz.mayday.tools.bunny.ddd.nosql.service;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Setter;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService;
import xyz.mayday.tools.bunny.ddd.nosql.query.QueryPredicate;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

public abstract class AbstractNoSqlService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> {
    
    @Setter
    MongoTemplate mongoTemplate;
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return Optional.ofNullable(mongoTemplate.findById(id, getDaoClass())).map(this::convertToDto);
    }
    
    @Override
    public List<DTO> findItemsByIds(List<ID> id) {
        return null;
    }
    
    @Override
    public PageableData<DTO> doFindItems(DTO example, CommonQueryParam queryParam) {
        List<SearchCriteria> searchCriteriaList = buildQuerySpecification(example).getSearchCriteriaList();
        Query query = new Query();
        searchCriteriaList.forEach(searchCriteria -> {
            query.addCriteria(QueryPredicate.presetPredicates.get(searchCriteria.getSearchOperation()).buildCriteria(searchCriteria));
        });
        List<DTO> collect = mongoTemplate.find(query, getDaoClass()).stream().map(this::convertToDto).collect(Collectors.toList());
        return PageableData.<DTO> builder().records(collect).build();
        
    }
    
    @Override
    public Long countItems(DTO example) {
        return null;
    }
    
    @Override
    public List<DTO> findAll(DTO example) {
        return null;
    }
    
    @Override
    public Stream<DTO> findStream(DTO example) {
        return null;
    }
    
    @Override
    public DTO insert(DTO dto) {
        ID id = convertIdType(idGenerator.generate());
        dto.setId(id);
        DAO tbSave = convertToDao(dto);
        auditWhenInsert(tbSave);
        DAO inserted = mongoTemplate.insert(tbSave);
        historyService.commit(inserted);
        return convertToDto(inserted);
    }
    
    @Override
    public List<DTO> bulkInsert(List<DTO> dtos) {
        return null;
    }
    
    @Override
    public DTO update(DTO dto) {
        Objects.requireNonNull(dto.getId());
        DAO tbUpdate = convertToDao(dto);
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(dto.getId()));
        DAO dbObject = mongoTemplate.findOne(query, getDaoClass());
        if (Objects.isNull(dbObject)) {
            throw new BusinessException();
        }
        mergeProperties(tbUpdate, dbObject);
        auditWhenUpdate(dbObject);
        DAO saved = mongoTemplate.save(dbObject);
        historyService.commit(saved);
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
    
    @Override
    public DTO delete(ID id) {
        DAO dao = mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("id").is(id)), getDaoClass());
        historyService.delete(id, getDaoClass());
        return convertToDto(dao);
    }
    
    @Override
    public List<DTO> bulkDeleteById(List<ID> ids) {
        return null;
    }
    
    @Override
    public List<DTO> deleteAll() {
        return null;
    }
}
