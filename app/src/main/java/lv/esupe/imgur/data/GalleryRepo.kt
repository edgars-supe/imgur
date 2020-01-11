package lv.esupe.imgur.data

import retrofit2.Retrofit
import javax.inject.Inject

class GalleryRepo @Inject constructor(
    private val retrofit: Retrofit
) {
    private val service = retrofit.create(GalleryService::class.java)

    fun getGallery(section: String) = service.getGallery(section)
}
