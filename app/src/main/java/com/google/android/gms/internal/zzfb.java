package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.dynamic.zze;

@zzmb
public class zzfb {
    private static zzfb zzAj;
    private static final Object zztU = new Object();
    private zzeu zzAk;
    private RewardedVideoAd zzAl;

    private zzfb() {
    }

    public static zzfb zzff() {
        zzfb zzfb;
        synchronized (zztU) {
            if (zzAj == null) {
                zzAj = new zzfb();
            }
            zzfb = zzAj;
        }
        return zzfb;
    }

    public RewardedVideoAd getRewardedVideoAdInstance(Context context) {
        RewardedVideoAd rewardedVideoAd;
        synchronized (zztU) {
            if (this.zzAl != null) {
                rewardedVideoAd = this.zzAl;
            } else {
                this.zzAl = new zznz(context, zzeh.zzeP().zza(context, (zzjs) new zzjr()));
                rewardedVideoAd = this.zzAl;
            }
        }
        return rewardedVideoAd;
    }

    public void openDebugMenu(Context context, String str) {
        zzac.zza(this.zzAk != null, (Object) "MobileAds.initialize() must be called prior to opening debug menu.");
        try {
            this.zzAk.zzb(zze.zzA(context), str);
        } catch (RemoteException e) {
            zzpy.zzb("Unable to open debug menu.", e);
        }
    }

    public void setAppMuted(boolean z) {
        zzac.zza(this.zzAk != null, (Object) "MobileAds.initialize() must be called prior to setting the app volume.");
        try {
            this.zzAk.setAppMuted(z);
        } catch (RemoteException e) {
            zzpy.zzb("Unable to set app mute state.", e);
        }
    }

    public void setAppVolume(float f) {
        boolean z = true;
        zzac.zzb(0.0f <= f && f <= 1.0f, (Object) "The app volume must be a value between 0 and 1 inclusive.");
        if (this.zzAk == null) {
            z = false;
        }
        zzac.zza(z, (Object) "MobileAds.initialize() must be called prior to setting the app volume.");
        try {
            this.zzAk.setAppVolume(f);
        } catch (RemoteException e) {
            zzpy.zzb("Unable to set app volume.", e);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    public void zza(Context context, String str, zzfc zzfc) {
        synchronized (zztU) {
            if (this.zzAk == null) {
                if (context == null) {
                    throw new IllegalArgumentException("Context cannot be null.");
                }
                try {
                    this.zzAk = zzeh.zzeP().zzk(context);
                    this.zzAk.initialize();
                    if (str != null) {
                        this.zzAk.zzy(str);
                    }
                } catch (RemoteException e) {
                    zzpy.zzc("Fail to initialize or set applicationCode on mobile ads setting manager", e);
                }
            }
        }
    }
}
