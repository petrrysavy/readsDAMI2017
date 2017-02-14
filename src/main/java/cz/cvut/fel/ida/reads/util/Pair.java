package cz.cvut.fel.ida.reads.util;

import java.util.Objects;

/**
 *
 * @author Petr Ryšavý
 * @param <T>
 * @param <U>
 */
public class Pair<T, U> {

    public final T value1;
    public final U value2;

    public Pair(T value1, U value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T getValue1() {
        return value1;
    }

    public U getValue2() {
        return value2;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.value1);
        hash = 47 * hash + Objects.hashCode(this.value2);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Pair<?, ?> other = (Pair<?, ?>) obj;
        return Objects.equals(this.value1, other.value1) && Objects.equals(this.value2, other.value2);
    }

    @Override
    public String toString() {
        return "Pair(" + value1 + ", " + value2 + ')';
    }
}
