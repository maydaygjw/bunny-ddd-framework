package xyz.mayday.tools.bunny.ddd.rdbms.service;

import org.springframework.stereotype.Service;
import xyz.mayday.tools.bunny.ddd.rdbms.domain.Domain;

@Service
public class UserService extends AbstractBaseRDBMSService<Long, Domain.UserDTO, Domain.UserDAO> {}
