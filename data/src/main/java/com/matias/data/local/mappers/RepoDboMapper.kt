package com.matias.data.local.mappers

import com.matias.data.local.model.RepoDbo
import com.matias.domain.model.Repo
import com.matias.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoDboMapper @Inject constructor(
    private val userDboMapper: UserDboMapper
) : DomainMapper<RepoDbo, Repo> {
    override fun mapToDomainModel(model: RepoDbo): Repo {
        return Repo(
            id = model.id,
            name = model.name,
            fullName = model.fullName,
            owner = userDboMapper.mapToDomainModel(model.owner),
            description = model.description ?: "",
            url = model.htmlUrl ?: "",
            homepage = model.homepage ?: "",
            stars = model.stargazersCount,
            language = model.language ?: "",
            forks = model.forksCount
        )
    }

    override fun mapFromDomainModel(domainModel: Repo): RepoDbo {
        return RepoDbo(
            id = domainModel.id,
            name = domainModel.name,
            fullName = domainModel.fullName,
            owner = userDboMapper.mapFromDomainModel(domainModel.owner),
            description = domainModel.description,
            htmlUrl = domainModel.url,
            homepage = domainModel.homepage,
            stargazersCount = domainModel.stars,
            language = domainModel.language,
            forksCount = domainModel.forks
        )
    }
}
