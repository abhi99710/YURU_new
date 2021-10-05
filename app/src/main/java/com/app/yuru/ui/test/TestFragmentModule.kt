package com.app.yuru.ui.test

import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.usecase.QuestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@Module
@InstallIn(FragmentComponent::class)
class TestFragmentModule {
    @Provides
    @FragmentScoped
    fun provideTestViewModel(useCase: QuestionUseCase): TestViewModel {
        return TestViewModel(useCase)
    }

    @Provides
    @FragmentScoped
    fun provideQuestionUseCase(repository: YuruRepository): QuestionUseCase {
        return QuestionUseCase(repository)
    }
}