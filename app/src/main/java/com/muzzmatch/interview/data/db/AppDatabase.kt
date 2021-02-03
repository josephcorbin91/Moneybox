package com.muzzmatch.interview.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.muzzmatch.interview.data.dao.ChatMessagesDao
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.utils.Converters
import com.muzzmatch.interview.utils.DATABASE_NAME
import com.muzzmatch.interview.worker.SeedDatabaseWorker

@Database(entities = [ChatMessage::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatMessagesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
                instance ?: synchronized(this) {
                    instance ?: buildDatabase(context).also { instance = it }
                }

        private fun buildDatabase(appContext: Context) =
                Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(
                                object : RoomDatabase.Callback() {
                                    override fun onCreate(db: SupportSQLiteDatabase) {
                                        super.onCreate(db)
                                        val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                                        WorkManager.getInstance(appContext).enqueue(request)
                                    }
                                }
                        )
                        .build()

    }
}