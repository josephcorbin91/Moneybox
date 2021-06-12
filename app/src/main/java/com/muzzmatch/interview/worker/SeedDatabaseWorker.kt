package com.muzzmatch.interview.worker

import android.content.Context
import android.content.res.AssetManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.muzzmatch.interview.data.db.AppDatabase
import com.muzzmatch.interview.data.entities.ChatMessage
import com.muzzmatch.interview.utils.DATABASE_SEED_FILE
import kotlinx.coroutines.coroutineScope
import timber.log.Timber


class SeedDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(DATABASE_SEED_FILE, AssetManager.ACCESS_STREAMING).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
                    val plantType = object : TypeToken<List<ChatMessage>>() {}.type
                    val chatList: List<ChatMessage> = gson.fromJson(jsonReader, plantType)
                    val database = AppDatabase.getDatabase(applicationContext)
                    database.chatDao().insertAll(chatList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding database")
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}