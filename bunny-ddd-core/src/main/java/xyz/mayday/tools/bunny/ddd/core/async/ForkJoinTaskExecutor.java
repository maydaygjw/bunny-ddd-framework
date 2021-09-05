package xyz.mayday.tools.bunny.ddd.core.async;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;

/** @author gejunwen */
@RequiredArgsConstructor
public class ForkJoinTaskExecutor {

  final AsyncTaskExecutor asyncTaskExecutor;

  public <T> List<T> submit(List<Callable<T>> tasks) {
    return tasks.parallelStream()
        .map(
            task -> {
              try {
                return asyncTaskExecutor.submit(task).get();
              } catch (InterruptedException | ExecutionException e) {
                throw new BusinessException();
              }
            })
        .collect(Collectors.toList());
  }
}
