package com.google.android.gms.ads;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.internal.zzdv;
import com.google.android.gms.internal.zzeb;
import com.google.android.gms.internal.zzeh;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzen;
import com.google.android.gms.internal.zzet;
import com.google.android.gms.internal.zzey;
import com.google.android.gms.internal.zzgw;
import com.google.android.gms.internal.zzhj;
import com.google.android.gms.internal.zzhk;
import com.google.android.gms.internal.zzho;
import com.google.android.gms.internal.zzhp;
import com.google.android.gms.internal.zzhq;
import com.google.android.gms.internal.zzhr;
import com.google.android.gms.internal.zzjr;
import com.google.android.gms.internal.zzpy;

public class AdLoader {
    private final Context mContext;
    private final zzeb zzrB;
    private final zzem zzrC;

    public static class Builder {
        private final Context mContext;
        private final zzen zzrD;

        Builder(Context context, zzen zzen) {
            this.mContext = context;
            this.zzrD = zzen;
        }

        public Builder(Context context, String str) {
            this((Context) zzac.zzb(context, (Object) "context cannot be null"), zzeh.zzeP().zzb(context, str, new zzjr()));
        }

        public AdLoader build() {
            try {
                return new AdLoader(this.mContext, this.zzrD.zzci());
            } catch (RemoteException e) {
                zzpy.zzb("Failed to build AdLoader.", e);
                return null;
            }
        }

        public Builder forAppInstallAd(NativeAppInstallAd.OnAppInstallAdLoadedListener onAppInstallAdLoadedListener) {
            try {
                this.zzrD.zza((zzhj) new zzho(onAppInstallAdLoadedListener));
            } catch (RemoteException e) {
                zzpy.zzc("Failed to add app install ad listener", e);
            }
            return this;
        }

        public Builder forContentAd(NativeContentAd.OnContentAdLoadedListener onContentAdLoadedListener) {
            try {
                this.zzrD.zza((zzhk) new zzhp(onContentAdLoadedListener));
            } catch (RemoteException e) {
                zzpy.zzc("Failed to add content ad listener", e);
            }
            return this;
        }

        public Builder forCustomTemplateAd(String str, NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener onCustomTemplateAdLoadedListener, NativeCustomTemplateAd.OnCustomClickListener onCustomClickListener) {
            try {
                this.zzrD.zza(str, new zzhr(onCustomTemplateAdLoadedListener), onCustomClickListener == null ? null : new zzhq(onCustomClickListener));
            } catch (RemoteException e) {
                zzpy.zzc("Failed to add custom template ad listener", e);
            }
            return this;
        }

        public Builder withAdListener(AdListener adListener) {
            try {
                this.zzrD.zzb((zzel) new zzdv(adListener));
            } catch (RemoteException e) {
                zzpy.zzc("Failed to set AdListener.", e);
            }
            return this;
        }

        public Builder withCorrelator(@NonNull Correlator correlator) {
            zzac.zzw(correlator);
            try {
                this.zzrD.zzb((zzet) correlator.zzbr());
            } catch (RemoteException e) {
                zzpy.zzc("Failed to set correlator.", e);
            }
            return this;
        }

        public Builder withNativeAdOptions(NativeAdOptions nativeAdOptions) {
            try {
                this.zzrD.zza(new zzgw(nativeAdOptions));
            } catch (RemoteException e) {
                zzpy.zzc("Failed to specify native ad options", e);
            }
            return this;
        }
    }

    AdLoader(Context context, zzem zzem) {
        this(context, zzem, zzeb.zzey());
    }

    AdLoader(Context context, zzem zzem, zzeb zzeb) {
        this.mContext = context;
        this.zzrC = zzem;
        this.zzrB = zzeb;
    }

    private void zza(zzey zzey) {
        try {
            this.zzrC.zzf(this.zzrB.zza(this.mContext, zzey));
        } catch (RemoteException e) {
            zzpy.zzb("Failed to load ad.", e);
        }
    }

    public String getMediationAdapterClassName() {
        try {
            return this.zzrC.getMediationAdapterClassName();
        } catch (RemoteException e) {
            zzpy.zzc("Failed to get the mediation adapter class name.", e);
            return null;
        }
    }

    public boolean isLoading() {
        try {
            return this.zzrC.isLoading();
        } catch (RemoteException e) {
            zzpy.zzc("Failed to check if ad is loading.", e);
            return false;
        }
    }

    @RequiresPermission("android.permission.INTERNET")
    public void loadAd(AdRequest adRequest) {
        zza(adRequest.zzbq());
    }

    public void loadAd(PublisherAdRequest publisherAdRequest) {
        zza(publisherAdRequest.zzbq());
    }
}
