package xyz.mayday.tools.bunny.ddd.sample.repo;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoDO;

@Repository
public interface TodoRepository extends JpaRepositoryImplementation<TodoDO, Long> {
}
