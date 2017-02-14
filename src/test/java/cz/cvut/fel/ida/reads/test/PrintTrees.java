package cz.cvut.fel.ida.reads.test;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.model.TreeNode;

/**
 *
 * @author Petr Ryšavý
 */
public class PrintTrees {

    private PrintTrees() {
    }

    public static <T> void printTree(TreeNode<T> t) {
        printTreeRecursively(t, 0);
    }

    private static <T> void printTreeRecursively(TreeNode<T> t, int spaces) {
        for (int i = 0; i < spaces; i++)
            System.out.print(" ");
        System.out.println(t.value == null ? "null : " + ((HierarchicalTreeNode) t).leftLength + " " + ((HierarchicalTreeNode) t).rightLength : t.value);
        final int len = t.value == null ? 4 : t.toString().length();
        if (t.left != null)
            printTreeRecursively(t.left, spaces + len);
        if (t.right != null)
            printTreeRecursively(t.right, spaces + len);
    }

}
