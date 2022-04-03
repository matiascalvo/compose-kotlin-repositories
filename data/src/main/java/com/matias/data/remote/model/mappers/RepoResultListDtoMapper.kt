package com.matias.data.remote.model.mappers

import com.matias.data.remote.model.RepoDto
import com.matias.data.remote.model.ResultListDto
import com.matias.domain.model.Repo
import com.matias.domain.model.ResultList
import com.matias.domain.util.DomainMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepoResultListDtoMapper @Inject constructor(
    private val repoDtoMapper: RepoDtoMapper,
) : DomainMapper<ResultListDto<RepoDto>, ResultList<Repo>> {
    override fun mapToDomainModel(model: ResultListDto<RepoDto>): ResultList<Repo> {
        return ResultList(
            totalCount = model.totalCount,
            items = model.items.map { repoDtoMapper.mapToDomainModel(it) }
        )
    }

    override fun mapFromDomainModel(domainModel: ResultList<Repo>): ResultListDto<RepoDto> =
        ResultListDto(
            totalCount = domainModel.totalCount,
            items = domainModel.items.map { repoDtoMapper.mapFromDomainModel(it) }
        )
}
