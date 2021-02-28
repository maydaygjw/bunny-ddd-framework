package xyz.mayday.tools.bunny.ddd.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoDO;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoTagDO;
import xyz.mayday.tools.bunny.ddd.sample.service.TodoService;
import xyz.mayday.tools.bunny.ddd.sample.service.TodoTagService;

/**
 * @author gejunwen
 */
@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class BunnyDDDSampleApplication implements CommandLineRunner {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoTagService todoTagService;

    public static void main(String[] args) {
        SpringApplication.run(BunnyDDDSampleApplication.class);
    }


    @Override
    public void run(String... args) throws Exception {

        todoService.deleteAll();
        todoTagService.deleteAll();

        TodoDO todoDo = todoService.insert(new TodoDO().withName("todo1").withDescription("d1"));
        todoTagService.insert(new TodoTagDO().withTodoId(todoDo.getId()).withTagName("Men"));
        todoTagService.insert(new TodoTagDO().withTodoId(todoDo.getId()).withTagName("Woman"));
    }
}
