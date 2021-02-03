/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moneybox.interview.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moneybox.interview.data.entities.AccountUserFriendly

/**
 * The Data Access Object for the Account class.
 */
@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getAccount(accountId: Long): LiveData<AccountUserFriendly>

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): LiveData<List<AccountUserFriendly>>

    @Update
    fun updateAccounts(vararg accounts: AccountUserFriendly)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(accounts: List<AccountUserFriendly>)
}
