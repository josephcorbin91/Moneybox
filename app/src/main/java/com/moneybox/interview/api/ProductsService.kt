package com.moneybox.interview.api

import com.moneybox.interview.data.entities.InvestorProduct
import com.moneybox.interview.data.request.OneOffPaymentRequestBody
import com.moneybox.interview.data.response.OneOffPaymentResponse
import retrofit2.Response
import retrofit2.http.*

interface ProductsService {
    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 7.15.0",
        "apiVersion: 3.0.0"
    )
    @GET("investorproducts")
    suspend fun getProducts(@Header("Authorization") token: String) : Response<InvestorProduct>

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 7.15.0",
        "apiVersion: 3.0.0"
    )
    @POST("oneoffpayments")
    suspend fun addOneOffPayment(
        @Header("Authorization") token: String,
        @Body oneOffPaymentRequestBody: OneOffPaymentRequestBody
    ) : Response<OneOffPaymentResponse>
}