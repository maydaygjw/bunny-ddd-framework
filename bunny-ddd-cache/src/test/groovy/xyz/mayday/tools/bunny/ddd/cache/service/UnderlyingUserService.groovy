package xyz.mayday.tools.bunny.ddd.cache.service

import xyz.mayday.tools.bunny.ddd.cache.domain.UserDO
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService

import java.util.stream.Stream

class UnderlyingUserService implements BaseService<Long, UserDO> {

    @Override
    Optional<UserDO> findItemById(Long aLong) {
        return null
    }

    @Override
    List<UserDO> findItemsByIds(List<Long> id) {
        return null
    }

    @Override
    List<UserDO> findHistoriesById(Long aLong) {
        return null
    }

    @Override
    PageableData<UserDO> findItems(UserDO example, CommonQueryParam queryParam) {
        return null
    }

    @Override
    Long countItems(UserDO example) {
        return null
    }

    @Override
    List<UserDO> findAll(UserDO example) {
        return null
    }

    @Override
    Stream<UserDO> findStream(UserDO example) {
        return null
    }

    @Override
    UserDO insert(UserDO userDo) {
        userDo.setId(1L)
        return userDo
    }

    @Override
    List<UserDO> bulkInsert(List<UserDO> userDos) {
        return null
    }

    @Override
    UserDO update(UserDO userDo) {
        return null
    }

    @Override
    List<UserDO> bulkUpdate(List<UserDO> userDos) {
        return null
    }

    @Override
    UserDO save(UserDO userDo) {
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

    @Override
    Class<UserDO> getDomainClass() {
        return null
    }
}
