package lv.esupe.imgur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.album.AlbumFragment
import lv.esupe.imgur.ui.image.ImageFragment
import lv.esupe.imgur.ui.master.MasterFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(main_toolbar)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MasterFragment.newInstance())
                .commitNow()
        }
    }

    override fun showImage(image: ImgurItem.Image) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ImageFragment.newInstance())
            .addToBackStack(image.id)
            .commit()
    }

    override fun showAlbum(image: ImgurItem.Album) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, AlbumFragment.newInstance())
            .addToBackStack(image.id)
            .commit()
    }

    override fun returnToMaster() {
        supportFragmentManager.popBackStack()
    }
}
