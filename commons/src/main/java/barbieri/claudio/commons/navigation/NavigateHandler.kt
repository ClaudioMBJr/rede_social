package barbieri.claudio.commons.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity

interface NavigateHandler {

    fun launchFeature(fromActivity: ComponentActivity, bundle: Bundle? = null)

}