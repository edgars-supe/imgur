package lv.esupe.imgur.ui.master

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import lv.esupe.imgur.data.ImgurRepo
import lv.esupe.imgur.data.model.DataList
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.utils.StringProvider
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class MasterViewModelTest {
    @Mock
    lateinit var mockImgurRepo: ImgurRepo
    @Mock
    lateinit var mockStringProvider: StringProvider
    private lateinit var subject: MasterViewModel

    @BeforeAll
    fun setUp() {
        initMocks(this)
        whenever(mockStringProvider.getString(any())).thenReturn("test")
        whenever(mockStringProvider.getString(any(), any())).thenReturn("test")
        whenever(mockImgurRepo.getGallery(any()))
            .thenReturn(Single.just(DataList(emptyList(), true, 200)))
        subject = MasterViewModel(mockImgurRepo, mockStringProvider)
    }

    @Test
    fun `It triggers correct events when items clicked`() {
        val image = ImgurItem.Image(id = "image")
        val image2 = ImgurItem.Image(id = "image2")
        val album = ImgurItem.Album(id = "album", cover = "image2", images = listOf(image2))
        val single = Single.just(DataList(listOf(image, album), true, 200))
        whenever(mockImgurRepo.getGallery(any())).thenReturn(single)
        // since data is first loaded in init and the mock is added after the VM has been created,
        // this gets the test data in (hacky)
        subject.onTryAgainClicked()
        val testObserver = subject.events.test()
        subject.onItemClicked(0)
        subject.onItemClicked(1)
        testObserver.dispose()
        testObserver.assertValueCount(2)
        assert(testObserver.values().first() is MasterEvent.ShowImage)
        assert(testObserver.values().last() is MasterEvent.ShowAlbum)
    }
}
