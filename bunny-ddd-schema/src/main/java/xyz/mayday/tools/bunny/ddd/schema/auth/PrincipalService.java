package xyz.mayday.tools.bunny.ddd.schema.auth;

public interface PrincipalService {

  String getCurrentUserId();

  String getCurrentUserName();
}
