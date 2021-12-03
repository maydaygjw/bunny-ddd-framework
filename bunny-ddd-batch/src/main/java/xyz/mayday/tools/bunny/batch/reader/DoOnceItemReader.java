package xyz.mayday.tools.bunny.batch.reader;

import javax.batch.runtime.StepExecution;

import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

/** @author gejunwen */
public abstract class DoOnceItemReader<I> implements ItemReader<I> {
    
    boolean executed;
    
    @Value("#{stepExecution}")
    StepExecution stepExecution;
    
    @Override
    public I read() throws Exception {
        
        I i = null;
        
        if (!executed) {
            i = doRead();
            executed = true;
        }
        
        return i;
    }
    
    public abstract I doRead();
    
    protected StepExecution getStepExecution() {
        return stepExecution;
    }
}
