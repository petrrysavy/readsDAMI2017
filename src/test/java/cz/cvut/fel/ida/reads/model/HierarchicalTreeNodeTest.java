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
