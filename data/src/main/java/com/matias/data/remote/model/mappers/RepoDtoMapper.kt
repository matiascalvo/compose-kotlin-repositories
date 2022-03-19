package com.matias.data.remote.model.mappers

import com.matias.data.remote.model.RepoDto
import com.matias.domain.model.Repo
import com.matias.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoDtoMapper @Inject constructor(
    private val userDtoMapper: UserDtoMapper
) : DomainMapper<RepoDto, Repo> {
    override fun mapToDomainModel(model: RepoDto): Repo {
        return Repo(
            id = model.id,
            name = model.name,
            fullName = model.fullName,
            owner = userDtoMapper.mapToDomainModel(model.owner),
            description = model.description ?: "",
            url = model.htmlUrl ?: "",
            homepage = model.homepage ?: "",
            stars = model.stargazersCount,
            language = model.language ?: "",
            forks = model.forksCount
        )
    }

    override fun mapFromDomainModel(domainModel: Repo): RepoDto {
        return RepoDto(
            id = domainModel.id,
            name = domainModel.name,
            fullName = domainModel.fullName,
            owner = userDtoMapper.mapFromDomainModel(domainModel.owner),
            description = domainModel.description,
            htmlUrl = domainModel.url,
            homepage = domainModel.homepage,
            stargazersCount = domainModel.stars,
            language = domainModel.language,
            forksCount = domainModel.forks
        )
    }
}
