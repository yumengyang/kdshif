package com.google.android.gms.vision.barcode;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.safeparcel.zzc;
import com.google.android.gms.vision.barcode.Barcode;

public class zzm implements Parcelable.Creator<Barcode.WiFi> {
    static void zza(Barcode.WiFi wiFi, Parcel parcel, int i) {
        int zzaV = zzc.zzaV(parcel);
        zzc.zzc(parcel, 1, wiFi.versionCode);
        zzc.zza(parcel, 2, wiFi.ssid, false);
        zzc.zza(parcel, 3, wiFi.password, false);
        zzc.zzc(parcel, 4, wiFi.encryptionType);
        zzc.zzJ(parcel, zzaV);
    }

    /* renamed from: zzjf */
    public Barcode.WiFi createFromParcel(Parcel parcel) {
        String str = null;
        int i = 0;
        int zzaU = zzb.zzaU(parcel);
        String str2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzaU) {
            int zzaT = zzb.zzaT(parcel);
            switch (zzb.zzcW(zzaT)) {
                case 1:
                    i2 = zzb.zzg(parcel, zzaT);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, zzaT);
                    break;
                case 3:
                    str = zzb.zzq(parcel, zzaT);
                    break;
                case 4:
                    i = zzb.zzg(parcel, zzaT);
                    break;
                default:
                    zzb.zzb(parcel, zzaT);
                    break;
            }
        }
        if (parcel.dataPosition() == zzaU) {
            return new Barcode.WiFi(i2, str2, str, i);
        }
        throw new zzb.zza(new StringBuilder(37).append("Overread allowed size end=").append(zzaU).toString(), parcel);
    }

    /* renamed from: zznu */
    public Barcode.WiFi[] newArray(int i) {
        return new Barcode.WiFi[i];
    }
}
