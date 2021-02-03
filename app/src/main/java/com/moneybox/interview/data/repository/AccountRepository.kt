package com.moneybox.interview.data.repository

import androidx.lifecycle.LiveData
import com.moneybox.interview.data.dao.AccountDao
import com.moneybox.interview.data.entities.AccountUserFriendly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class AccountRepository @Inject constructor(private val accountDao: AccountDao) {

    fun getAccount(accountId: Long): LiveData<AccountUserFriendly> {
        return accountDao.getAccount(accountId)
    }

    fun getAllAccount(): LiveData<List<AccountUserFriendly>> {
        return accountDao.getAllAccounts()
    }

    suspend fun updateAccountMoneyBox(account: AccountUserFriendly) = withContext(Dispatchers.IO) {
        return@withContext accountDao.updateAccounts(account)
    }

    suspend fun insertAllProducts(accounts: List<AccountUserFriendly>) = accountDao.insertAll(accounts)
}