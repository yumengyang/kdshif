package com.google.android.gms.ads;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzdt;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzpy;

class BaseAdView extends ViewGroup {
    protected final zzez zzrJ;

    public BaseAdView(Context context, int i) {
        super(context);
        this.zzrJ = new zzez(this, i);
    }

    public BaseAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.zzrJ = new zzez(this, attributeSet, false, i);
    }

    public BaseAdView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.zzrJ = new zzez(this, attributeSet, false, i2);
    }

    public void destroy() {
        this.zzrJ.destroy();
    }

    public AdListener getAdListener() {
        return this.zzrJ.getAdListener();
    }

    public AdSize getAdSize() {
        return this.zzrJ.getAdSize();
    }

    public String getAdUnitId() {
        return this.zzrJ.getAdUnitId();
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.zzrJ.getInAppPurchaseListener();
    }

    public String getMediationAdapterClassName() {
        return this.zzrJ.getMediationAdapterClassName();
    }

    public boolean isLoading() {
        return this.zzrJ.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public void loadAd(AdRequest adRequest) {
        this.zzrJ.zza(adRequest.zzbq());
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null && childAt.getVisibility() != 8) {
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i5 = ((i3 - i) - measuredWidth) / 2;
            int i6 = ((i4 - i2) - measuredHeight) / 2;
            childAt.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        AdSize adSize;
        int i3;
        int i4 = 0;
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            AdSize adSize2 = null;
            try {
                adSize = getAdSize();
            } catch (NullPointerException e) {
                zzpy.zzb("Unable to retrieve ad size.", e);
                adSize = adSize2;
            }
            if (adSize != null) {
                Context context = getContext();
                i3 = adSize.getWidthInPixels(context);
                i4 = adSize.getHeightInPixels(context);
            } else {
                i3 = 0;
            }
        } else {
            measureChild(childAt, i, i2);
            i3 = childAt.getMeasuredWidth();
            i4 = childAt.getMeasuredHeight();
        }
        setMeasuredDimension(View.resolveSize(Math.max(i3, getSuggestedMinimumWidth()), i), View.resolveSize(Math.max(i4, getSuggestedMinimumHeight()), i2));
    }

    public void pause() {
        this.zzrJ.pause();
    }

    public void resume() {
        this.zzrJ.resume();
    }

    public void setAdListener(AdListener adListener) {
        this.zzrJ.setAdListener(adListener);
        if (adListener != null && (adListener instanceof zzdt)) {
            this.zzrJ.zza((zzdt) adListener);
        } else if (adListener == null) {
            this.zzrJ.zza((zzdt) null);
        }
    }

    public void setAdSize(AdSize adSize) {
        this.zzrJ.setAdSizes(adSize);
    }

    public void setAdUnitId(String str) {
        this.zzrJ.setAdUnitId(str);
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        this.zzrJ.setInAppPurchaseListener(inAppPurchaseListener);
    }

    public void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String str) {
        this.zzrJ.setPlayStorePurchaseParams(playStorePurchaseListener, str);
    }
}
