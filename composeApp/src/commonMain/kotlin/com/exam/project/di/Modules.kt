package com.exam.project.di

import com.exam.project.data.repository.RepositoryImpl
import com.exam.project.data.source.local.LocalSource
import com.exam.project.data.source.local.LocalSourceImpl
import com.exam.project.data.source.remote.source.RemoteSource
import com.exam.project.data.source.remote.source.RemoteSourceImpl
import com.exam.project.data.source.remote.util.HttpClientFactory
import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.CheckHasMoreToLoad
import com.exam.project.domain.usecase.GetMedia
import com.exam.project.domain.usecase.GetMediaList
import com.exam.project.domain.usecase.GetSearchHistory
import com.exam.project.domain.usecase.SaveMediaList
import com.exam.project.domain.usecase.SaveSearchQuery
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::RemoteSourceImpl).bind<RemoteSource>()
    singleOf(::LocalSourceImpl).bind<LocalSource>()
    singleOf(::RepositoryImpl).bind<Repository>()
    singleOf(::GetMediaList).bind()
    singleOf(::GetSearchHistory).bind()
    singleOf(::SaveMediaList).bind()
    singleOf(::SaveSearchQuery).bind()
    singleOf(::CheckHasMoreToLoad).bind()
    singleOf(::GetMedia).bind()

}