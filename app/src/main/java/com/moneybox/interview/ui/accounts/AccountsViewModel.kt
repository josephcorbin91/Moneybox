package com.moneybox.interview.ui.accounts

import android.content.SharedPreferences
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.moneybox.interview.ui.login.BEARER_TOKEN_KEY
import com.moneybox.interview.api.ProductsService
import com.moneybox.interview.data.entities.AccountUserFriendly
import com.moneybox.interview.data.entities.InvestorProduct
import com.moneybox.interview.data.repository.AccountRepository
import com.moneybox.interview.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountsViewModel @ViewModelInject constructor(
        private val productsService: ProductsService,
        private val accountRepository: AccountRepository,
        private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val accountUserFriendly: LiveData<List<AccountUserFriendly>>
        get() = accountRepository.getAllAccount()

     val totalPlanValue : LiveData<Double>
         get() = _totalPlanValue

    private val _totalPlanValue = MutableLiveData<Double>()


    val investorProductResponse: LiveData<Resource<Response<InvestorProduct>>>
        get() = _investorProductResponse
    private val _investorProductResponse = MutableLiveData<Resource<Response<InvestorProduct>>>()

    init {
        viewModelScope.launch {
            getInvestorProducts()
        }
    }
    suspend fun getInvestorProducts() {
        _investorProductResponse.postValue(Resource.loading())
        try {
            sharedPreferences.getString(BEARER_TOKEN_KEY, "")?.let { bearerToken ->
                val investorProductResponse = productsService.getProducts(bearerToken)
                _investorProductResponse.postValue(Resource.success(investorProductResponse))

                _totalPlanValue.postValue(investorProductResponse.body()?.totalPlanValue)

                val accountList = investorProductResponse.body()?.productResponses?.map {
                    AccountUserFriendly(it.id, it.product.friendlyName, it.planValue, it.moneybox)
                }
                accountList?.let { accountEntities ->
                    viewModelScope.launch {
                        accountRepository.insertAllProducts(accountEntities)
                    }
                }
                if (!investorProductResponse.isSuccessful) throw Exception(investorProductResponse.errorBody()?.string())

            }
        } catch (exception: Exception) {
            _investorProductResponse.postValue(
                    Resource.error(
                            data = null,
                            message = exception.message ?: "Error Occurred!"
                    )
            )
        }
    }
}