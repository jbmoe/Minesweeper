package com.example.minesweeper.di

import com.example.minesweeper.domain.model.Board
import com.example.minesweeper.domain.use_case.start.Delete
import com.example.minesweeper.domain.use_case.start.Get
import com.example.minesweeper.domain.use_case.start.Insert
import com.example.minesweeper.domain.use_case.start.StartUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStartUseCases(): StartUseCases {
        return StartUseCases(
            get = Get(),
            insert = Insert(),
            delete = Delete()
        )
    }

    @Provides
    @Singleton
    fun provideBoard(): Board {
        return Board()
    }
}