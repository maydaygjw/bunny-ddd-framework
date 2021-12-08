package xyz.mayday.tools.bunny.ddd.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.javers.core.metamodel.annotation.DiffIgnore;

import xyz.mayday.tools.bunny.ddd.schema.cache.CacheQueryField;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.domain.DataStateEnum;

/** @author gejunwen */
@Data
@MappedSuperclass
public abstract class AbstractBaseDAO<ID extends Serializable> implements BaseDAO<ID> {
    
    private static final String DAO_SUFFIX = "DAO";
    private static final String DO_SUFFIX = "DO";
    
    @Id
    ID id;
    
    @DiffIgnore
    @Version
    @Column(nullable = false)
    @org.springframework.data.annotation.Version
    Integer version;
    
    @DiffIgnore
    @Column(nullable = false)
    Date createdDate;
    
    @DiffIgnore
    @Column(nullable = false)
    @CacheQueryField
    Date updatedDate;
    
    @DiffIgnore
    @Column(nullable = false)
    String createdBy;
    
    @DiffIgnore
    @Column(nullable = false)
    @CacheQueryField
    String updatedBy;
    
    @DiffIgnore
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @CacheQueryField
    DataStateEnum dataState;
    
    @Override
    public String getDomainName() {
        
        String simpleName = getClass().getSimpleName();
        if (StringUtils.endsWithIgnoreCase(simpleName, DAO_SUFFIX)) {
            return StringUtils.substringBeforeLast(simpleName, DAO_SUFFIX);
        } else if (StringUtils.endsWithIgnoreCase(simpleName, DO_SUFFIX)) {
            return StringUtils.substringBeforeLast(simpleName, DO_SUFFIX);
        }
        return simpleName;
    }
}
