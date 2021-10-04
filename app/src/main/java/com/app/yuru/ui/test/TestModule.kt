package com.app.yuru.ui.test

import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.usecase.QuestionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class TestModule {
    @Provides
    @ActivityScoped
    fun provideTestViewModel(): TestStepViewModel {
        return TestStepViewModel()
    }

    @Provides
    fun provideQuestionUseCase(repository: YuruRepository): QuestionUseCase {
        return QuestionUseCase(repository)
    }

//    @Provides
//    fun provideTestViewModel(useCase: QuestionUseCase): TestViewModel {
//        return TestViewModel(useCase)
//    }
}