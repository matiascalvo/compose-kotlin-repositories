package com.matias.data.remote.model.mappers

import com.matias.data.remote.model.UserDto
import com.matias.domain.model.User
import com.matias.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDtoMapper @Inject constructor() : DomainMapper<UserDto, User> {
    override fun mapToDomainModel(model: UserDto): User {
        return User(
            login = model.login,
            id = model.id,
            avatarUrl = model.avatarUrl
        )
    }

    override fun mapFromDomainModel(domainModel: User): UserDto {
        return UserDto(
            login = domainModel.login,
            id = domainModel.id,
            avatarUrl = domainModel.avatarUrl
        )
    }
}
