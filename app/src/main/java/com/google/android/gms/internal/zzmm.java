package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzmb
public final class zzmm extends zza {
    public static final Parcelable.Creator<zzmm> CREATOR = new zzmn();
    public final int versionCode;
    public final boolean zzSi;
    @Nullable
    public final List<String> zzSj;

    public zzmm() {
        this(1, false, Collections.emptyList());
    }

    public zzmm(int i, boolean z, List<String> list) {
        this.versionCode = i;
        this.zzSi = z;
        this.zzSj = list;
    }

    public zzmm(boolean z) {
        this(1, z, Collections.emptyList());
    }

    public zzmm(boolean z, List<String> list) {
        this(1, z, list);
    }

    @Nullable
    public static zzmm zzg(JSONObject jSONObject) {
        if (jSONObject == null) {
            return new zzmm();
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("reporting_urls");
        ArrayList arrayList = new ArrayList();
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                try {
                    arrayList.add(optJSONArray.getString(i));
                } catch (JSONException e) {
                    zzpe.zzc("Error grabbing url from json.", e);
                }
            }
        }
        return new zzmm(jSONObject.optBoolean("enable_protection"), arrayList);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzmn.zza(this, parcel, i);
    }
}
