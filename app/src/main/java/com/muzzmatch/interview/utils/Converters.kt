package com.muzzmatch.interview.utils

import androidx.room.TypeConverter
import com.muzzmatch.interview.data.entities.MessageReadStatus
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }


    @TypeConverter
    fun fromMessageReadStatus(readStatus: MessageReadStatus?): String? {
        return readStatus?.name
    }

    @TypeConverter
    fun ftoMessageReadStatus(readStatus: String?): MessageReadStatus? {
        return readStatus?.let {
            MessageReadStatus.valueOf(it)
        }
    }
}