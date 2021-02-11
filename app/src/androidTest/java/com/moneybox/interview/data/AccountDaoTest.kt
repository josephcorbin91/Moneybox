package com.moneybox.interview.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.moneybox.interview.data.dao.AccountDao
import com.moneybox.interview.data.db.AppDatabase
import com.moneybox.interview.data.entities.AccountUserFriendly
import com.moneybox.interview.utilities.getValue
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AccountDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var accountDao: AccountDao
    private val accountA = AccountUserFriendly(1L, "Friendly Name A ", 100.0, 1.0)
    private val accountB = AccountUserFriendly(2L, "Friendly Name B ", 100.0, 1.0)
    private val accountC = AccountUserFriendly(3L, "Friendly Name C", 100.0, 2.0)


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        accountDao = database.accountDao()

        accountDao.insertAll(listOf(accountA, accountB, accountC))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetAccounts() = runBlocking {
        val accountList = getValue(accountDao.getAllAccounts())
        assertThat(accountList.size, equalTo(3))
        assertThat(accountList[0], equalTo(accountA))
        assertThat(accountList[1], equalTo(accountB))
        assertThat(accountList[2], equalTo(accountC))
    }

    @Test
    fun testGetAccount() = runBlocking {
        val retrievedAccount = getValue(accountDao.getAccount(2L))
        assertThat(retrievedAccount, equalTo(accountB))
    }

    @Test
    fun updateAccount() = runBlocking {
        assertThat(accountA.moneyBox, equalTo(1.0))
        accountA.moneyBox = 30.0
        accountDao.updateAccounts(accountA)
        val retrievedAccountA = getValue(accountDao.getAccount(1L))
        assertThat(retrievedAccountA.moneyBox, equalTo(30.0))
    }
}
