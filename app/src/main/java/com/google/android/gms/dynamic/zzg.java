package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.zze;

public abstract class zzg<T> {
    private final String zzaQo;
    private T zzaQp;

    public static class zza extends Exception {
        public zza(String str) {
            super(str);
        }

        public zza(String str, Throwable th) {
            super(str, th);
        }
    }

    protected zzg(String str) {
        this.zzaQo = str;
    }

    /* access modifiers changed from: protected */
    public final T zzaT(Context context) {
        if (this.zzaQp == null) {
            zzac.zzw(context);
            Context remoteContext = zze.getRemoteContext(context);
            if (remoteContext == null) {
                throw new zza("Could not get remote context.");
            }
            try {
                this.zzaQp = zzc((IBinder) remoteContext.getClassLoader().loadClass(this.zzaQo).newInstance());
            } catch (ClassNotFoundException e) {
                throw new zza("Could not load creator class.", e);
            } catch (InstantiationException e2) {
                throw new zza("Could not instantiate creator.", e2);
            } catch (IllegalAccessException e3) {
                throw new zza("Could not access creator.", e3);
            }
        }
        return this.zzaQp;
    }

    /* access modifiers changed from: protected */
    public abstract T zzc(IBinder iBinder);
}
