package com.moneybox.interview.api

import com.moneybox.interview.data.entities.LoginResponse
import com.moneybox.interview.data.request.UserLoginRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 7.15.0",
        "apiVersion: 3.0.0"
    )
    @POST("users/login")
    suspend fun login(@Body userLoaderRequestBody: UserLoginRequestBody) : Response<LoginResponse>
}