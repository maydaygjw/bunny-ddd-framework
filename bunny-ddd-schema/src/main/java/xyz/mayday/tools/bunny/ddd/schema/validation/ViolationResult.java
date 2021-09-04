package xyz.mayday.tools.bunny.ddd.schema.validation;

import lombok.Data;

@Data
public class ViolationResult {

  String code;

  String message;

  String reference;
}
