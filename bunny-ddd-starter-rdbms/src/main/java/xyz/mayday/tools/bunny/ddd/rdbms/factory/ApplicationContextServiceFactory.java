package xyz.mayday.tools.bunny.ddd.rdbms.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.rpc.RpcClientSpec;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

@RequiredArgsConstructor
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ApplicationContextServiceFactory implements PersistenceServiceFactory {
    
    final EntityManager entityManager;
    
    final ApplicationContext ctx;
    
    final JdbcTemplate jdbcTemplate;
    
    Map<Class<? extends BaseDAO<?>>, JpaRepositoryImplementation<? extends BaseDAO<?>, ?>> repositoryMap = new ConcurrentHashMap<>();
    
    @Override
    public <ID, DTO, DAO> JpaRepositoryImplementation<DAO, ID> getRepository(DAO dao) {
        return null;
    }
    
    @Override
    public <ID, DTO, DAO extends BaseDAO<ID>> JpaRepositoryImplementation<DAO, ID> getRepository(Class<DAO> daoCls) {
        
        String domainName = ReflectionUtils.newInstance(daoCls).getDomainName();
        
        if (!repositoryMap.containsKey(daoCls)) {
            Map<String, JpaRepositoryImplementation> beans = ctx.getBeansOfType(JpaRepositoryImplementation.class);
            JpaRepositoryImplementation repo = beans.entrySet().stream()
                    .filter(entry -> StringUtils.substringBefore(entry.getKey(), "Repository").equals(domainName)).map(Map.Entry::getValue).findFirst()
                    .orElse(new SimpleJpaRepository<>(daoCls, entityManager));
            repositoryMap.put(daoCls, repo);
        }
        return (JpaRepositoryImplementation<DAO, ID>) repositoryMap.get(daoCls);
    }
    
    @Override
    public <ID, DTO, DAO extends BaseDAO<ID>> RevisionRepository<DAO, ID, Integer> getRevisionRepository(Class<DAO> daoCls) {
        return null;
    }
    
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
    
    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    @Override
    public <DOMAIN> BaseService<?, DOMAIN> getService(DOMAIN domain) {
        return null;
    }
    
    @Override
    public <DOMAIN> BaseService<?, DOMAIN> getService(Class<DOMAIN> domainClass) {
        return null;
    }
    
    @Override
    public <DOMAIN> RpcClientSpec getRpcClient(Class<DOMAIN> domainClass) {
        return null;
    }
}
