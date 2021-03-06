package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzg;

public final class zzabx {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Common.API", zzahd, zzahc);
    public static final zzaby zzaFp = new zzabz();
    public static final Api.zzf<zzacb> zzahc = new Api.zzf<>();
    private static final Api.zza<zzacb, Api.ApiOptions.NoOptions> zzahd = new Api.zza<zzacb, Api.ApiOptions.NoOptions>() {
        /* renamed from: zzf */
        public zzacb zza(Context context, Looper looper, zzg zzg, Api.ApiOptions.NoOptions noOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return new zzacb(context, looper, zzg, connectionCallbacks, onConnectionFailedListener);
        }
    };
}
