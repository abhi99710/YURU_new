package com.app.yuru.ui.login

import com.app.yuru.data.repository.YuruRepository
import com.app.yuru.domain.usecase.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class SignUpModule {

    @Provides
    fun provideSignUpUseCase(repository: YuruRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    fun provideSignupViewModel(useCase: SignUpUseCase): SignUpViewModel {
        return SignUpViewModel(useCase)
    }
}