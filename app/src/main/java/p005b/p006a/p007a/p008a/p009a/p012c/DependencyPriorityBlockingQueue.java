package p005b.p006a.p007a.p008a.p009a.p012c;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import p005b.p006a.p007a.p008a.p009a.p012c.C0449l;
import p005b.p006a.p007a.p008a.p009a.p012c.Dependency;
import p005b.p006a.p007a.p008a.p009a.p012c.PriorityProvider;

/* renamed from: b.a.a.a.a.c.c */
public class DependencyPriorityBlockingQueue<E extends Dependency & C0449l & PriorityProvider> extends PriorityBlockingQueue<E> {
    static final int PEEK = 1;
    static final int POLL = 2;
    static final int POLL_WITH_TIMEOUT = 3;
    static final int TAKE = 0;
    final Queue<E> blockedQueue = new LinkedList();
    private final ReentrantLock lock = new ReentrantLock();

    public E take() {
        return get(0, null, null);
    }

    public E peek() {
        boolean z = false;
        try {
            return get(1, null, null);
        } catch (InterruptedException e) {
            return z;
        }
    }

    public E poll(long j, TimeUnit timeUnit) {
        return get(3, Long.valueOf(j), timeUnit);
    }

    public E poll() {
        boolean z = false;
        try {
            return get(2, null, null);
        } catch (InterruptedException e) {
            return z;
        }
    }

    public int size() {
        try {
            this.lock.lock();
            return this.blockedQueue.size() + super.size();
        } finally {
            this.lock.unlock();
        }
    }

    public <T> T[] toArray(T[] tArr) {
        try {
            this.lock.lock();
            return concatenate(super.toArray(tArr), this.blockedQueue.toArray(tArr));
        } finally {
            this.lock.unlock();
        }
    }

    public Object[] toArray() {
        try {
            this.lock.lock();
            return concatenate(super.toArray(), this.blockedQueue.toArray());
        } finally {
            this.lock.unlock();
        }
    }

    public int drainTo(Collection<? super E> collection) {
        try {
            this.lock.lock();
            int drainTo = super.drainTo(collection) + this.blockedQueue.size();
            while (!this.blockedQueue.isEmpty()) {
                collection.add(this.blockedQueue.poll());
            }
            return drainTo;
        } finally {
            this.lock.unlock();
        }
    }

    public int drainTo(Collection<? super E> collection, int i) {
        try {
            this.lock.lock();
            int drainTo = super.drainTo(collection, i);
            while (!this.blockedQueue.isEmpty() && drainTo <= i) {
                collection.add(this.blockedQueue.poll());
                drainTo++;
            }
            return drainTo;
        } finally {
            this.lock.unlock();
        }
    }

    public boolean contains(Object obj) {
        try {
            this.lock.lock();
            return super.contains(obj) || this.blockedQueue.contains(obj);
        } finally {
            this.lock.unlock();
        }
    }

    public void clear() {
        try {
            this.lock.lock();
            this.blockedQueue.clear();
            super.clear();
        } finally {
            this.lock.unlock();
        }
    }

    public boolean remove(Object obj) {
        try {
            this.lock.lock();
            return super.remove(obj) || this.blockedQueue.remove(obj);
        } finally {
            this.lock.unlock();
        }
    }

    public boolean removeAll(Collection<?> collection) {
        try {
            this.lock.lock();
            return super.removeAll(collection) | this.blockedQueue.removeAll(collection);
        } finally {
            this.lock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public E performOperation(int i, Long l, TimeUnit timeUnit) {
        switch (i) {
            case 0:
                return (Dependency) super.take();
            case 1:
                return (Dependency) super.peek();
            case 2:
                return (Dependency) super.poll();
            case 3:
                return (Dependency) super.poll(l.longValue(), timeUnit);
            default:
                return null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean offerBlockedResult(int i, E e) {
        try {
            this.lock.lock();
            if (i == 1) {
                super.remove(e);
            }
            return this.blockedQueue.offer(e);
        } finally {
            this.lock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public E get(int i, Long l, TimeUnit timeUnit) {
        E performOperation;
        while (true) {
            performOperation = performOperation(i, l, timeUnit);
            if (performOperation == null || canProcess(performOperation)) {
                return performOperation;
            }
            offerBlockedResult(i, performOperation);
        }
        return performOperation;
    }

    /* access modifiers changed from: package-private */
    public boolean canProcess(E e) {
        return e.areDependenciesMet();
    }

    public void recycleBlockedQueue() {
        try {
            this.lock.lock();
            Iterator it = this.blockedQueue.iterator();
            while (it.hasNext()) {
                Dependency bVar = (Dependency) it.next();
                if (canProcess(bVar)) {
                    super.offer(bVar);
                    it.remove();
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public <T> T[] concatenate(T[] tArr, T[] tArr2) {
        int length = tArr.length;
        int length2 = tArr2.length;
        T[] tArr3 = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), length + length2);
        System.arraycopy(tArr, 0, tArr3, 0, length);
        System.arraycopy(tArr2, 0, tArr3, length, length2);
        return tArr3;
    }
}
