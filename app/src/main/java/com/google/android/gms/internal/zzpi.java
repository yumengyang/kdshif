package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.internal.ClientApi;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.ads.internal.zzv;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzgl;
import com.google.android.gms.internal.zzpx;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p005b.p006a.p007a.p008a.p009a.p011b.AbstractSpiCall;

@zzmb
public class zzpi {
    public static final Handler zzWR = new zzpf(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public String zzHY;
    private zzja zzSV;
    /* access modifiers changed from: private */
    public boolean zzWS = true;
    private boolean zzWT = false;
    /* access modifiers changed from: private */
    public final Object zzrN = new Object();

    private final class zza extends BroadcastReceiver {
        private zza() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                boolean unused = zzpi.this.zzWS = true;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                boolean unused2 = zzpi.this.zzWS = false;
            }
        }
    }

    private JSONArray zza(Collection<?> collection) {
        JSONArray jSONArray = new JSONArray();
        for (Object zza2 : collection) {
            zza(jSONArray, (Object) zza2);
        }
        return jSONArray;
    }

    private void zza(JSONArray jSONArray, Object obj) {
        if (obj instanceof Bundle) {
            jSONArray.put(zzi((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONArray.put(zzP((Map) obj));
        } else if (obj instanceof Collection) {
            jSONArray.put(zza((Collection<?>) (Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONArray.put(zza((Object[]) obj));
        } else {
            jSONArray.put(obj);
        }
    }

    private void zza(JSONObject jSONObject, String str, Object obj) {
        if (obj instanceof Bundle) {
            jSONObject.put(str, zzi((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONObject.put(str, zzP((Map) obj));
        } else if (obj instanceof Collection) {
            if (str == null) {
                str = "null";
            }
            jSONObject.put(str, zza((Collection<?>) (Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, zza((Collection<?>) Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    private boolean zza(KeyguardManager keyguardManager) {
        if (keyguardManager == null) {
            return false;
        }
        return keyguardManager.inKeyguardRestrictedInputMode();
    }

    private boolean zza(PowerManager powerManager) {
        return powerManager == null || powerManager.isScreenOn();
    }

    public static void zzb(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            runnable.run();
        } else {
            zzph.zza(runnable);
        }
    }

    private boolean zzh(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        if (powerManager == null) {
            return false;
        }
        return powerManager.isScreenOn();
    }

    private JSONObject zzi(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            zza(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    private Bitmap zzq(@NonNull View view) {
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width == 0 || height == 0) {
                zzpe.zzbe("Width or height of view is zero");
                return null;
            }
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            view.layout(0, 0, width, height);
            view.draw(canvas);
            return createBitmap;
        } catch (RuntimeException e) {
            zzpe.zzb("Fail to capture the webview", e);
            return null;
        }
    }

    private Bitmap zzr(@NonNull View view) {
        RuntimeException e;
        Bitmap bitmap;
        try {
            boolean isDrawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            Bitmap drawingCache = view.getDrawingCache();
            bitmap = drawingCache != null ? Bitmap.createBitmap(drawingCache) : null;
            try {
                view.setDrawingCacheEnabled(isDrawingCacheEnabled);
            } catch (RuntimeException e2) {
                e = e2;
                zzpe.zzb("Fail to capture the web view", e);
                return bitmap;
            }
        } catch (RuntimeException e3) {
            RuntimeException runtimeException = e3;
            bitmap = null;
            e = runtimeException;
            zzpe.zzb("Fail to capture the web view", e);
            return bitmap;
        }
        return bitmap;
    }

    public void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            zzWR.post(runnable);
        }
    }

    /* access modifiers changed from: protected */
    public String zzA(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public AlertDialog.Builder zzB(Context context) {
        return new AlertDialog.Builder(context);
    }

    public zzfp zzC(Context context) {
        return new zzfp(context);
    }

    public String zzD(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            }
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
            if (runningTasks != null && !runningTasks.isEmpty()) {
                ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
                if (!(runningTaskInfo == null || runningTaskInfo.topActivity == null)) {
                    return runningTaskInfo.topActivity.getClassName();
                }
            }
            return null;
        } catch (Exception e) {
        }
    }

    public boolean zzE(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ActivityManager.RunningAppProcessInfo next = it.next();
                if (Process.myPid() == next.pid) {
                    if (next.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode() && zzh(context)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public Bitmap zzF(Context context) {
        Bitmap bitmap;
        if (!(context instanceof Activity)) {
            return null;
        }
        try {
            if (zzfx.zzDK.get().booleanValue()) {
                Window window = ((Activity) context).getWindow();
                if (window != null) {
                    bitmap = zzr(window.getDecorView().getRootView());
                }
                bitmap = null;
            } else {
                bitmap = zzq(((Activity) context).getWindow().getDecorView());
            }
        } catch (RuntimeException e) {
            zzpe.zzb("Fail to capture screen shot", e);
        }
        return bitmap;
    }

    public AudioManager zzG(Context context) {
        return (AudioManager) context.getSystemService("audio");
    }

    public float zzH(Context context) {
        AudioManager zzG = zzG(context);
        if (zzG == null) {
            return 0.0f;
        }
        int streamMaxVolume = zzG.getStreamMaxVolume(3);
        int streamVolume = zzG.getStreamVolume(3);
        if (streamMaxVolume != 0) {
            return ((float) streamVolume) / ((float) streamMaxVolume);
        }
        return 0.0f;
    }

    public int zzI(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if (applicationInfo == null) {
            return 0;
        }
        return applicationInfo.targetSdkVersion;
    }

    public boolean zzJ(Context context) {
        try {
            context.getClassLoader().loadClass(ClientApi.class.getName());
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

    public Bundle zzK(Context context) {
        zzdb zzw = zzv.zzcN().zzw(context);
        if (zzw == null) {
            return null;
        }
        return zza(zzw);
    }

    public JSONObject zzP(Map<String, ?> map) {
        try {
            JSONObject jSONObject = new JSONObject();
            for (String next : map.keySet()) {
                zza(jSONObject, next, (Object) map.get(next));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(e.getMessage());
            throw new JSONException(valueOf.length() != 0 ? "Could not convert map to JSON: ".concat(valueOf) : new String("Could not convert map to JSON: "));
        }
    }

    public Bundle zza(zzdb zzdb) {
        String str;
        String zzjR;
        String zzjS;
        if (zzdb == null) {
            return null;
        }
        if (!zzfx.zzBU.get().booleanValue() && !zzfx.zzBW.get().booleanValue()) {
            return null;
        }
        if (zzv.zzcN().zzjJ() && zzv.zzcN().zzjK()) {
            return null;
        }
        if (zzdb.zzei()) {
            zzdb.wakeup();
        }
        zzcy zzeg = zzdb.zzeg();
        if (zzeg != null) {
            String zzdV = zzeg.zzdV();
            String zzdW = zzeg.zzdW();
            String zzdX = zzeg.zzdX();
            if (zzdV != null) {
                zzv.zzcN().zzaS(zzdV);
            }
            if (zzdX != null) {
                zzv.zzcN().zzaT(zzdX);
                zzjR = zzdV;
                str = zzdW;
                zzjS = zzdX;
            } else {
                zzjR = zzdV;
                str = zzdW;
                zzjS = zzdX;
            }
        } else {
            str = null;
            zzjR = zzv.zzcN().zzjR();
            zzjS = zzv.zzcN().zzjS();
        }
        Bundle bundle = new Bundle(1);
        if (zzjS != null && zzfx.zzBW.get().booleanValue() && !zzv.zzcN().zzjK()) {
            bundle.putString("v_fp_vertical", zzjS);
        }
        if (zzjR != null && zzfx.zzBU.get().booleanValue() && !zzv.zzcN().zzjJ()) {
            bundle.putString("fingerprint", zzjR);
            if (!zzjR.equals(str)) {
                bundle.putString("v_fp", str);
            }
        }
        if (!bundle.isEmpty()) {
            return bundle;
        }
        return null;
    }

    public DisplayMetrics zza(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public PopupWindow zza(View view, int i, int i2, boolean z) {
        return new PopupWindow(view, i, i2, z);
    }

    public String zza(Context context, View view, zzec zzec) {
        if (!zzfx.zzCl.get().booleanValue()) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("width", zzec.width);
            jSONObject2.put("height", zzec.height);
            jSONObject.put("size", jSONObject2);
            jSONObject.put("activity", zzD(context));
            if (!zzec.zzzl) {
                JSONArray jSONArray = new JSONArray();
                while (view != null) {
                    ViewParent parent = view.getParent();
                    if (parent != null) {
                        int i = -1;
                        if (parent instanceof ViewGroup) {
                            i = ((ViewGroup) parent).indexOfChild(view);
                        }
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put(ShareConstants.MEDIA_TYPE, parent.getClass().getName());
                        jSONObject3.put("index_of_child", i);
                        jSONArray.put(jSONObject3);
                    }
                    view = (parent == null || !(parent instanceof View)) ? null : (View) parent;
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("parents", jSONArray);
                }
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            zzpe.zzc("Fail to get view hierarchy json", e);
            return null;
        }
    }

    public String zza(Context context, zzav zzav, String str, View view) {
        if (zzav == null) {
            return str;
        }
        try {
            Uri parse = Uri.parse(str);
            if (zzav.zzd(parse)) {
                parse = zzav.zza(parse, context, view);
            }
            return parse.toString();
        } catch (Exception e) {
            return str;
        }
    }

    public String zza(zzqp zzqp, String str) {
        return zza(zzqp.getContext(), zzqp.zzkX(), str, zzqp.getView());
    }

    public String zza(InputStreamReader inputStreamReader) {
        StringBuilder sb = new StringBuilder(8192);
        char[] cArr = new char[2048];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read == -1) {
                return sb.toString();
            }
            sb.append(cArr, 0, read);
        }
    }

    public Map<String, Integer> zza(View view, WindowManager windowManager) {
        DisplayMetrics zza2 = zza(windowManager);
        int i = zza2.widthPixels;
        int i2 = zza2.heightPixels;
        int[] iArr = new int[2];
        HashMap hashMap = new HashMap();
        view.getLocationInWindow(iArr);
        hashMap.put("xInPixels", Integer.valueOf(iArr[0]));
        hashMap.put("yInPixels", Integer.valueOf(iArr[1]));
        hashMap.put("windowWidthInPixels", Integer.valueOf(i));
        hashMap.put("windowHeightInPixels", Integer.valueOf(i2));
        return hashMap;
    }

    /* access modifiers changed from: package-private */
    public JSONArray zza(Object[] objArr) {
        JSONArray jSONArray = new JSONArray();
        for (Object zza2 : objArr) {
            zza(jSONArray, zza2);
        }
        return jSONArray;
    }

    public void zza(Activity activity, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public void zza(Activity activity, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);
        }
    }

    @TargetApi(18)
    public void zza(Context context, Uri uri) {
        try {
            Bundle bundle = new Bundle();
            if (zzfx.zzEP.get().booleanValue() && zzs.zzyE()) {
                bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, null);
            }
            bundle.putString("com.android.browser.application_id", context.getPackageName());
            context.startActivity(new Intent("android.intent.action.VIEW", uri).putExtras(bundle));
            String valueOf = String.valueOf(uri.toString());
            zzpe.zzbc(new StringBuilder(String.valueOf(valueOf).length() + 26).append("Opening ").append(valueOf).append(" in a new browser.").toString());
        } catch (ActivityNotFoundException e) {
            zzpe.zzb("No browser is found.", e);
        }
    }

    public void zza(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(zzh(context, str));
    }

    public void zza(final Context context, @Nullable final String str, String str2, Bundle bundle, boolean z) {
        if (z) {
            bundle.putString("device", zzv.zzcJ().zzkm());
            bundle.putString("eids", TextUtils.join(",", zzfx.zzfn()));
        }
        zzeh.zzeO().zza(context, str, str2, bundle, z, new zzpx.zza(this) {
            public void zzu(String str) {
                zzv.zzcJ().zzc(context, str, str);
            }
        });
    }

    public void zza(Context context, String str, List<String> list) {
        for (String zzps : list) {
            new zzps(context, str, zzps).zziw();
        }
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        zza(context, str, z, httpURLConnection, false);
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection, boolean z2) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty(AbstractSpiCall.HEADER_USER_AGENT, zzh(context, str));
        httpURLConnection.setUseCaches(z2);
    }

    public void zza(final Context context, final List<String> list) {
        if (!(context instanceof Activity) || TextUtils.isEmpty(zzbuz.zzcf((Activity) context))) {
            return;
        }
        if (list == null) {
            zzpe.m2431v("Cannot ping urls: empty list.");
        } else if (!zzgl.zzn(context)) {
            zzpe.m2431v("Cannot ping url because custom tabs is not supported");
        } else {
            final zzgl zzgl = new zzgl();
            zzgl.zza((zzgl.zza) new zzgl.zza(this) {
                public void zzfE() {
                    for (String str : list) {
                        String valueOf = String.valueOf(str);
                        zzpe.zzbd(valueOf.length() != 0 ? "Pinging url: ".concat(valueOf) : new String("Pinging url: "));
                        zzgl.mayLaunchUrl(Uri.parse(str), null, null);
                    }
                    zzgl.zzd((Activity) context);
                }

                public void zzfF() {
                }
            });
            zzgl.zze((Activity) context);
        }
    }

    public void zza(List<String> list, String str) {
        for (String zzps : list) {
            new zzps(zzps, str).zziw();
        }
    }

    @TargetApi(24)
    public boolean zza(Activity activity, Configuration configuration) {
        zzpx zzeO = zzeh.zzeO();
        int zzb = zzeO.zzb((Context) activity, configuration.screenHeightDp);
        int zzb2 = zzeO.zzb((Context) activity, configuration.screenWidthDp);
        DisplayMetrics zza2 = zza((WindowManager) activity.getApplicationContext().getSystemService("window"));
        int i = zza2.heightPixels;
        int i2 = zza2.widthPixels;
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        int dimensionPixelSize = identifier > 0 ? activity.getResources().getDimensionPixelSize(identifier) : 0;
        int intValue = zzfx.zzEX.get().intValue() * ((int) Math.round(((double) activity.getResources().getDisplayMetrics().density) + 0.5d));
        return zzb(i, dimensionPixelSize + zzb, intValue) && zzb(i2, zzb2, intValue);
    }

    public boolean zza(PackageManager packageManager, String str, String str2) {
        return packageManager.checkPermission(str2, str) == 0;
    }

    public boolean zza(View view, Context context) {
        KeyguardManager keyguardManager = null;
        Context applicationContext = context.getApplicationContext();
        PowerManager powerManager = applicationContext != null ? (PowerManager) applicationContext.getSystemService("power") : null;
        Object systemService = context.getSystemService("keyguard");
        if (systemService != null && (systemService instanceof KeyguardManager)) {
            keyguardManager = (KeyguardManager) systemService;
        }
        return zza(view, powerManager, keyguardManager);
    }

    public boolean zza(View view, PowerManager powerManager, KeyguardManager keyguardManager) {
        return view.getVisibility() == 0 && view.isShown() && zza(powerManager) && (zzv.zzcJ().zzki() || !zza(keyguardManager)) && (!zzfx.zzCM.get().booleanValue() || view.getLocalVisibleRect(new Rect()) || view.getGlobalVisibleRect(new Rect()));
    }

    public boolean zza(ClassLoader classLoader, Class<?> cls, String str) {
        boolean z = false;
        try {
            return cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
            return z;
        }
    }

    public String zzaV(String str) {
        return Uri.parse(str).buildUpon().query(null).build().toString();
    }

    public int zzaW(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(e);
            zzpe.zzbe(new StringBuilder(String.valueOf(valueOf).length() + 22).append("Could not parse value:").append(valueOf).toString());
            return 0;
        }
    }

    public boolean zzaX(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
    }

    public String zzb(String str, Map<String, String> map) {
        for (String next : map.keySet()) {
            str = str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{next}), String.format("$1%s$2", new Object[]{Uri.encode(map.get(next))}));
        }
        return str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"[^@]+"}), String.format("$1%s$2", new Object[]{""})).replaceAll("@@", "@");
    }

    public void zzb(Activity activity, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().removeOnScrollChangedListener(onScrollChangedListener);
        }
    }

    public void zzb(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Throwable th) {
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public void zzb(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (zzfx.zzCR.get().booleanValue()) {
            zza(context, str, str2, bundle, z);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean zzb(int i, int i2, int i3) {
        return Math.abs(i - i2) <= i3;
    }

    public void zzc(Context context, String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(str2);
        zza(context, str, (List<String>) arrayList);
    }

    public float zzco() {
        zzp zzcn = zzv.zzdd().zzcn();
        if (zzcn == null || !zzcn.zzcp()) {
            return 1.0f;
        }
        return zzcn.zzco();
    }

    public boolean zzcq() {
        zzp zzcn = zzv.zzdd().zzcn();
        if (zzcn != null) {
            return zzcn.zzcq();
        }
        return false;
    }

    public zzja zzd(Context context, zzqa zzqa) {
        zzja zzja;
        synchronized (this.zzrN) {
            if (this.zzSV == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                this.zzSV = new zzja(context, zzqa, zzfx.zzAR.get());
            }
            zzja = this.zzSV;
        }
        return zzja;
    }

    public void zzd(Context context, String str, String str2) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes("UTF-8"));
            openFileOutput.close();
        } catch (Exception e) {
            zzpe.zzb("Error writing to file in internal storage.", e);
        }
    }

    public Map<String, String> zzg(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String next : zzv.zzcL().zzh(uri)) {
            hashMap.put(next, uri.getQueryParameter(next));
        }
        return hashMap;
    }

    public String zzh(final Context context, String str) {
        String str2;
        synchronized (this.zzrN) {
            if (this.zzHY != null) {
                str2 = this.zzHY;
            } else if (str == null) {
                str2 = zzkj();
            } else {
                try {
                    this.zzHY = zzv.zzcL().getDefaultUserAgent(context);
                } catch (Exception e) {
                }
                if (TextUtils.isEmpty(this.zzHY)) {
                    if (!zzeh.zzeO().zzkJ()) {
                        this.zzHY = null;
                        zzWR.post(new Runnable() {
                            public void run() {
                                synchronized (zzpi.this.zzrN) {
                                    String unused = zzpi.this.zzHY = zzpi.this.zzA(context);
                                    zzpi.this.zzrN.notifyAll();
                                }
                            }
                        });
                        while (this.zzHY == null) {
                            try {
                                this.zzrN.wait();
                            } catch (InterruptedException e2) {
                                this.zzHY = zzkj();
                                String valueOf = String.valueOf(this.zzHY);
                                zzpe.zzbe(valueOf.length() != 0 ? "Interrupted, use default user agent: ".concat(valueOf) : new String("Interrupted, use default user agent: "));
                            }
                        }
                    } else {
                        try {
                            this.zzHY = zzA(context);
                        } catch (Exception e3) {
                            this.zzHY = zzkj();
                        }
                    }
                }
                String valueOf2 = String.valueOf(this.zzHY);
                this.zzHY = new StringBuilder(String.valueOf(valueOf2).length() + 11 + String.valueOf(str).length()).append(valueOf2).append(" (Mobile; ").append(str).append(")").toString();
                str2 = this.zzHY;
            }
        }
        return str2;
    }

    public int[] zzh(Activity activity) {
        Window window = activity.getWindow();
        if (window != null) {
            View findViewById = window.findViewById(16908290);
            if (findViewById != null) {
                return new int[]{findViewById.getWidth(), findViewById.getHeight()};
            }
        }
        return zzkn();
    }

    public String zzi(Context context, String str) {
        try {
            return new String(zzo.zza((InputStream) context.openFileInput(str), true), "UTF-8");
        } catch (IOException e) {
            zzpe.zzb("Error reading from internal storage.", e);
            return "";
        }
    }

    public int[] zzi(Activity activity) {
        int[] zzh = zzh(activity);
        return new int[]{zzeh.zzeO().zzc(activity, zzh[0]), zzeh.zzeO().zzc(activity, zzh[1])};
    }

    public int[] zzj(Activity activity) {
        Window window = activity.getWindow();
        if (window != null) {
            View findViewById = window.findViewById(16908290);
            if (findViewById != null) {
                return new int[]{findViewById.getTop(), findViewById.getBottom()};
            }
        }
        return zzkn();
    }

    public int[] zzk(Activity activity) {
        int[] zzj = zzj(activity);
        return new int[]{zzeh.zzeO().zzc(activity, zzj[0]), zzeh.zzeO().zzc(activity, zzj[1])};
    }

    public boolean zzki() {
        return this.zzWS;
    }

    /* access modifiers changed from: package-private */
    public String zzkj() {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("Mozilla/5.0 (Linux; U; Android");
        if (Build.VERSION.RELEASE != null) {
            stringBuffer.append(" ").append(Build.VERSION.RELEASE);
        }
        stringBuffer.append("; ").append(Locale.getDefault());
        if (Build.DEVICE != null) {
            stringBuffer.append("; ").append(Build.DEVICE);
            if (Build.DISPLAY != null) {
                stringBuffer.append(" Build/").append(Build.DISPLAY);
            }
        }
        stringBuffer.append(") AppleWebKit/533 Version/4.0 Safari/533");
        return stringBuffer.toString();
    }

    public String zzkk() {
        return UUID.randomUUID().toString();
    }

    public String zzkl() {
        UUID randomUUID = UUID.randomUUID();
        byte[] byteArray = BigInteger.valueOf(randomUUID.getLeastSignificantBits()).toByteArray();
        byte[] byteArray2 = BigInteger.valueOf(randomUUID.getMostSignificantBits()).toByteArray();
        String bigInteger = new BigInteger(1, byteArray).toString();
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(byteArray);
                instance.update(byteArray2);
                byte[] bArr = new byte[8];
                System.arraycopy(instance.digest(), 0, bArr, 0, 8);
                bigInteger = new BigInteger(1, bArr).toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return bigInteger;
    }

    public String zzkm() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? str2 : new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(str2).length()).append(str).append(" ").append(str2).toString();
    }

    /* access modifiers changed from: protected */
    public int[] zzkn() {
        return new int[]{0, 0};
    }

    public Bundle zzko() {
        Bundle bundle = new Bundle();
        try {
            if (zzfx.zzBw.get().booleanValue()) {
                Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                bundle.putParcelable("debug_memory_info", memoryInfo);
            }
            if (zzfx.zzBx.get().booleanValue()) {
                Runtime runtime = Runtime.getRuntime();
                bundle.putLong("runtime_free_memory", runtime.freeMemory());
                bundle.putLong("runtime_max_memory", runtime.maxMemory());
                bundle.putLong("runtime_total_memory", runtime.totalMemory());
            }
        } catch (Exception e) {
            zzpe.zzc("Unable to gather memory stats", e);
        }
        return bundle;
    }

    public Bitmap zzp(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    public int zzs(@Nullable View view) {
        if (view == null) {
            return -1;
        }
        ViewParent parent = view.getParent();
        while (parent != null && !(parent instanceof AdapterView)) {
            parent = parent.getParent();
        }
        if (parent == null) {
            return -1;
        }
        return ((AdapterView) parent).getPositionForView(view);
    }

    public boolean zzy(Context context) {
        boolean z;
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            zzpe.zzbe("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
            zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"keyboard"}));
            z = false;
        } else {
            z = true;
        }
        if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
            zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"keyboardHidden"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 128) == 0) {
            zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"orientation"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 256) == 0) {
            zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"screenLayout"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 512) == 0) {
            zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"uiMode"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 1024) == 0) {
            zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"screenSize"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & 2048) != 0) {
            return z;
        }
        zzpe.zzbe(String.format("com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".", new Object[]{"smallestScreenSize"}));
        return false;
    }

    public boolean zzz(Context context) {
        if (this.zzWT) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver(new zza(), intentFilter);
        this.zzWT = true;
        return true;
    }
}
