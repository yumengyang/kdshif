package android.support.p001v4.p003os;

import android.annotation.TargetApi;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

@TargetApi(13)
@RequiresApi(13)
/* renamed from: android.support.v4.os.ParcelableCompatCreatorHoneycombMR2Stub */
/* compiled from: ParcelableCompatHoneycombMR2 */
class ParcelableCompatCreatorHoneycombMR2Stub {
    ParcelableCompatCreatorHoneycombMR2Stub() {
    }

    static <T> Parcelable.Creator<T> instantiate(ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
        return new ParcelableCompatCreatorHoneycombMR2(parcelableCompatCreatorCallbacks);
    }
}
