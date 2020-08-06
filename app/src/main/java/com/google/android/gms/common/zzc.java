package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzp;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzacx;

public class zzc {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final zzc zzaxc = new zzc();

    zzc() {
    }

    static String zzt(@Nullable Context context, @Nullable String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("gcore_");
        sb.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        sb.append("-");
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
        }
        sb.append("-");
        if (context != null) {
            sb.append(context.getPackageName());
        }
        sb.append("-");
        if (context != null) {
            try {
                sb.append(zzacx.zzaQ(context).getPackageInfo(context.getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        return sb.toString();
    }

    public static zzc zzuz() {
        return zzaxc;
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2) {
        return zza(context, i, i2, null);
    }

    public String getErrorString(int i) {
        return zze.getErrorString(i);
    }

    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zze.getOpenSourceSoftwareLicenseInfo(context);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        int isGooglePlayServicesAvailable = zze.isGooglePlayServicesAvailable(context);
        if (zze.zzd(context, isGooglePlayServicesAvailable)) {
            return 18;
        }
        return isGooglePlayServicesAvailable;
    }

    public boolean isUserResolvableError(int i) {
        return zze.isUserRecoverableError(i);
    }

    @Nullable
    public PendingIntent zza(Context context, int i, int i2, @Nullable String str) {
        Intent zzb = zzb(context, i, str);
        if (zzb == null) {
            return null;
        }
        return PendingIntent.getActivity(context, i2, zzb, 268435456);
    }

    public int zzak(Context context) {
        return zze.zzak(context);
    }

    public void zzam(Context context) {
        zze.zzZ(context);
    }

    public void zzan(Context context) {
        zze.zzan(context);
    }

    @Nullable
    public Intent zzb(Context context, int i, @Nullable String str) {
        switch (i) {
            case 1:
            case 2:
                return (context == null || !zzi.zzaJ(context)) ? zzp.zzC("com.google.android.gms", zzt(context, str)) : zzp.zzxu();
            case 3:
                return zzp.zzdt("com.google.android.gms");
            default:
                return null;
        }
    }

    @Nullable
    @Deprecated
    public Intent zzcr(int i) {
        return zzb(null, i, null);
    }

    public boolean zzd(Context context, int i) {
        return zze.zzd(context, i);
    }

    public boolean zzs(Context context, String str) {
        return zze.zzs(context, str);
    }
}
