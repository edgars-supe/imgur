package lv.esupe.imgur.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import lv.esupe.imgur.details.DetailsViewModel
import lv.esupe.imgur.master.MasterViewModel

@Component(
    modules = [NetworkModule::class]
)
interface AppComponent {
    val masterViewModel: MasterViewModel

    val detailsViewModel: DetailsViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
