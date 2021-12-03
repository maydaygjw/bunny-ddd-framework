package xyz.mayday.tools.bunny.ddd.schema.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseExceptionEnum {
    
    String code;
    String message;
    Object payload;
    
    public static BaseExceptionEnum of(String code, String message, Object payload) {
        return new BaseExceptionEnum(code, message, payload);
    }
    
    public static BaseExceptionEnum of(String code, String message) {
        return of(code, message, null);
    }
}
