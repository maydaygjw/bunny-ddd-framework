package xyz.mayday.tools.bunny.ddd.cache.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import xyz.mayday.tools.bunny.ddd.cache.domain.UserDO
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService

@Component
class UserService extends AbstractCacheableService<Long, UserDO, UserDO> {

    UserService(@Autowired @Qualifier("underlyingUserService") UnderlyingUserService underlyingService) {
        super(underlyingService)
    }

    @Override
    BaseService<Long, UserDO> getUnderlyingService() {
        return super.getUnderlyingService()
    }
}
