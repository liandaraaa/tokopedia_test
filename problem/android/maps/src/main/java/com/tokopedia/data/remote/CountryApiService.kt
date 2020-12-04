package com.tokopedia.data.remote

import com.tokopedia.data.model.CountryApiItem
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface CountryApiService {

    @GET("name/{country_name}")
    fun getCountry(@Path("country_name") countryName:String):Observable<ArrayList<CountryApiItem>>

}

class ApiService(){
    companion object {

        fun create(): CountryApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            val httpClientBuilder = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(CustomInterceptor())
                    .addInterceptor(logger)
                    .build()


            val retrofit = Retrofit.Builder()
                    .baseUrl("https://restcountries-v1.p.rapidapi.com/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClientBuilder)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(CountryApiService::class.java)
        }
    }
}

class CustomInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
                .addHeader("x-rapidapi-key", "83b99c17b4msh1821fe8f4ef0ec1p1d9b0ajsn9b39ba7ae1a9")
                .addHeader("x-rapidapi-host", "restcountries-v1.p.rapidapi.com")
                .addHeader("useQueryString","true")
        return chain.proceed(newRequest.build())
    }
}