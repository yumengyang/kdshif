package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.p001v4.media.TransportMediator;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.internal.zzmb;
import com.google.android.gms.internal.zzpe;

@zzmb
class zzx implements SensorEventListener {
    private final SensorManager zzNG;
    private final Object zzNH = new Object();
    private final Display zzNI;
    private final float[] zzNJ = new float[9];
    private final float[] zzNK = new float[9];
    private float[] zzNL;
    private Handler zzNM;
    private zza zzNN;

    interface zza {
        void zzho();
    }

    zzx(Context context) {
        this.zzNG = (SensorManager) context.getSystemService("sensor");
        this.zzNI = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
    }

    private void zzh(int i, int i2) {
        float f = this.zzNK[i];
        this.zzNK[i] = this.zzNK[i2];
        this.zzNK[i2] = f;
    }

    /* access modifiers changed from: package-private */
    public int getRotation() {
        return this.zzNI.getRotation();
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        zza(sensorEvent.values);
    }

    /* access modifiers changed from: package-private */
    public void start() {
        if (this.zzNM == null) {
            Sensor defaultSensor = this.zzNG.getDefaultSensor(11);
            if (defaultSensor == null) {
                zzpe.m2432e("No Sensor of TYPE_ROTATION_VECTOR");
                return;
            }
            HandlerThread handlerThread = new HandlerThread("OrientationMonitor");
            handlerThread.start();
            this.zzNM = new Handler(handlerThread.getLooper());
            if (!this.zzNG.registerListener(this, defaultSensor, 0, this.zzNM)) {
                zzpe.m2432e("SensorManager.registerListener failed.");
                stop();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (this.zzNM != null) {
            this.zzNG.unregisterListener(this);
            this.zzNM.post(new Runnable(this) {
                public void run() {
                    Looper.myLooper().quit();
                }
            });
            this.zzNM = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void zza(zza zza2) {
        this.zzNN = zza2;
    }

    /* access modifiers changed from: package-private */
    public void zza(float[] fArr) {
        if (fArr[0] != 0.0f || fArr[1] != 0.0f || fArr[2] != 0.0f) {
            synchronized (this.zzNH) {
                if (this.zzNL == null) {
                    this.zzNL = new float[9];
                }
            }
            SensorManager.getRotationMatrixFromVector(this.zzNJ, fArr);
            switch (getRotation()) {
                case 1:
                    SensorManager.remapCoordinateSystem(this.zzNJ, 2, 129, this.zzNK);
                    break;
                case 2:
                    SensorManager.remapCoordinateSystem(this.zzNJ, 129, TransportMediator.KEYCODE_MEDIA_RECORD, this.zzNK);
                    break;
                case 3:
                    SensorManager.remapCoordinateSystem(this.zzNJ, TransportMediator.KEYCODE_MEDIA_RECORD, 1, this.zzNK);
                    break;
                default:
                    System.arraycopy(this.zzNJ, 0, this.zzNK, 0, 9);
                    break;
            }
            zzh(1, 3);
            zzh(2, 6);
            zzh(5, 7);
            synchronized (this.zzNH) {
                System.arraycopy(this.zzNK, 0, this.zzNL, 0, 9);
            }
            if (this.zzNN != null) {
                this.zzNN.zzho();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean zzb(float[] fArr) {
        boolean z = false;
        synchronized (this.zzNH) {
            if (this.zzNL != null) {
                System.arraycopy(this.zzNL, 0, fArr, 0, this.zzNL.length);
                z = true;
            }
        }
        return z;
    }
}
