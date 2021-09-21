package com.app.yuru.di.module

import android.content.Context
import com.app.yuru.coreandroid.network.NetworkChecker
import com.app.yuru.coreandroid.network.NetworkCheckerImpl
import com.app.yuru.data.datasource.remote.YuruRemoteDatasource
import com.app.yuru.data.datasource.remote.YuruRemoteDatasourceImpl
import com.app.yuru.data.datasource.remote.service.YuruApiService
import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.data.repository.YuruRepositoryImpl
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
    fun provideYuruRemoteDatasource(services: YuruApiService): YuruRemoteDatasource {
        return YuruRemoteDatasourceImpl(services)
    }


    @Provides
    @ActivityScoped
    fun provideNetworkChecker(@ApplicationContext ctx: Context): NetworkChecker {
        return NetworkCheckerImpl(ctx)
    }

    @Provides
    @ActivityScoped
    fun provideNewsRepository(
        remote: YuruRemoteDatasource,
        networkCheck: NetworkChecker
    ): YuruRepository {
        return YuruRepositoryImpl(remote = remote, networkChecker = networkCheck)
    }
}