package xyz.mayday.tools.bunny.ddd.context.service;

import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;

public class DefaultPrincipalService implements PrincipalService {
    @Override
    public String getCurrentUserId() {
        return "mockUserId";
    }

    @Override
    public String getCurrentUserName() {
        return "mockUserName";
    }
}
