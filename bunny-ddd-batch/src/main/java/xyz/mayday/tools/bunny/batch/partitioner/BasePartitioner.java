package xyz.mayday.tools.bunny.batch.partitioner;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.List;
import java.util.Map;

public abstract class BasePartitioner implements Partitioner {

  @Override
  public Map<String, ExecutionContext> partition(int gridSize) {
    return null;
  }

  abstract List<String> partition();
}
