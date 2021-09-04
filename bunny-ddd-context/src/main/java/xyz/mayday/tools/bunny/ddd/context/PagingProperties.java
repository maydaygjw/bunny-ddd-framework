package xyz.mayday.tools.bunny.ddd.context;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bunny.ddd.paging")
@Data
public class PagingProperties {

  Integer pageSizeDefault;

  Integer pageSizeLimit;
}
