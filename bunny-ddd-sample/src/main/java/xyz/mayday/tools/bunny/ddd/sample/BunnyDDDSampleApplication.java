package xyz.mayday.tools.bunny.ddd.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import javax.annotation.processing.Processor;

/**
 * @author gejunwen
 */
@SpringBootApplication
@Slf4j
public class BunnyDDDSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BunnyDDDSampleApplication.class);
    }

}
