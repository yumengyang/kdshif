package android.support.p001v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.OverScroller;

@TargetApi(14)
@RequiresApi(14)
/* renamed from: android.support.v4.widget.ScrollerCompatIcs */
class ScrollerCompatIcs {
    ScrollerCompatIcs() {
    }

    public static float getCurrVelocity(Object obj) {
        return ((OverScroller) obj).getCurrVelocity();
    }
}
