package com.trantt.omdbexample

import com.trantt.omdbexample.data.OMDBApi
import com.trantt.omdbexample.di.AppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestModule {
    @Provides
    @Singleton
    fun getClient(): OMDBApi {
        return MockOMDBApi()
    }
}