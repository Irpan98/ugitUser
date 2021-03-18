package id.itborneo.ugithub.widget

import android.content.Intent
import android.widget.RemoteViewsService
import id.itborneo.ugithub.widget.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}