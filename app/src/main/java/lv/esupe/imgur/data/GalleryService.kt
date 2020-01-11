package lv.esupe.imgur.data

import io.reactivex.Single
import lv.esupe.imgur.model.DataList
import lv.esupe.imgur.model.Image
import retrofit2.http.GET
import retrofit2.http.Path


interface GalleryService {
    @GET("gallery/{section}")
    fun getGallery(@Path("section") section: String): Single<DataList<Image>>
}
