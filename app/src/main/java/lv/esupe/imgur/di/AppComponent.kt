package lv.esupe.imgur.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import lv.esupe.imgur.ui.album.AlbumViewModel
import lv.esupe.imgur.ui.image.ImageViewModel
import lv.esupe.imgur.ui.master.MasterViewModel

@Component(
    modules = [NetworkModule::class, RetrofitModule::class]
)
interface AppComponent {
    val masterViewModel: MasterViewModel

    val imageViewModel: ImageViewModel

    val albumViewModel: AlbumViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
