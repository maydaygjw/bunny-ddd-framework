package xyz.mayday.tools.bunny.ddd.schema.http;

import lombok.Data;

@Data
public class Response<T> {

    String code;

    String message;

    T data;

}
