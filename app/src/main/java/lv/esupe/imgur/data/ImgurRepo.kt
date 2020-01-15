package lv.esupe.imgur.data

import retrofit2.Retrofit
import javax.inject.Inject

class ImgurRepo @Inject constructor(
    private val retrofit: Retrofit
) {
    private val service = retrofit.create(ImgurService::class.java)

    fun getGallery(section: String) = service.getGallery(section)
}
