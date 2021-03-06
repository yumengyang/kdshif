package com.google.android.gms.ads.internal.purchase;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;

public class zza implements Parcelable.Creator<GInAppPurchaseManagerInfoParcel> {
    static void zza(GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, gInAppPurchaseManagerInfoParcel.versionCode);
        zzc.zza(parcel, 3, gInAppPurchaseManagerInfoParcel.zzil(), false);
        zzc.zza(parcel, 4, gInAppPurchaseManagerInfoParcel.zzim(), false);
        zzc.zza(parcel, 5, gInAppPurchaseManagerInfoParcel.zzin(), false);
        zzc.zza(parcel, 6, gInAppPurchaseManagerInfoParcel.zzik(), false);
        zzc.zzJ(parcel, zzaV);
    }

    /* renamed from: zzM */
    public GInAppPurchaseManagerInfoParcel[] newArray(int i) {
        return new GInAppPurchaseManagerInfoParcel[i];
    }

    /* renamed from: zzl */
    public GInAppPurchaseManagerInfoParcel createFromParcel(Parcel parcel) {
        IBinder iBinder = null;
        int zzaU = zzb.zzaU(parcel);
        int i = 0;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                case 3:
                    iBinder4 = zzb.zzr(parcel, zzaT);
                    break;
                case 4:
                    iBinder3 = zzb.zzr(parcel, zzaT);
                    break;
                case 5:
                    iBinder2 = zzb.zzr(parcel, zzaT);
                    break;
                case 6:
                    iBinder = zzb.zzr(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new GInAppPurchaseManagerInfoParcel(i, iBinder4, iBinder3, iBinder2, iBinder);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaU).toString(), parcel);
    }
}
