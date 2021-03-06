package com.google.android.gms.internal;

import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import org.json.JSONException;
import org.json.JSONObject;

@zzmb
public class zzko {
    private final zzqp zzGt;
    private final String zzLX;

    public zzko(zzqp zzqp) {
        this(zzqp, "");
    }

    public zzko(zzqp zzqp, String str) {
        this.zzGt = zzqp;
        this.zzLX = str;
    }

    public void zza(int i, int i2, int i3, int i4, float f, int i5) {
        try {
            this.zzGt.zzb("onScreenInfoChanged", new JSONObject().put("width", i).put("height", i2).put("maxSizeWidth", i3).put("maxSizeHeight", i4).put("density", (double) f).put("rotation", i5));
        } catch (JSONException e) {
            zzpe.zzb("Error occured while obtaining screen information.", e);
        }
    }

    public void zzaA(String str) {
        try {
            this.zzGt.zzb("onStateChanged", new JSONObject().put(ServerProtocol.DIALOG_PARAM_STATE, str));
        } catch (JSONException e) {
            zzpe.zzb("Error occured while dispatching state change.", e);
        }
    }

    public void zzay(String str) {
        try {
            this.zzGt.zzb("onError", new JSONObject().put(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, str).put(NativeProtocol.WEB_DIALOG_ACTION, this.zzLX));
        } catch (JSONException e) {
            zzpe.zzb("Error occurred while dispatching error event.", e);
        }
    }

    public void zzaz(String str) {
        try {
            this.zzGt.zzb("onReadyEventReceived", new JSONObject().put("js", str));
        } catch (JSONException e) {
            zzpe.zzb("Error occured while dispatching ready Event.", e);
        }
    }

    public void zzb(int i, int i2, int i3, int i4) {
        try {
            this.zzGt.zzb("onSizeChanged", new JSONObject().put("x", i).put("y", i2).put("width", i3).put("height", i4));
        } catch (JSONException e) {
            zzpe.zzb("Error occured while dispatching size change.", e);
        }
    }

    public void zzc(int i, int i2, int i3, int i4) {
        try {
            this.zzGt.zzb("onDefaultPositionReceived", new JSONObject().put("x", i).put("y", i2).put("width", i3).put("height", i4));
        } catch (JSONException e) {
            zzpe.zzb("Error occured while dispatching default position.", e);
        }
    }
}
