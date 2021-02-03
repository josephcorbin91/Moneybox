package com.moneybox.interview.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moneybox.interview.api.ProductsService
import com.moneybox.interview.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://api-test01.moneyboxapp.com/"
const val SHARED_PREFERENCE = "SHARED_PREFERENCE"
@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(rxJava2CallAdapterFactory)
        .client(client)
        .build()

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideRxJava2CallAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)

    @Provides
    fun provideAccountService(retrofit: Retrofit): ProductsService = retrofit.create(ProductsService::class.java)

}
