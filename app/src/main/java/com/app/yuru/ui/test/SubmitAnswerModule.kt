package com.app.yuru.ui.test

import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.usecase.SignUpUseCase
import com.app.yuru.domain.usecase.SubmitResponseUseCase
import com.app.yuru.ui.login.SignUpViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class SubmitAnswerModule {
    @Provides
    fun provideSubmitAnswerUseCase(repository: YuruRepository): SubmitResponseUseCase {
        return SubmitResponseUseCase(repository)
    }

    @Provides
    fun provideSubmitAnswerModel(useCase: SubmitResponseUseCase): SubmitResponseModel {
        return SubmitResponseModel(useCase)
    }
}