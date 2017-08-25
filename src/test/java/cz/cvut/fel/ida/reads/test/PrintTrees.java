/* 
 * Copyright (C) 2017 Petr Ryšavý <petr.rysavy@fel.cvut.cz>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
