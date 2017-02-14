package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class HierarchicalTreeNodeTest {

    private HierarchicalTreeNode<Integer> tree;

    @Before
    public void buildTree() {
        tree = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(1), 1,
                        new HierarchicalTreeNode<>(2), 1,
                        null), 5,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(3), 1,
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(4), 1,
                                new HierarchicalTreeNode<>(5), 1,
                                null), 2,
                        null), 1,
                1);
    }

    @Test
    public void testCutAtLevel1() {
        assertThat(tree.clusterizeAtLevel(1), containsInAnyOrder(CollectionUtils.asSet(1, 2, 3, 4, 5)));
    }

    @Test
    public void testCutAtLevel2() {
        assertThat(tree.clusterizeAtLevel(2), containsInAnyOrder(CollectionUtils.asSet(1, 2), CollectionUtils.asSet(3, 4, 5)));
    }

    @Test
    public void testCutAtLevel3() {
        assertThat(tree.clusterizeAtLevel(3), containsInAnyOrder(CollectionUtils.asSet(1, 2), CollectionUtils.asSet(3), CollectionUtils.asSet(4, 5)));
    }

    @Test
    public void testCutAtLevel4() {
        assertThat(tree.clusterizeAtLevel(4), containsInAnyOrder(CollectionUtils.asSet(1, 2), CollectionUtils.asSet(3), CollectionUtils.asSet(4), CollectionUtils.asSet(5)));
    }

    @Test
    public void testCutAtLevel5() {
        assertThat(tree.clusterizeAtLevel(5), containsInAnyOrder(CollectionUtils.asSet(1), CollectionUtils.asSet(2), CollectionUtils.asSet(3), CollectionUtils.asSet(4), CollectionUtils.asSet(5)));
    }

}
