package com.app.yuru.di.module

import android.content.Context
import com.app.yuru.coreandroid.network.NetworkChecker
import com.app.yuru.coreandroid.network.NetworkCheckerImpl
import com.app.yuru.data.datasource.remote.NewsRemoteDatasource
import com.app.yuru.data.datasource.remote.NewsRemoteDatasourceImpl
import com.app.yuru.data.datasource.remote.service.NewsApiServices
import com.app.yuru.data.repository.NewsRepository
import com.app.yuru.data.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun provideNewsRemoteDatasource(services: NewsApiServices): NewsRemoteDatasource {
        return NewsRemoteDatasourceImpl(services)
    }


    @Provides
    @ActivityScoped
    fun provideNetworkChecker(@ApplicationContext ctx: Context): NetworkChecker {
        return NetworkCheckerImpl(ctx)
    }

    @Provides
    @ActivityScoped
    fun provideNewsRepository(
        remote: NewsRemoteDatasource,
        networkCheck: NetworkChecker
    ): NewsRepository {
        return NewsRepositoryImpl(remote = remote, networkChecker = networkCheck)
    }
}