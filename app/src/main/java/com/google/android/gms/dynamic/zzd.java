package com.google.android.gms.dynamic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface zzd extends IInterface {

    public static abstract class zza extends Binder implements zzd {

        /* renamed from: com.google.android.gms.dynamic.zzd$zza$zza  reason: collision with other inner class name */
        private static class C1697zza implements zzd {
            private IBinder zzrp;

            C1697zza(IBinder iBinder) {
                this.zzrp = iBinder;
            }

            public IBinder asBinder() {
                return this.zzrp;
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.dynamic.IObjectWrapper");
        }

        public static zzd zzcd(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzd)) ? new C1697zza(iBinder) : (zzd) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            switch (i) {
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.dynamic.IObjectWrapper");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }
}
