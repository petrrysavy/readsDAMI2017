package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.IteratorWrapper;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class HashMultisetTest {

    private HashMultiset<Integer> multiset;

    @Before
    public void init() {
        multiset = new HashMultiset<>();
        multiset.add(0);
        multiset.add(1);
        multiset.add(0);
        multiset.add(2);
        multiset.add(4);
        multiset.add(1);
        multiset.add(0);
        multiset.add(4);
    }

    @Test
    public void constructEmptySet() {
        multiset = new HashMultiset<>();
        assertThat(multiset.size(), is(equalTo(0)));
        assertThat(multiset.contains(0), is(false));
        assertThat(multiset.count(0), is(equalTo(0)));
        assertThat(multiset.iterator().hasNext(), is(false));
    }

    @Test
    public void constructViaCollection() {
        HashMultiset<Integer> multiset2 = new HashMultiset<>(CollectionUtils.asList(0, 1, 0, 2, 4, 1, 0, 4));
        assertThat(multiset2, is(equalTo(multiset)));
    }

    @Test
    public void constructViaVarargs() {
        HashMultiset<Integer> multiset2 = new HashMultiset<>(0, 1, 0, 2, 4, 1, 0, 4);
        assertThat(multiset2, is(equalTo(multiset)));
    }

    @Test
    public void testIterator() {
        assertThat(new IteratorWrapper<>(multiset.iterator()), containsInAnyOrder(0, 0, 0, 1, 1, 2, 4, 4));
    }

    @Test
    public void testCount() {
        assertThat(multiset.count(0), is(equalTo(3)));
        assertThat(multiset.count(1), is(equalTo(2)));
        assertThat(multiset.count(2), is(equalTo(1)));
        assertThat(multiset.count(3), is(equalTo(0)));
        assertThat(multiset.count(4), is(equalTo(2)));
    }

    @Test
    public void testContains() {
        assertThat(multiset.contains(0), is(true));
        assertThat(multiset.contains(1), is(true));
        assertThat(multiset.contains(2), is(true));
        assertThat(multiset.contains(3), is(false));
        assertThat(multiset.contains(4), is(true));
    }

    @Test
    public void testSize() {
        assertThat(multiset.size(), is(equalTo(8)));
    }

    @Test
    public void testUniqueSize() {
        assertThat(multiset.uniqueSize(), is(equalTo(4)));
    }

    @Test
    public void testClear() {
        multiset.clear();
        assertThat(multiset.size(), is(equalTo(0)));
        assertThat(multiset.iterator().hasNext(), is(false));
    }

    @Test
    public void testAdd() {
        multiset.add(5);
        assertThat(multiset, containsInAnyOrder(0, 0, 0, 1, 1, 2, 4, 4, 5));
    }

    @Test
    public void testAddCount() {
        multiset.add(5, 10);
        assertThat(multiset.count(5), is(equalTo(10)));
    }

    @Test
    public void testToSet() {
        assertThat(multiset.toSet(), is(equalTo(CollectionUtils.asSet(0, 1, 2, 4))));
    }

    @Test
    public void testRemove() {
        multiset.remove(2);
        multiset.remove(0);
        multiset.remove(5);
        assertThat(multiset.size(), is(equalTo(6)));
        assertThat(multiset.count(0), is(equalTo(2)));
        assertThat(multiset.count(2), is(equalTo(0)));
        assertThat(multiset, containsInAnyOrder(0, 0, 1, 1, 4, 4));
    }

    @Test
    public void testUnion() {
        multiset.clear();
        multiset.add(1, 3);
        multiset.add(2, 5);
        multiset.add(0);
        Multiset<Integer> multiset2 = new HashMultiset<>();
        multiset2.add(3);
        multiset2.add(2, 5);
        assertThat(multiset.size(), is(equalTo(9)));
        assertThat(multiset2.size(), is(equalTo(6)));
        Multiset<Integer> union = multiset.union(multiset2);
        assertThat(union.size(), is(equalTo(15)));
        assertThat(union.count(-1), is(equalTo(0)));
        assertThat(union.count(0), is(equalTo(1)));
        assertThat(union.count(1), is(equalTo(3)));
        assertThat(union.count(2), is(equalTo(10)));
        assertThat(union.count(3), is(equalTo(1)));
        assertThat(union.count(4), is(equalTo(0)));
    }

    @Test
    public void testClone() {
        Multiset<Integer> clone = multiset.clone();
        assertThat(clone, is(equalTo(multiset)));
    }
}
