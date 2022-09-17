package xyz.johannainggolan.appnetworking.apiinterface

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import xyz.johannainggolan.appnetworking.models.Photo

interface RestService {

    @GET("/photos")
    suspend fun getPhotos(): Response<List<Photo>>?

    companion object{
        var restService: RestService? = null
        var urlMainDomain = ""
        fun getInstance() : RestService {
            if (restService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                restService = retrofit.create(RestService::class.java)
            }
            return restService!!
        }
    }
}