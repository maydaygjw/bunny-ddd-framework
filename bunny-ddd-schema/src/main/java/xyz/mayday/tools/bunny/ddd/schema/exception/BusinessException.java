package xyz.mayday.tools.bunny.ddd.schema.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum.FRAMEWORK_EXCEPTION;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    String code;

    String message;

    Object payload;

    public BusinessException(String code, String message, Object payload, Throwable cause) {
        super(cause);
    }

    public BusinessException(BaseExceptionEnum exceptionEnum) {
        super();
        this.code = exceptionEnum.code;
        this.message = exceptionEnum.message;
        this.payload = exceptionEnum.payload;
    }

    public BusinessException() {
        this(FRAMEWORK_EXCEPTION);
    }
}
