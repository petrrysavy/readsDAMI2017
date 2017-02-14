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
public class UPGMATest {

    @Test
    public void testBuildTree() {
        DistanceCalculator<Integer, Double> dc = new DistanceCalculator<Integer, Double>() {
            private final int[][] distanceMatrix = new int[][]{
                {
                    0, 17, 21, 31, 23
                },
                {
                    17, 0, 30, 34, 21
                },
                {
                    21, 30, 0, 28, 39
                },
                {
                    31, 34, 28, 0, 43
                },
                {
                    23, 21, 39, 43, 0
                }
            };

            @Override
            public Double getDistance(Integer a, Integer b) {
                return (double) distanceMatrix[a][b];
            }
        };

        UPGMA<Integer> upgma = new UPGMA<>();
        HierarchicalTreeNode<Integer> root = upgma.buildTree(new Integer[]{
            0, 1, 2, 3, 4
        }, dc).value1;
        HierarchicalTreeNode<Integer> expected = new HierarchicalTreeNode<>(
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(2),
                        14.0,
                        new HierarchicalTreeNode<>(3),
                        14.0,
                        null
                ),
                2.5,
                new HierarchicalTreeNode<>(
                        new HierarchicalTreeNode<>(
                                new HierarchicalTreeNode<>(0),
                                8.5,
                                new HierarchicalTreeNode<>(1),
                                8.5,
                                null
                        ),
                        2.5,
                        new HierarchicalTreeNode<>(4),
                        11.0,
                        null
                ),
                5.5,
                null
        );

        PrintTrees.printTree(root);

        assertThat(CompareTrees.compareTrees(root, expected), is(true));
    }
}
