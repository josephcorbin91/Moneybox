package com.moneybox.interview

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moneybox.interview.api.ProductsService
import com.moneybox.interview.data.entities.InvestorProduct
import com.moneybox.interview.data.repository.AccountRepository
import com.moneybox.interview.ui.accounts.AccountsViewModel
import com.moneybox.interview.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.Callable

@ExperimentalCoroutinesApi
class  AccountViewModelTest {

    var mockResponse = mockk<Response<InvestorProduct>>(relaxUnitFun = true) {
        every { body() } returns investorProduct
        every { isSuccessful } returns true

    }

    var investorProduct = mockk<InvestorProduct>(relaxUnitFun = true)
    var productsService = mockk<ProductsService>(relaxUnitFun = true) {
        coEvery { getProducts("BEARER_TOKEN") } returns mockResponse
    }

    @MockK
    lateinit var accountRepository: AccountRepository

    var sharedPreferences = mockk<SharedPreferences>(relaxUnitFun = true) {
        every { getString("BEARER_TOKEN_KEY", "") } returns "BEARER_TOKEN"
    }


    val dispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: AccountsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        MockKAnnotations.init(this)
        viewModel = AccountsViewModel(productsService, accountRepository, sharedPreferences)
    }

    @Test
    fun `test investorProduct`()= runBlockingTest {
        // Given
        val mockedObserver = createInvestorProductObserver()
        viewModel.investorProductResponse.observeForever(mockedObserver)

        // When
        viewModel.getInvestorProducts()

        // Then
        val investorProductResponseSlots = mutableListOf<Resource<Response<InvestorProduct>>>()
        verify { mockedObserver.onChanged(capture(investorProductResponseSlots)) }
        assert(investorProductResponseSlots[1].status == Resource.Status.LOADING)
        assert(investorProductResponseSlots[2].status == Resource.Status.SUCCESS)
    }

    private fun createInvestorProductObserver(): Observer<Resource<Response<InvestorProduct>>> =
        spyk(Observer { })
}