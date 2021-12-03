package xyz.mayday.tools.bunny.batch.partitioner;

import java.util.List;
import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public abstract class BasePartitioner implements Partitioner {
    
    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        return null;
    }
    
    abstract List<String> partition();
}
