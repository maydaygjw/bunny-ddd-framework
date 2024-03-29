package xyz.mayday.tools.bunny.ddd.cache.service

import org.springframework.stereotype.Component
import xyz.mayday.tools.bunny.ddd.cache.domain.UserDO
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService
import xyz.mayday.tools.bunny.ddd.core.utils.BeanUtils
import xyz.mayday.tools.bunny.ddd.schema.domain.DataStateEnum
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam

import java.util.stream.Stream

@Component
class UnderlyingUserService extends AbstractBaseService<Long, UserDO, UserDO> {

    Map<Long, UserDO> storage = new HashMap<>();


    @Override
    PageableData<UserDO> findItems(UserDO example, CommonQueryParam queryParam) {
        return null
    }

    @Override
    Optional<UserDO> findItemById(Long aLong) {
        return null
    }

    @Override
    List<UserDO> findItemsByIds(List<Long> id) {
        return null
    }

    @Override
    Long countItems(UserDO example) {
        return null
    }

    @Override
    List<UserDO> findAll(UserDO example) {
        return Collections.emptyList()
    }

    @Override
    Stream<UserDO> findStream(UserDO example) {
        return null
    }

    @Override
    UserDO insert(UserDO userDO) {
        userDO.setId(1L)
        userDO.setDataState(DataStateEnum.VALID)
        super.auditWhenInsert(userDO)
        storage.put(userDO.id, userDO)
        return userDO
    }

    @Override
    List<UserDO> bulkInsert(List<UserDO> userDOS) {
        return null
    }

    @Override
    UserDO update(UserDO userDO) {
        if (Objects.isNull(userDO.getId())) {
            throw new BusinessException();
        }
        def dbOne = storage.get(userDO.getId())
        BeanUtils.copyProperties(userDO, dbOne);
        auditWhenUpdate(dbOne);
        dbOne.setVersion(dbOne.getVersion() + 1);
        return dbOne;
    }

    @Override
    List<UserDO> bulkUpdate(List<UserDO> userDOS) {
        return null
    }

    @Override
    UserDO save(UserDO userDO) {
        return null
    }

    @Override
    UserDO delete(Long aLong) {
        return null
    }

    @Override
    List<UserDO> bulkDeleteById(List<Long> longs) {
        return null
    }

    @Override
    List<UserDO> deleteAll() {
        return null
    }
}
