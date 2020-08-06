package android.support.p001v4.widget;

import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.ListView;

/* renamed from: android.support.v4.widget.ListViewCompat */
public final class ListViewCompat {
    public static void scrollListBy(@NonNull ListView listView, int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            ListViewCompatKitKat.scrollListBy(listView, i);
        } else {
            ListViewCompatGingerbread.scrollListBy(listView, i);
        }
    }

    private ListViewCompat() {
    }
}
