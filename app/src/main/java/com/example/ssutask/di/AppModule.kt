package com.example.ssutask.di

import android.app.Application
import androidx.room.Room
import com.example.ssutask.data.local.MyLibraryDatabaseClass
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMyLibraryDatabase(app: Application): MyLibraryDatabaseClass {
        return Room.databaseBuilder(
            app,
            MyLibraryDatabaseClass::class.java,
            MyLibraryDatabaseClass.DATABASE_NAME,
        ).build()
    }
}