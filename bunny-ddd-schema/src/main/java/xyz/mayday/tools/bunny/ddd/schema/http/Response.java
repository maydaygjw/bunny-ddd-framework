package xyz.mayday.tools.bunny.ddd.schema.http;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("响应对象")
public class Response<T> {

    @ApiModelProperty("响应码")
    String code;

    @ApiModelProperty("响应消息, '0'表示正常返回")
    String message;

    @ApiModelProperty("响应消息体")
    T body;

    public static <T> Response<T> success(T body) {
        return new Response<T>().withCode("0").withMessage("执行成功").withBody(body);
    }

    public static <T> Response<T> success() {
        return new Response<T>().withCode("0").withMessage("执行成功");
    }

}
