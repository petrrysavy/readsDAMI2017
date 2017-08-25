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
import cz.cvut.fel.ida.reads.similarity.DistanceCalculator;
import cz.cvut.fel.ida.reads.test.CompareTrees;
import cz.cvut.fel.ida.reads.test.PrintTrees;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author Petr Ryšavý
 */
public class NeighborJoiningTest {

    @Test
    public void testBuildTree() {
        DistanceCalculator<Integer, Double> dc = new DistanceCalculator<Integer, Double>() {
            private final int[][] distanceMatrix = new int[][]{
                {
                    0, 5, 9, 9, 8
                },
                {
                    5, 0, 10, 10, 9
                },
                {
                    9, 10, 0, 8, 7
                },
                {
                    9, 10, 8, 0, 3
                },
                {
                    8, 9, 7, 3, 0
                }
            };

            @Override
            public Double getDistance(Integer a, Integer b) {
                return (double) distanceMatrix[a][b];
            }
        };

        NeighborJoining<Integer> neighborJoinging = new NeighborJoining<>();
        HierarchicalTreeNode<Integer> root = neighborJoinging.buildTree(new Integer[]{
            0, 1, 2, 3, 4
        }, dc).value1;
        // test case taken from Wikipedia
        // https://en.wikipedia.org/wiki/Neighbor_joining
        HierarchicalTreeNode<Integer> expected = new HierarchicalTreeNode<>(
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

        PrintTrees.printTree(root);

        assertThat(CompareTrees.compareTrees(root, expected), is(true));
    }
}
