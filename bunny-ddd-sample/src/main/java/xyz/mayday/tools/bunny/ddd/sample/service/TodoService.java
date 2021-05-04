package xyz.mayday.tools.bunny.ddd.sample.service;

import org.springframework.stereotype.Service;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseRDBMSService;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoDO;

/**
 * @author gejunwen
 */
@Service
public class TodoService extends AbstractBaseRDBMSService<Long, TodoDO, TodoDO> {
}
