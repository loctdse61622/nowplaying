package tiki.com.nowplaying.di.module

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tiki.com.nowplaying.BuildConfig
import tiki.com.nowplaying.api.MovieService
import javax.inject.Singleton

/**
 * Created by Admin on 5/4/2018.
 */
@Module
class NetworkModule(val baseUrl: String) {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder().setLenient()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, requestInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().cache(cache)
                .addInterceptor(LoggingInterceptor.Builder()
                        .loggable(BuildConfig.DEBUG)
                        .log(Platform.INFO)
                        .setLevel(Level.BODY)
                        .request("Request")
                        .response("Response")
                        .build())
                .addInterceptor(requestInterceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url: HttpUrl = request.url()
                    .newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .build()
            val newRequest = request.newBuilder().url(url).build()
            return@Interceptor chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)

}