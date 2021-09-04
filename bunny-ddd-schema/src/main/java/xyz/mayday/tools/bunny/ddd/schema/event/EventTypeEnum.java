package xyz.mayday.tools.bunny.ddd.schema.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventTypeEnum {
  SUCCESS("SUCCESS", "成功事件"),
  FAILED("FAILED", "失败事件");

  String code;
  String message;

  @JsonCreator
  public static EventTypeEnum from(Object input) {
    return EventTypeEnum.SUCCESS;
  }
}
