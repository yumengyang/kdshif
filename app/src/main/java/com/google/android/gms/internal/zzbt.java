package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf;

public class zzbt extends zzby {
    public zzbt(zzbc zzbc, String str, String str2, zzaf.zza zza, int i, int i2) {
        super(zzbc, str, str2, zza, i, i2);
    }

    /* access modifiers changed from: protected */
    public void zzbe() {
        synchronized (this.zzra) {
            zzbb zzbb = new zzbb((String) this.zzrj.invoke(null, new Object[0]));
            this.zzra.zzbL = zzbb.zzqp;
            this.zzra.zzbM = zzbb.zzqq;
        }
    }
}
