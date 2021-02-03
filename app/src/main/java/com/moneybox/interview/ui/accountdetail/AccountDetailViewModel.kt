package com.moneybox.interview.ui.accountdetail

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneybox.interview.ui.login.BEARER_TOKEN_KEY
import com.moneybox.interview.api.ProductsService
import com.moneybox.interview.data.repository.AccountRepository
import com.moneybox.interview.data.request.OneOffPaymentRequestBody
import com.moneybox.interview.data.response.OneOffPaymentResponse
import com.moneybox.interview.data.entities.AccountUserFriendly
import com.moneybox.interview.utils.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * The ViewModel used in [AccountDetailFragment].
 */
class AccountDetailViewModel @ViewModelInject constructor(
        private val accountRepository: AccountRepository,
        private val productsService: ProductsService,
        private val sharedPreferences: SharedPreferences
) : ViewModel() {

    lateinit var accountUserFriendly: LiveData<AccountUserFriendly>

    val addMoneyResponse: LiveData<Resource<Response<OneOffPaymentResponse>>>
        get() = _addMoneyResponse
    private val _addMoneyResponse = MutableLiveData<Resource<Response<OneOffPaymentResponse>>>()

    fun getAccountInformation(accountId: Long) {
        viewModelScope.launch {
            accountUserFriendly = accountRepository.getAccount(accountId)
        }
    }

    fun onAddMoneyClicked() {
        GlobalScope.launch {
            _addMoneyResponse.postValue(Resource.loading(data = null))
            try {
                sharedPreferences.getString(BEARER_TOKEN_KEY, "")?.let { bearerToken ->
                    accountUserFriendly.value?.let { accountToBeUpdated ->
                        val response = productsService.addOneOffPayment(
                            bearerToken,
                            OneOffPaymentRequestBody(10, accountToBeUpdated.id)
                        )
                        if (!response.isSuccessful) throw Exception(response.errorBody()?.string())
                        response.body()?.moneyBox?.let {moneyBoxValue ->
                            accountToBeUpdated.moneyBox = moneyBoxValue
                        }
                        accountRepository.updateAccountMoneyBox(accountToBeUpdated )
                        _addMoneyResponse.postValue(Resource.success(data = response))
                    }
                }
            } catch (exception: Exception) {
                _addMoneyResponse.postValue(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }
    }
}
