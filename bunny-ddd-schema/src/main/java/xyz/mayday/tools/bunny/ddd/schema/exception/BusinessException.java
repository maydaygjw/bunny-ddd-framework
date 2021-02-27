package xyz.mayday.tools.bunny.ddd.schema.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    String code;

    String message;

    Object payload;

    public BusinessException(String code, String message, Object payload, Throwable cause) {
        super(cause);
    }
}
