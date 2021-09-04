package xyz.mayday.tools.bunny.ddd.schema.view;

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
  Integer revision;

  @ApiModelProperty("操作类型")
  String operationType;
}
