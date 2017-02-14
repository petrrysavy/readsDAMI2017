package cz.cvut.fel.ida.reads.algo;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.model.TreeNode;
import cz.cvut.fel.ida.reads.util.TransposedList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Petr Ryšavý
 */
public class OutgroupRerootify<T> {

    private final HierarchicalTreeNode<T> tree;

    public OutgroupRerootify(HierarchicalTreeNode<T> tree) {
        this.tree = tree;
    }

    public HierarchicalTreeNode<T> outgroopReRootify(T value, Comparator<T> comparator) {
        // we need to start from the root, therefore transpose the list first
        final List<TreeNode<T>> path = new TransposedList<>(tree.tracePath(value, comparator));
        final HierarchicalTreeNode<T> root = (HierarchicalTreeNode<T>) path.get(0);
        // first is the list we are creating, i.e. replacing with last
        HierarchicalTreeNode<T> first = (HierarchicalTreeNode<T>) path.get(1);
        // if second is null, it means that first is the leaf
        HierarchicalTreeNode<T> second = path.size() > 2 ? (HierarchicalTreeNode<T>) path.get(2) : null;
        HierarchicalTreeNode<T> last = second == null ? new HierarchicalTreeNode<>(root.getOtherNode(first), root.leftLength + root.rightLength, first, 0.0, null)
                : new HierarchicalTreeNode<>(root.getOtherNode(first), root.leftLength + root.rightLength, first.getOtherNode(second), first.getOtherEdgeLength(second), first.value);
        for (int i = 2; i < path.size(); i++) {
            final HierarchicalTreeNode<T> originalLast = (HierarchicalTreeNode<T>) path.get(i - 1);
            first = (HierarchicalTreeNode<T>) path.get(i);
            second = i < path.size() - 1 ? (HierarchicalTreeNode<T>) path.get(i + 1) : null;
            last = second == null ? new HierarchicalTreeNode<>(last, originalLast.getEdgeLength(first), first, 0.0, null)
                    : new HierarchicalTreeNode<>(last, originalLast.getEdgeLength(first), first.getOtherNode(second), first.getOtherEdgeLength(second), first.value);
        }
        return last;
    }
}
