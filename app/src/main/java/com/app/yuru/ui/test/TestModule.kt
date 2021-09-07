package com.app.yuru.ui.test

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
    fun provideTestViewModel(): TestViewModel {
        return TestViewModel()
    }
}