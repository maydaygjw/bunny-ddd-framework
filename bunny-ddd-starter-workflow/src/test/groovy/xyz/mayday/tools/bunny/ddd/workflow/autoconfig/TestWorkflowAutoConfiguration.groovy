package xyz.mayday.tools.bunny.ddd.workflow.autoconfig

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.workflow.client.WorkflowClient

@SpringBootTest
@Configuration
class TestWorkflowAutoConfiguration extends Specification {

    @Autowired
    WorkflowClient workflowClient;

    def "workflow client is registered fine"() {
        expect:
        workflowClient
    }

}
