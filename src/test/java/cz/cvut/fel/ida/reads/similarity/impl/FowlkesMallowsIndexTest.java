package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.hamcrest.CoreMatchers;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class FowlkesMallowsIndexTest {

    @Test
    public void testGetSimilarity() {
        List<Set<Integer>> a = Arrays.asList(CollectionUtils.asSet(1, 3), CollectionUtils.asSet(5, 4, 2));
        List<Set<Integer>> b = Arrays.asList(CollectionUtils.asSet(1, 4), CollectionUtils.asSet(3, 2, 5));
        assertThat(new FowlkesMallowsIndex<Integer>().getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(0.25, 1e-11)));
    }

    @Test
    public void testGetSimilarity2() {
        List<Set<Integer>> a = Arrays.asList(CollectionUtils.asSet(1, 2), CollectionUtils.asSet(3, 4, 5));
        List<Set<Integer>> b = Arrays.asList(CollectionUtils.asSet(1, 2), CollectionUtils.asSet(3, 4, 5));
        assertThat(new FowlkesMallowsIndex<Integer>().getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(1.0, 1e-11)));
    }

    @Test
    public void testGetSimilarity3() {
        List<Set<Integer>> a = Arrays.asList(CollectionUtils.asSet(5, 4), CollectionUtils.asSet(1, 2, 3));
        List<Set<Integer>> b = Arrays.asList(CollectionUtils.asSet(1, 2), CollectionUtils.asSet(3, 4, 5));
        assertThat(new FowlkesMallowsIndex<Integer>().getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(0.5, 1e-11)));
    }

}
