package xyz.mayday.tools.bunny.ddd.schema.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import xyz.mayday.tools.bunny.ddd.schema.domain.AuditInfo;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;

public interface HistoryService {
    
    <ID, DAO extends BaseDAO<ID>> List<Pair<DAO, AuditInfo>> findHistoriesById(ID id, Class<DAO> domainClass);
    
    <DAO extends BaseDAO<?>> void commit(DAO domain);
    
    <ID, DAO extends BaseDAO<ID>> void delete(ID id, Class<DAO> domainClass);
    
}
