package com.moneybox.interview.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountUserFriendly (
    @PrimaryKey
    val id: Long,
    val friendlyName: String,
    val planValue: Double,
    var moneyBox: Double
)
