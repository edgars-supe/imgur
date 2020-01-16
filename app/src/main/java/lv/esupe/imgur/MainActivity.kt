package lv.esupe.imgur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main_activity.*
import lv.esupe.imgur.model.ImgurItem
import lv.esupe.imgur.ui.album.AlbumFragment
import lv.esupe.imgur.ui.image.ImageFragment
import lv.esupe.imgur.ui.master.MasterFragment

class MainActivity : AppCompatActivity(), Navigator, ToolbarController {

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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showImage(image: ImgurItem.Image) {
        ImageFragment.newInstance(image).show(supportFragmentManager, null)
    }

    override fun showAlbum(album: ImgurItem.Album) {
        main_app_bar.setExpanded(true, true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, AlbumFragment.newInstance(album))
            .addToBackStack(album.id)
            .commit()
    }

    override fun returnToMaster() {
        main_app_bar.setExpanded(true, true)
        supportFragmentManager.popBackStack()
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun enableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun disableBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}
