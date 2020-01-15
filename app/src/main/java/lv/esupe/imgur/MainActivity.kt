package lv.esupe.imgur

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lv.esupe.imgur.ui.details.DetailsFragment
import lv.esupe.imgur.ui.master.MasterFragment

class MainActivity : AppCompatActivity(), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MasterFragment.newInstance())
                .commitNow()
        }
    }

    override fun showDetails(id: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DetailsFragment.newInstance())
            .addToBackStack(null)
            .commitNow()
    }

    override fun returnToMaster() {
        supportFragmentManager.popBackStack()
    }
}
