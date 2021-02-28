package xyz.mayday.tools.bunny.batch.config;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

public interface BatchTemplate<I, O> {

    String getJobName();

    int getConcurrency();

    Partitioner getPartitioner();

    Tasklet getPreDefinedTask();

    ItemReader<I> getReader();

    ItemWriter<O> getWriter();

    ItemProcessor<I, O> getProcessor();
}
