package android.support.p001v4.media.session;

import android.annotation.TargetApi;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.p001v4.media.session.MediaControllerCompatApi21;

@TargetApi(23)
@RequiresApi(23)
/* renamed from: android.support.v4.media.session.MediaControllerCompatApi23 */
class MediaControllerCompatApi23 {

    /* renamed from: android.support.v4.media.session.MediaControllerCompatApi23$TransportControls */
    public static class TransportControls extends MediaControllerCompatApi21.TransportControls {
        public static void playFromUri(Object obj, Uri uri, Bundle bundle) {
            ((MediaController.TransportControls) obj).playFromUri(uri, bundle);
        }
    }

    MediaControllerCompatApi23() {
    }
}
