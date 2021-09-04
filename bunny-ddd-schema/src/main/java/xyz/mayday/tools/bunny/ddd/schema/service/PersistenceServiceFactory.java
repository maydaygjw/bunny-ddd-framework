package xyz.mayday.tools.bunny.ddd.schema.service;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;

import javax.persistence.EntityManager;

/** @author gejunwen */
public interface PersistenceServiceFactory extends ServiceFactory {

  <ID, DTO, DAO> JpaRepositoryImplementation<DAO, ID> getRepository(DAO dto);

  <ID, DTO, DAO extends BaseDAO<ID>> JpaRepositoryImplementation<DAO, ID> getRepository(
      Class<DAO> daoCls);

  <ID, DTO, DAO extends BaseDAO<ID>> RevisionRepository<DAO, ID, Integer> getRevisionRepository(
      Class<DAO> daoCls);

  EntityManager getEntityManager();

  JdbcTemplate getJdbcTemplate();
}
