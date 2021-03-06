package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.common.stats.zza;
import com.google.android.gms.internal.zzlc;
import com.google.android.gms.internal.zzmb;
import com.google.android.gms.internal.zzpe;

@zzmb
public final class zzg extends zzlc.zza implements ServiceConnection {
    private Context mContext;
    private int mResultCode;
    zzb zzOR;
    private String zzOX;
    private zzf zzPb;
    private boolean zzPh = false;
    private Intent zzPi;

    public zzg(Context context, String str, boolean z, int i, Intent intent, zzf zzf) {
        this.zzOX = str;
        this.mResultCode = i;
        this.zzPi = intent;
        this.zzPh = z;
        this.mContext = context;
        this.zzPb = zzf;
    }

    public void finishPurchase() {
        int zzd = zzv.zzcX().zzd(this.zzPi);
        if (this.mResultCode == -1 && zzd == 0) {
            this.zzOR = new zzb(this.mContext);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            zza.zzyc().zza(this.mContext, intent, (ServiceConnection) this, 1);
        }
    }

    public String getProductId() {
        return this.zzOX;
    }

    public Intent getPurchaseData() {
        return this.zzPi;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public boolean isVerified() {
        return this.zzPh;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzpe.zzbd("In-app billing service connected.");
        this.zzOR.zzV(iBinder);
        String zzaE = zzv.zzcX().zzaE(zzv.zzcX().zze(this.zzPi));
        if (zzaE != null) {
            if (this.zzOR.zzl(this.mContext.getPackageName(), zzaE) == 0) {
                zzh.zzq(this.mContext).zza(this.zzPb);
            }
            zza.zzyc().zza(this.mContext, this);
            this.zzOR.destroy();
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        zzpe.zzbd("In-app billing service disconnected.");
        this.zzOR.destroy();
    }
}
