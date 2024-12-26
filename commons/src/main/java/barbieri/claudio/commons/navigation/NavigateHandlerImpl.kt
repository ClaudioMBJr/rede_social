package barbieri.claudio.commons.navigation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class NavigateHandlerImpl(private val activity: Class<*>) : NavigateHandler {

    override fun launchFeature(
        fromActivity: ComponentActivity,
        bundle: Bundle?
    ) {
        with(Intent(fromActivity, activity).apply {
            putExtra(
                "bundle",
                bundle
            ).takeIf { bundle != null }
        }) {
            fromActivity.startActivity(this)
        }
    }

}