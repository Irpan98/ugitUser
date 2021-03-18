package id.itborneo.ugithub.widget

import android.content.Intent
import android.widget.RemoteViewsService

class UgithubWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return UgithubRemoteViewsFactory(this.applicationContext)
    }
}