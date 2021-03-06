package com.google.android.gms.auth.api.signin.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.zzac;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONException;

public class zzl {
    private static final Lock zzajI = new ReentrantLock();
    private static zzl zzajJ;
    private final Lock zzajK = new ReentrantLock();
    private final SharedPreferences zzajL;

    zzl(Context context) {
        this.zzajL = context.getSharedPreferences("com.google.android.gms.signin", 0);
    }

    public static zzl zzaa(Context context) {
        zzac.zzw(context);
        zzajI.lock();
        try {
            if (zzajJ == null) {
                zzajJ = new zzl(context.getApplicationContext());
            }
            return zzajJ;
        } finally {
            zzajI.unlock();
        }
    }

    private String zzx(String str, String str2) {
        String valueOf = String.valueOf(":");
        return new StringBuilder(String.valueOf(str).length() + 0 + String.valueOf(valueOf).length() + String.valueOf(str2).length()).append(str).append(valueOf).append(str2).toString();
    }

    /* access modifiers changed from: package-private */
    public void zza(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzac.zzw(googleSignInAccount);
        zzac.zzw(googleSignInOptions);
        String zzqF = googleSignInAccount.zzqF();
        zzw(zzx("googleSignInAccount", zzqF), googleSignInAccount.zzqH());
        zzw(zzx("googleSignInOptions", zzqF), googleSignInOptions.zzqG());
    }

    public void zzb(GoogleSignInAccount googleSignInAccount, GoogleSignInOptions googleSignInOptions) {
        zzac.zzw(googleSignInAccount);
        zzac.zzw(googleSignInOptions);
        zzw("defaultGoogleSignInAccount", googleSignInAccount.zzqF());
        zza(googleSignInAccount, googleSignInOptions);
    }

    /* access modifiers changed from: protected */
    public String zzcA(String str) {
        this.zzajK.lock();
        try {
            return this.zzajL.getString(str, null);
        } finally {
            this.zzajK.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public void zzcB(String str) {
        if (!TextUtils.isEmpty(str)) {
            zzcC(zzx("googleSignInAccount", str));
            zzcC(zzx("googleSignInOptions", str));
        }
    }

    /* access modifiers changed from: protected */
    public void zzcC(String str) {
        this.zzajK.lock();
        try {
            this.zzajL.edit().remove(str).apply();
        } finally {
            this.zzajK.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public GoogleSignInAccount zzcy(String str) {
        GoogleSignInAccount googleSignInAccount = null;
        if (TextUtils.isEmpty(str)) {
            return googleSignInAccount;
        }
        String zzcA = zzcA(zzx("googleSignInAccount", str));
        if (zzcA == null) {
            return googleSignInAccount;
        }
        try {
            return GoogleSignInAccount.zzcu(zzcA);
        } catch (JSONException e) {
            return googleSignInAccount;
        }
    }

    /* access modifiers changed from: package-private */
    public GoogleSignInOptions zzcz(String str) {
        GoogleSignInOptions googleSignInOptions = null;
        if (TextUtils.isEmpty(str)) {
            return googleSignInOptions;
        }
        String zzcA = zzcA(zzx("googleSignInOptions", str));
        if (zzcA == null) {
            return googleSignInOptions;
        }
        try {
            return GoogleSignInOptions.zzcw(zzcA);
        } catch (JSONException e) {
            return googleSignInOptions;
        }
    }

    public GoogleSignInAccount zzrc() {
        return zzcy(zzcA("defaultGoogleSignInAccount"));
    }

    public GoogleSignInOptions zzrd() {
        return zzcz(zzcA("defaultGoogleSignInAccount"));
    }

    public void zzre() {
        String zzcA = zzcA("defaultGoogleSignInAccount");
        zzcC("defaultGoogleSignInAccount");
        zzcB(zzcA);
    }

    /* access modifiers changed from: protected */
    public void zzw(String str, String str2) {
        this.zzajK.lock();
        try {
            this.zzajL.edit().putString(str, str2).apply();
        } finally {
            this.zzajK.unlock();
        }
    }
}
