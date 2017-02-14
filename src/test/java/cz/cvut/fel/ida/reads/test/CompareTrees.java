package cz.cvut.fel.ida.reads.test;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import java.util.Objects;

/**
 *
 * @author Petr Ryšavý
 */
public class CompareTrees {

    private CompareTrees() {
    }

    public static <T> boolean compareTrees(HierarchicalTreeNode<T> actual, HierarchicalTreeNode<T> expected) {
        if (Objects.equals(actual, expected))
            return true;
        if (!Objects.equals(actual.value, expected.value))
            return false;
        if (compareTrees((HierarchicalTreeNode<T>) actual.left, (HierarchicalTreeNode<T>) expected.left) && compareTrees((HierarchicalTreeNode<T>) actual.right, (HierarchicalTreeNode<T>) expected.right))
            return actual.leftLength == expected.leftLength && actual.rightLength == expected.rightLength;
        if (compareTrees((HierarchicalTreeNode<T>) actual.left, (HierarchicalTreeNode<T>) expected.right) && compareTrees((HierarchicalTreeNode<T>) actual.right, (HierarchicalTreeNode<T>) expected.left))
            return actual.leftLength == expected.rightLength && actual.rightLength == expected.leftLength;
        return false;
    }

}
