package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzv;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzmb
public class zzqc<T> implements zzqf<T> {
    private boolean zzKK;
    private T zzYe;
    private Throwable zzYf;
    private boolean zzYg;
    private final zzqg zzYh = new zzqg();
    private final Object zzrN = new Object();

    private boolean zzkL() {
        return this.zzYf != null || this.zzYg;
    }

    public boolean cancel(boolean z) {
        if (!z) {
            return false;
        }
        synchronized (this.zzrN) {
            if (zzkL()) {
                return false;
            }
            this.zzKK = true;
            this.zzYg = true;
            this.zzrN.notifyAll();
            this.zzYh.zzkM();
            return true;
        }
    }

    public T get() {
        T t;
        synchronized (this.zzrN) {
            if (!zzkL()) {
                try {
                    this.zzrN.wait();
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            if (this.zzYf != null) {
                throw new ExecutionException(this.zzYf);
            } else if (this.zzKK) {
                throw new CancellationException("CallbackFuture was cancelled.");
            } else {
                t = this.zzYe;
            }
        }
        return t;
    }

    public T get(long j, TimeUnit timeUnit) {
        T t;
        synchronized (this.zzrN) {
            if (!zzkL()) {
                try {
                    long millis = timeUnit.toMillis(j);
                    if (millis != 0) {
                        this.zzrN.wait(millis);
                    }
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            if (this.zzYf != null) {
                throw new ExecutionException(this.zzYf);
            } else if (!this.zzYg) {
                throw new TimeoutException("CallbackFuture timed out.");
            } else if (this.zzKK) {
                throw new CancellationException("CallbackFuture was cancelled.");
            } else {
                t = this.zzYe;
            }
        }
        return t;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.zzrN) {
            z = this.zzKK;
        }
        return z;
    }

    public boolean isDone() {
        boolean zzkL;
        synchronized (this.zzrN) {
            zzkL = zzkL();
        }
        return zzkL;
    }

    public void zzc(Runnable runnable) {
        this.zzYh.zzc(runnable);
    }

    public void zzd(Runnable runnable) {
        this.zzYh.zzd(runnable);
    }

    public void zze(Throwable th) {
        synchronized (this.zzrN) {
            if (!this.zzKK) {
                if (zzkL()) {
                    zzv.zzcN().zza((Throwable) new IllegalStateException("Provided CallbackFuture with multiple values."), "CallbackFuture.provideException");
                    return;
                }
                this.zzYf = th;
                this.zzrN.notifyAll();
                this.zzYh.zzkM();
            }
        }
    }

    public void zzh(@Nullable T t) {
        synchronized (this.zzrN) {
            if (!this.zzKK) {
                if (zzkL()) {
                    zzv.zzcN().zza((Throwable) new IllegalStateException("Provided CallbackFuture with multiple values."), "CallbackFuture.provideValue");
                    return;
                }
                this.zzYg = true;
                this.zzYe = t;
                this.zzrN.notifyAll();
                this.zzYh.zzkM();
            }
        }
    }
}
