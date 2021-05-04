package xyz.mayday.tools.bunny.ddd.sample.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseRDBMSService;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoDO;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoTagDO;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author gejunwen
 */
@Service
public class TodoService extends AbstractBaseRDBMSService<Long, TodoDO, TodoDO> {
}
