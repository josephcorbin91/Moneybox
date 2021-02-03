package com.moneybox.interview.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneybox.interview.api.UserService
import com.moneybox.interview.data.entities.LoginResponse
import com.moneybox.interview.data.request.UserLoginRequestBody
import com.moneybox.interview.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel @ViewModelInject constructor(
    private val userService: UserService
) : ViewModel() {

    val loginResponse: LiveData<Resource<Response<LoginResponse>>>
        get() = _loginResponse
    private val _loginResponse = MutableLiveData<Resource<Response<LoginResponse>>>()


    fun onLoginClicked(userName: String, password: String) {
        if (userName.isBlank()) {
            _loginResponse.postValue(
                    Resource.error(
                            data = null,
                            message = "Username cannot be  blank"
                    )
            )
            return
        }
        if (password.isBlank()) {
            _loginResponse.postValue(
                    Resource.error(
                            data = null,
                            message = "Password cannot be blank"
                    )
            )
            return
        }
        viewModelScope.launch {
            _loginResponse.postValue(Resource.loading(data = null))
            try {
                val response =userService.login(
                    UserLoginRequestBody(
                        userName,
                        password
                    )
                )
                if (!response.isSuccessful) throw Exception(response.errorBody()?.string())
                _loginResponse.postValue(Resource.success(response))
            } catch (exception: Exception) {
                _loginResponse.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }
    }
}
