package xyz.mayday.tools.bunny.ddd.schema.view;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;

/** @author gejunwen */
@Data
@ApiModel
public abstract class AbstractBaseVO<ID> implements BaseVO<ID> {
    
    @ApiModelProperty("ID")
    ID id;
    
    @ApiModelProperty(value = "版本号", hidden = true)
    Integer version;
    
    @ApiModelProperty("版本号")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long revision;
    
    @ApiModelProperty("操作类型")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String operationType;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    List<String> changedProperties;

    Date createdDate;

    Date updatedDate;

    String createdBy;

    String updatedBy;
}
