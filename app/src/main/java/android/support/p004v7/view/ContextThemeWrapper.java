package android.support.p004v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.p004v7.appcompat.C0309R;
import android.view.LayoutInflater;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.view.ContextThemeWrapper */
public class ContextThemeWrapper extends ContextWrapper {
    private LayoutInflater mInflater;
    private Resources.Theme mTheme;
    private int mThemeResource;

    public ContextThemeWrapper(Context context, @StyleRes int i) {
        super(context);
        this.mThemeResource = i;
    }

    public ContextThemeWrapper(Context context, Resources.Theme theme) {
        super(context);
        this.mTheme = theme;
    }

    public void setTheme(int i) {
        if (this.mThemeResource != i) {
            this.mThemeResource = i;
            initializeTheme();
        }
    }

    public int getThemeResId() {
        return this.mThemeResource;
    }

    public Resources.Theme getTheme() {
        if (this.mTheme != null) {
            return this.mTheme;
        }
        if (this.mThemeResource == 0) {
            this.mThemeResource = C0309R.C0312style.Theme_AppCompat_Light;
        }
        initializeTheme();
        return this.mTheme;
    }

    public Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.mInflater;
    }

    /* access modifiers changed from: protected */
    public void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, true);
    }

    private void initializeTheme() {
        boolean z = this.mTheme == null;
        if (z) {
            this.mTheme = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.mTheme.setTo(theme);
            }
        }
        onApplyThemeResource(this.mTheme, this.mThemeResource, z);
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }
}
