package xyz.mayday.tools.bunny.ddd.schema.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FrameworkExceptionEnum {
    
    public static final BaseExceptionEnum NO_SUCH_ELEMENT = BaseExceptionEnum.of("NO_SUCH_ELEMENT", "找不到当前元素");
    
    public static final BaseExceptionEnum UNSUPPORTED_OPERATION = BaseExceptionEnum.of("UNSUPPORTED_OPERATION", "不支持的操作");
    
    public static final BaseExceptionEnum FRAMEWORK_EXCEPTION = BaseExceptionEnum.of("FRAMEWORK_EXCEPTION", "FRAMEWORK_EXCEPTION");
}
