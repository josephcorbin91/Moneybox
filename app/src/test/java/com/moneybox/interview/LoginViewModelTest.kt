package com.moneybox.interview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moneybox.interview.api.UserService
import com.moneybox.interview.data.entities.LoginResponse
import com.moneybox.interview.ui.login.LoginViewModel
import com.moneybox.interview.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.util.concurrent.Callable

@ExperimentalCoroutinesApi
class  LoginViewModelTest {

    @MockK
    lateinit var userService: UserService

    @MockK
    lateinit var mockResponse: Response<LoginResponse>

    val dispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler?>? -> Schedulers.trampoline() }
        MockKAnnotations.init(this)
        viewModel = LoginViewModel(userService)
    }

    @Test
    fun `login blank username gives on error response`() {
        // Given
        val mockedObserver = createLoginResponseObserver()
        viewModel.loginResponse.observeForever(mockedObserver)

        every { mockResponse.isSuccessful } returns true
        coEvery { userService.login(any()) } returns mockResponse

        // When
        viewModel.onLoginClicked("", "password")

        // Then
        val loginResponseSlots = mutableListOf<Resource<Response<LoginResponse>>>()


        verify { mockedObserver.onChanged(capture(loginResponseSlots)) }

        assert(loginResponseSlots[0].status == Resource.Status.ERROR && loginResponseSlots[0].message == "Username cannot be  blank")
    }

    @Test
    fun `login blank password gives on error response`() {
        // Given
        val mockedObserver = createLoginResponseObserver()
        viewModel.loginResponse.observeForever(mockedObserver)

        every { mockResponse.isSuccessful } returns true
        coEvery { userService.login(any()) } returns mockResponse

        // When
        viewModel.onLoginClicked("username", "")

        // Then
        val loginResponseSlots = mutableListOf<Resource<Response<LoginResponse>>>()


        verify { mockedObserver.onChanged(capture(loginResponseSlots)) }

        assert(loginResponseSlots[0].status == Resource.Status.ERROR && loginResponseSlots[0].message == "Password cannot be blank")
    }

    @Test
    fun `login valid input gives correct response`() {
        // Given
        val mockedObserver = createLoginResponseObserver()
        viewModel.loginResponse.observeForever(mockedObserver)

        every { mockResponse.isSuccessful } returns true
        coEvery { userService.login(any()) } returns mockResponse

        // When
        viewModel.onLoginClicked("username", "password")

        // Then
        val loginResponseSlots = mutableListOf<Resource<Response<LoginResponse>>>()


        verify { mockedObserver.onChanged(capture(loginResponseSlots)) }

        assert(loginResponseSlots[0].status == Resource.Status.LOADING)
        assert(loginResponseSlots[1].status == Resource.Status.SUCCESS)
        coVerify { userService.login(any()) }
    }

    @Test
    fun `login valid input server error gives error response`() {
        // Given
        val mockedObserver = createLoginResponseObserver()
        viewModel.loginResponse.observeForever(mockedObserver)

        every { mockResponse.isSuccessful } returns false
        every { mockResponse.errorBody() } returns null
        coEvery { userService.login(any()) } returns mockResponse

        // When
        viewModel.onLoginClicked("username", "password")

        // Then
        val loginResponseSlots = mutableListOf<Resource<Response<LoginResponse>>>()


        verify { mockedObserver.onChanged(capture(loginResponseSlots)) }
        assert(loginResponseSlots[0].status == Resource.Status.LOADING)
        assert(loginResponseSlots[1].status == Resource.Status.ERROR && loginResponseSlots[1].message == "Error Occurred!")
        coVerify { userService.login(any()) }
    }

    private fun createLoginResponseObserver(): Observer<Resource<Response<LoginResponse>>> =
        spyk(Observer { })
}