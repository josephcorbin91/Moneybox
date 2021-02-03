package com.moneybox.interview.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.moneybox.interview.data.dao.AccountDao
import com.moneybox.interview.data.entities.AccountUserFriendly

@Database(entities = [AccountUserFriendly::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "moneyboxDb")
                .fallbackToDestructiveMigration()
                .build()
    }

}