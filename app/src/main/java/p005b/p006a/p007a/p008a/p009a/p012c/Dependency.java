package p005b.p006a.p007a.p008a.p009a.p012c;

import java.util.Collection;

/* renamed from: b.a.a.a.a.c.b */
public interface Dependency<T> {
    void addDependency(T t);

    boolean areDependenciesMet();

    Collection<T> getDependencies();
}
