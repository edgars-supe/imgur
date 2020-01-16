package lv.esupe.imgur.data

import io.reactivex.Single
import lv.esupe.imgur.data.model.DataList
import lv.esupe.imgur.data.model.RawImgurItem
import retrofit2.http.GET
import retrofit2.http.Path


interface ImgurService {
    @GET("gallery/{section}")
    fun getGallery(@Path("section") section: String): Single<DataList<RawImgurItem>>
}
