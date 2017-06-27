package cz.cvut.fel.ida.reads.similarity.impl;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import org.hamcrest.CoreMatchers;
import org.hamcrest.number.IsCloseTo;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class TreeNodeFowlkesMallowsIndexTest {

    private static HierarchicalTreeNode<Integer> buildTree(int a, int b, int c, int d, int e) {
        return new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(a), 1,
                        new HierarchicalTreeNode<>(b), 1,
                        null), 5,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(c), 1,
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(d), 1,
                                new HierarchicalTreeNode<>(e), 1,
                                null), 2,
                        null), 1,
                1);
    }

    @Test
    public void testGetSimilarity() {
        HierarchicalTreeNode<Integer> a = buildTree(1, 3, 5, 2, 4);
        HierarchicalTreeNode<Integer> b = buildTree(1, 4, 3, 2, 5);
        final TreeNodeFowlkesMallowsIndex<Integer> calc = new TreeNodeFowlkesMallowsIndex<>();
        calc.setK(2);
        assertThat(calc.getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(0.25, 1e-11)));
    }

    @Test
    public void testGetSimilarity2() {
        HierarchicalTreeNode<Integer> a = buildTree(1, 2, 3, 4, 5);
        HierarchicalTreeNode<Integer> b = buildTree(1, 2, 3, 4, 5);
        final TreeNodeFowlkesMallowsIndex<Integer> calc = new TreeNodeFowlkesMallowsIndex<>();
        calc.setK(2);
        assertThat(calc.getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(1.0, 1e-11)));
    }

    @Test
    public void testGetSimilarity3() {
        HierarchicalTreeNode<Integer> a = buildTree(5, 4, 1, 2, 3);
        HierarchicalTreeNode<Integer> b = buildTree(1, 2, 3, 4, 5);
        final TreeNodeFowlkesMallowsIndex<Integer> calc = new TreeNodeFowlkesMallowsIndex<>();
        calc.setK(2);
        assertThat(calc.getSimilarity(a, b), CoreMatchers.is(IsCloseTo.closeTo(0.5, 1e-11)));
    }

}
