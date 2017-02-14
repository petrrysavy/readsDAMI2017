package cz.cvut.fel.ida.reads.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author Petr Ryšavý
 */
public final class CollectionUtils {

    private CollectionUtils() {

    }

    public static <T> int intersectionSize(Set<T> a, Set<T> b) {
        if (a.size() > b.size())
            return intersectionSize(b, a);

        int count = 0;
        for (T t : a)
            if (b.contains(t))
                count++;
        return count;
    }

    public static int getFirstNotIJ(Set<Integer> set, int i, int j) {
        for (int val : set)
            if (val != i && val != j)
                return val;
        throw new NoSuchElementException("Cannot find value different from " + i + " and " + j + " in " + set + ".");
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Set<T> asSet(T... vals) {
        final HashSet<T> set = new HashSet<>(vals.length);
        set.addAll(Arrays.asList(vals));
        return set;
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asList(T... vals) {
        // we need this list to be modifiable - Arrays.asList returns fixed size list
        return new ArrayList<>(Arrays.asList(vals));
    }

    public static <T> List<List<T>> nLists(int n) {
        List<List<T>> list = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            list.add(new ArrayList<>());
        return list;
    }

    public static <T> void clear(List<List<T>> lists) {
        for (List l : lists)
            l.clear();
    }

    public static <K, V> Map<K, V> pairWithAll(List<K> keys, V value) {
        final Map<K, V> map = new HashMap<>(keys.size());
        for (K key : keys)
            map.put(key, value);
        return map;
    }

    public static <T, U> List<Pair<T, U>> carthesianProduct(List<T> l1, List<U> l2) {
        final List<Pair<T, U>> target = new ArrayList<>(l1.size() * l2.size());
        for (T v1 : l1)
            for (U v2 : l2)
                target.add(new Pair<>(v1, v2));
        return target;
    }

    public static <T> void growToSize(List<T> list, int size) {
        final int grow = size - list.size();

        if (grow < 0)
            throw new IllegalArgumentException("Cannot grow list with " + list.size() + " elements to size " + size);

        for (int i = 0; i < grow; i++)
            list.add(null);
    }

    public static <T> List<List<T>> zip(Collection<List<T>> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException("Cannot zip empty list.");

        final int n = list.iterator().next().size();
        final List<List<T>> zipped = new ArrayList<>(n);
        for(List<T> l : list)
            assert (l.size() == n);

        for (int i = 0; i < n; i++) {
            final List<T> oneRow = new ArrayList<>(list.size());
            for (List<T> l : list)
                oneRow.add(l.get(i));
            zipped.add(oneRow);
        }
        return zipped;
    }
    
    public static <T> List<T> flattern(Collection<? extends Collection<T>> list) {
        final List<T> out = new ArrayList<>(list.size());
        for(Collection<T> col : list)
            out.addAll(col);
        return out;
    }
    
    public static <T extends Comparable> List<T> filterOutExtremes(Collection<T> list, int extremes) {
        final List<T> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        return sorted.subList(extremes, sorted.size() - extremes);
    }
}
