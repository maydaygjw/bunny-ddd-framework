package xyz.mayday.tools.bunny.ddd.core.domain;

import org.springframework.stereotype.Service;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseRDBMSService;

@Service
public class UserService extends AbstractBaseRDBMSService<Long, Domain.UserDTO, Domain.UserDAO> {}
