package lv.esupe.imgur

import android.app.Application
import lv.esupe.imgur.di.AppComponent
import lv.esupe.imgur.di.DaggerAppComponent

class ImgurApp : Application() {
    val component: AppComponent by lazy { DaggerAppComponent.factory().create(this) }
}
