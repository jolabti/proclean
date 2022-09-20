package xyz.johannainggolan.appnetworking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import xyz.johannainggolan.appnetworking.BuildConfig.DEBUG
import xyz.johannainggolan.appnetworking.apiinterface.RestService
import xyz.johannainggolan.appnetworking.viewmodels.PhotoViewModel
import java.util.concurrent.TimeUnit


val serviceModule = module {

    fun providePhotoService(retrofit: Retrofit): RestService {
        return retrofit.create(RestService::class.java)
    }
    single { providePhotoService(get()) }
}

val networkConnectionModule = module {
    val connectTimeout: Long = 30// 20s
    val readTimeout: Long = 30 // 20s

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    single { provideHttpClient() }

    fun provideRetrofitLibs(okHttpClient: OkHttpClient, baseDomainUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseDomainUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    single {
        val baseUrl = "https://jsonplaceholder.typicode.com/"
        provideRetrofitLibs(get(), baseUrl)
    }

}

val viewModuleCollections = module {
    viewModel {
        PhotoViewModel(get())
    }
}

