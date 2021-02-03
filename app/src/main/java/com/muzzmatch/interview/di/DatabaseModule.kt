package com.muzzmatch.interview.di

import android.content.Context
import com.muzzmatch.interview.data.dao.ChatMessagesDao
import com.muzzmatch.interview.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideChatMessagesDao(appDatabase: AppDatabase): ChatMessagesDao {
        return appDatabase.chatDao()
    }
}
