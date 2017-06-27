package cz.cvut.fel.ida.reads.experiment;

/**
 * Type of resulting tree based on algorithm that produced it. Currently UPGMA
 * or neighbor-joining.
 *
 * @author Petr Ryšavý
 */
public enum TreeType {
    /** Tree built by UPGMA algorithm. */
    UPGMA_TREE,
    /** Tree built by the
     * neighbor-joining algorithm. */
    NEIGHBOR_JOINING_TREE;
}
