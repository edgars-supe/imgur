package lv.esupe.imgur.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import lv.esupe.imgur.data.model.DataList
import lv.esupe.imgur.data.model.RawImgurItem
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.model.Section
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class ImgurRepoTest {

    @Mock private lateinit var mockImgurService: ImgurService
    private lateinit var subject: ImgurRepo

    @BeforeAll
    fun setUp() {
        initMocks(this)
        subject = ImgurRepo(mockImgurService)
    }

    @Test
    fun `It correctly converts RawImgurItem to Image, even if it has album-related fields set`() {
        val imageWithAlbumFields = RawImgurItem(id = "albumyImage", isAlbum = false, cover = "image")
        setServiceToReturn(listOf(imageWithAlbumFields))

        val testObserver = subject.getGallery(Section.Hot).test()
        val data = testObserver.values().first()
        assert(data.data.first() is ImgurItem.Image)
    }

    @Test
    fun `It does not add albums to album images list`() {
        val nestedAlbum = RawImgurItem(id = "nestedAlbum", isAlbum = true)
        val image = RawImgurItem(id = "image", isAlbum = false)
        val album = RawImgurItem(id = "album", isAlbum = true, images = listOf(nestedAlbum, image))
        setServiceToReturn(listOf(album))

        val testObserver = subject.getGallery(Section.Hot).test()
        val data = testObserver.values().first()
        val dataAlbum = data.data.first()
        assertTrue(dataAlbum is ImgurItem.Album)
        val images = (dataAlbum as ImgurItem.Album).images
        assertTrue(images.size == 1)
        assertEquals("image", images.first().id)
    }

    private fun setServiceToReturn(items: List<RawImgurItem>) {
        val dataList = Single.just(DataList(items, true, 200))
        whenever(mockImgurService.getGallery(any())).thenReturn(dataList)
    }
}
