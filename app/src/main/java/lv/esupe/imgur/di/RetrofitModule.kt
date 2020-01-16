package lv.esupe.imgur.di

import dagger.Module
import dagger.Provides
import lv.esupe.imgur.data.ImgurService
import retrofit2.Retrofit

@Module
object RetrofitModule {
    @JvmStatic
    @Provides
    fun provideImgurService(retrofit: Retrofit): ImgurService =
        retrofit.create(ImgurService::class.java)
}
