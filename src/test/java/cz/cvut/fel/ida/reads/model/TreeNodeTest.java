package cz.cvut.fel.ida.reads.model;

import cz.cvut.fel.ida.reads.util.CollectionUtils;
import cz.cvut.fel.ida.reads.util.IteratorWrapper;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class TreeNodeTest {

    private TreeNode<Integer> treeNode;

    @Before
    public void init() {
        treeNode = new TreeNode<>(
                new TreeNode<>(new TreeNode<>(2), new TreeNode<>(3), 1),
                new TreeNode<>(new TreeNode<>(4), null, 5),
                0);
    }

    public TreeNodeTest() {
    }

    @Test
    public void testIterator() {
        assertThat(new IteratorWrapper<>(treeNode.iterator()), containsInAnyOrder(0, 1, 2, 3, 4, 5));
    }

    @Test
    public void testDepth() {
        assertThat(treeNode.depth(), is(equalTo(2)));
    }

    @Test
    public void testIsLeaf() {
        assertThat(treeNode.isLeaf(), is(false));
        assertThat(treeNode.left.isLeaf(), is(false));
        assertThat(treeNode.right.isLeaf(), is(false));
        assertThat(treeNode.left.left.isLeaf(), is(true));
        assertThat(treeNode.left.right.isLeaf(), is(true));
        assertThat(treeNode.right.left.isLeaf(), is(true));
    }

    @Test
    public void testGetLeavesSet() {
        assertThat(treeNode.getLeavesSet(), containsInAnyOrder(2, 3, 4));
    }

    @Test
    public void testClusterizeAtLevel() {
        assertThat(treeNode.clusterizeAtLevel(2),
                containsInAnyOrder(CollectionUtils.asSet(2, 3), CollectionUtils.asSet(4)));
    }

    @Test
    public void testCutAtLevel() {
        Set<TreeNode<Integer>> cut = treeNode.cutAtLevel(2);
        assertThat(cut.size(), is(equalTo(2)));
        Iterator<TreeNode<Integer>> it = cut.iterator();
        Integer first = it.next().value;
        Integer second = it.next().value;
        assertThat(first, anyOf(equalTo(1), equalTo(5)));
        assertThat(second, anyOf(equalTo(1), equalTo(5)));
        assertThat(first, is(not(equalTo(second))));
    }

    @Test
    public void testTracePath() {
        List<TreeNode<Integer>> path = treeNode.tracePath(3, null);
        assertThat(path.size(), is(equalTo(3)));
        assertThat(path.get(0).value, is(equalTo(3)));
        assertThat(path.get(1).value, is(equalTo(1)));
        assertThat(path.get(2).value, is(equalTo(0)));
    }

}
