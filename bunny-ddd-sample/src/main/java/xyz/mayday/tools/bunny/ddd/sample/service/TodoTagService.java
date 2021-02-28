package xyz.mayday.tools.bunny.ddd.sample.service;

import org.springframework.stereotype.Service;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseRDBMSService;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoTagDO;

@Service
public class TodoTagService extends AbstractBaseRDBMSService<String, TodoTagDO, TodoTagDO> {
}
