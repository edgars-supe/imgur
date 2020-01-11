package lv.esupe.imgur.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import lv.esupe.imgur.BuildConfig
import lv.esupe.imgur.network.HeaderAuthenticator
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideHeaderAuthenticator(): HeaderAuthenticator =
        HeaderAuthenticator(BuildConfig.IMGUR_CLIENT_ID)

    @JvmStatic
    @Provides
    fun provideClient(headerAuthenticator: HeaderAuthenticator): OkHttpClient = OkHttpClient()
        .newBuilder()
        .authenticator(headerAuthenticator)
        .build()

    @JvmStatic
    @Provides
    @Reusable
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val mediaType = MediaType.get("application/json")
        val configuration = JsonConfiguration(strictMode = false)
        val converterFactory = Json(configuration).asConverterFactory(mediaType)

        return Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/")
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }
}
