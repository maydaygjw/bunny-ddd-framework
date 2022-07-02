package xyz.mayday.tools.bunny.ddd.core.async;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;

/**
 * @author gejunwen
 */
@RequiredArgsConstructor
public class ForkJoinTaskExecutor {
    
    final AsyncTaskExecutor asyncTaskExecutor;
    
    static final long EXEC_TIMEOUT_SECONDS = 30;
    
    public <T> List<T> submit(List<Callable<T>> tasks) {
        
        RequestAttributes context = null;
        
        if (Objects.nonNull(RequestContextHolder.getRequestAttributes())) {
            context = RequestContextHolder.currentRequestAttributes();
        }
        
        final RequestAttributes finalContext = context;
        
        List<Future<T>> futures = tasks.parallelStream().map(task -> asyncTaskExecutor.submit(new ContextualTask<>(finalContext, task)))
                .collect(Collectors.toList());
        
        return futures.stream().map(future -> {
            try {
                return future.get(EXEC_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                future.cancel(true);
                throw new BusinessException(e);
            }
        }).collect(Collectors.toList());
    }
    
    @AllArgsConstructor
    private static class ContextualTask<T> implements Callable<T> {
        
        RequestAttributes requestAttributes;
        Callable<T> target;
        
        @Override
        public T call() throws Exception {
            if (Objects.nonNull(requestAttributes)) {
                RequestContextHolder.setRequestAttributes(requestAttributes);
            }
            try {
                return target.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}
