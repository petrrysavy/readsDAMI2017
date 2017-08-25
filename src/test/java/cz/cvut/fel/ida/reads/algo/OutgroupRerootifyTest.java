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
package cz.cvut.fel.ida.reads.algo;

import cz.cvut.fel.ida.reads.model.HierarchicalTreeNode;
import cz.cvut.fel.ida.reads.test.CompareTrees;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class OutgroupRerootifyTest {

    private OutgroupRerootify<Integer> outgroup = null;
    private HierarchicalTreeNode<Integer> expected = null;
    private HierarchicalTreeNode<Integer> originalTree = null;

    @Before
    public void bootstrap() {
        originalTree = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(4),
                0.5,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(2),
                                4.0,
                                new HierarchicalTreeNode<>(
                                        new HierarchicalTreeNode<>(0),
                                        2.0,
                                        new HierarchicalTreeNode<>(1),
                                        3.0,
                                        null
                                ),
                                3.0,
                                null
                        ),
                        2.0,
                        new HierarchicalTreeNode<>(3),
                        2.0,
                        null
                ),
                0.5,
                null
        );
        outgroup = new OutgroupRerootify<>(originalTree);
    }

    @Test
    public void testOutgroopReRootifyMakeroot0() {
        // it differs on the edge legth that goes to the outgroup
        expected = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(0),
                0.0,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(2),
                                4.0,
                                new HierarchicalTreeNode<>(
                                        new HierarchicalTreeNode<>(4),
                                        1.0,
                                        new HierarchicalTreeNode<>(3),
                                        2.0,
                                        null
                                ),
                                2.0,
                                null
                        ),
                        3.0,
                        new HierarchicalTreeNode<>(1),
                        3.0,
                        null
                ),
                2.0,
                null
        );
        assertThat(CompareTrees.compareTrees(expected, outgroup.outgroopReRootify(0, null)), is(true));
    }

    @Test
    public void testOutgroopReRootifyMakeroot1() {
        // it differs on the edge legth that goes to the outgroup
        expected = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(1),
                0.0,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(2),
                                4.0,
                                new HierarchicalTreeNode<>(
                                        new HierarchicalTreeNode<>(4),
                                        1.0,
                                        new HierarchicalTreeNode<>(3),
                                        2.0,
                                        null
                                ),
                                2.0,
                                null
                        ),
                        3.0,
                        new HierarchicalTreeNode<>(0),
                        2.0,
                        null
                ),
                3.0,
                null
        );
        assertThat(CompareTrees.compareTrees(expected, outgroup.outgroopReRootify(1, null)), is(true));
    }

    @Test
    public void testOutgroopReRootifyMakeroot2() {
        // it differs on the edge legth that goes to the outgroup
        expected = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(2),
                0.0,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(0),
                                2.0,
                                new HierarchicalTreeNode<>(1),
                                3.0,
                                null
                        ),
                        3.0,
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(3),
                                2.0,
                                new HierarchicalTreeNode<>(4),
                                1.0,
                                null
                        ),
                        2.0,
                        null
                ),
                4.0,
                null
        );
        assertThat(CompareTrees.compareTrees(expected, outgroup.outgroopReRootify(2, null)), is(true));
    }

    @Test
    public void testOutgroopReRootifyMakeroot3() {
        // it differs on the edge legth that goes to the outgroup
        expected = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(3),
                0.0,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(2),
                                4.0,
                                new HierarchicalTreeNode<>(
                                        new HierarchicalTreeNode<>(0),
                                        2.0,
                                        new HierarchicalTreeNode<>(1),
                                        3.0,
                                        null
                                ),
                                3.0,
                                null
                        ),
                        2.0,
                        new HierarchicalTreeNode<>(4),
                        1.0,
                        null
                ),
                2.0,
                null
        );
        assertThat(CompareTrees.compareTrees(expected, outgroup.outgroopReRootify(3, null)), is(true));
    }

    @Test
    public void testOutgroopReRootifyMakeroot4() {
        // it differs on the edge legth that goes to the outgroup
        expected = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(4),
                0.0,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(2),
                                4.0,
                                new HierarchicalTreeNode<>(
                                        new HierarchicalTreeNode<>(0),
                                        2.0,
                                        new HierarchicalTreeNode<>(1),
                                        3.0,
                                        null
                                ),
                                3.0,
                                null
                        ),
                        2.0,
                        new HierarchicalTreeNode<>(3),
                        2.0,
                        null
                ),
                1.0,
                null
        );
        assertThat(CompareTrees.compareTrees(expected, outgroup.outgroopReRootify(4, null)), is(true));
    }

}
