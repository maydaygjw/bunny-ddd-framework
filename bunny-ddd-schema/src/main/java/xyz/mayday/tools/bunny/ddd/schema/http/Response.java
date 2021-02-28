package xyz.mayday.tools.bunny.ddd.schema.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    String code;

    String message;

    T body;

    public static <T> Response<T> success(T body) {
        return new Response<T>().withCode("0").withBody(body);
    }

    public static <T> Response<T> success() {
        return new Response<T>().withCode("0");
    }

}
