package com.app.yuru.ui.homepage

import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.usecase.GetTopHeadlineUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class HomePageModule {

    @Provides
    fun provideTopHeadLineUseCase(repository: YuruRepository): GetTopHeadlineUseCase {
        return GetTopHeadlineUseCase(repository)
    }

    @Provides
    fun provideListNewsViewModel(useCase: GetTopHeadlineUseCase): ListNewsViewModel {
        return ListNewsViewModel(useCase)
    }
}