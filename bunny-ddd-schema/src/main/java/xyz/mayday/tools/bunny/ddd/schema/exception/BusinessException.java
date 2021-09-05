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
