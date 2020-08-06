package android.support.p001v4.p003os;

import android.annotation.TargetApi;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;

@TargetApi(16)
@RequiresApi(16)
/* renamed from: android.support.v4.os.CancellationSignalCompatJellybean */
class CancellationSignalCompatJellybean {
    CancellationSignalCompatJellybean() {
    }

    public static Object create() {
        return new CancellationSignal();
    }

    public static void cancel(Object obj) {
        ((CancellationSignal) obj).cancel();
    }
}
