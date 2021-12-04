package xyz.mayday.tools.bunny.ddd.schema.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataStateEnum {
    
    VALID("VALID", "有效"),
    DRAFT("FAILED", "草稿"),
    INVALID("INVALID", "失效"),;
    
    String code;
    String message;
    
}
