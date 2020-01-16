package lv.esupe.imgur.data

import lv.esupe.imgur.data.model.DataList
import lv.esupe.imgur.data.model.RawImgurItem
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.model.Section
import retrofit2.Retrofit
import javax.inject.Inject

class ImgurRepo @Inject constructor(
    private val retrofit: Retrofit
) {
    private val service = retrofit.create(ImgurService::class.java)

    fun getGallery(section: Section) = service.getGallery(section.id)
        .map { data -> data.toModel() }

    private fun DataList<RawImgurItem>.toModel(): DataList<ImgurItem> =
        DataList(
            data = data.map { it.toModel() },
            success = success,
            status = status
        )
}
