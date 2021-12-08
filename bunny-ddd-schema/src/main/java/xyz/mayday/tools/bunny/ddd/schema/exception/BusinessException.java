package xyz.mayday.tools.bunny.ddd.schema.exception;

import static xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum.FRAMEWORK_EXCEPTION;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    
    String code;
    
    String message;
    
    Object payload;
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public BusinessException(String code) {
        this.code = code;
    }
    
    public BusinessException(String code, String message, Object payload, Throwable cause) {
        super(cause);
    }
    
    public BusinessException(BaseExceptionEnum exceptionEnum) {
        super();
        this.code = exceptionEnum.code;
        this.message = exceptionEnum.message;
        this.payload = exceptionEnum.payload;
    }
    
    public BusinessException(Throwable cause) {
        this(FRAMEWORK_EXCEPTION.code, FRAMEWORK_EXCEPTION.message, cause);
    }
    
    public BusinessException() {
        this(FRAMEWORK_EXCEPTION);
    }
}
