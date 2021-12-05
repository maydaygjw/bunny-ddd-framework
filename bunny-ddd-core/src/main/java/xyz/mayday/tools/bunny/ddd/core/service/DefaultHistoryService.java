package xyz.mayday.tools.bunny.ddd.core.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.tuple.Pair;
import org.javers.core.Javers;
import org.javers.repository.jql.InstanceIdDTO;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;

import xyz.mayday.tools.bunny.ddd.schema.domain.AuditInfo;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService;

import com.google.common.collect.Streams;

@RequiredArgsConstructor
public class DefaultHistoryService implements HistoryService {
    
    final Javers javers;
    
    @Override
    public <ID, DAO extends BaseDAO<ID>> List<Pair<DAO, AuditInfo>> findHistoriesById(ID id, Class<DAO> domainClass) {
        JqlQuery query = QueryBuilder.byInstanceId(id, domainClass).build();
        return Streams.zip(javers.<DAO> findShadows(query).stream(), javers.findSnapshots(query).stream(), ((daoShadow, snapshot) -> {
            DAO dao = daoShadow.get();
            AuditInfo auditInfo = new AuditInfo().withRevision(snapshot.getCommitId().valueAsNumber().longValue()).withChangedProperties(snapshot.getChanged())
                    .withVersion((int) snapshot.getVersion()).withOperationType(snapshot.getType().name());
            return Pair.of(dao, auditInfo);
        })).collect(Collectors.toList());
    }
    
    @Override
    public <DAO extends BaseDAO<?>> void commit(DAO domain) {
        javers.commit(domain.getUpdatedBy(), domain);
    }
    
    @Override
    public <ID, DOMAIN extends BaseDAO<ID>> void delete(ID id, Class<DOMAIN> domainClass) {
        javers.commitShallowDeleteById("DeleteMan", InstanceIdDTO.instanceId(id, domainClass));
    }
    
}
