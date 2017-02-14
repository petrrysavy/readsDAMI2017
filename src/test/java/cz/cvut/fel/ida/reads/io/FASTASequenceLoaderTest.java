package cz.cvut.fel.ida.reads.io;

import cz.cvut.fel.ida.reads.model.Sequence;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Petr Ryšavý
 */
public class FASTASequenceLoaderTest {

    @Test
    public void testLoadZika() {
        Path p = Paths.get("src", "test", "java", "data", "LC002520");
        FASTASequenceLoader loader = new FASTASequenceLoader(p);
        Sequence s = loader.loadSequence();

        Assert.assertEquals("ENA|LC002520|LC002520.1 Zika virus genomic RNA, complete genome, strain: MR766-NIID.", s.getDescription());
        Assert.assertEquals("AGTTGTTGATCTGTGTGAGTCAGACTGCGACAGTTCGAGTCTGAAGCGAGAGCTAACAAC"
                + "AGTATCAACAGGTTTAATTTGGATTTGGAAACGAGAGTTTCTGGTCATGAAAAACCCAAA"
                + "GAAGAAATCCGGAGGATTCCGGATTGTCAATATGCTAAAACGCGGAGTAGCCCGTGTAAA"
                + "CCCCTTGGGAGGTTTGAAGAGGTTGCCAGCCGGACTTCTGCTGGGTCATGGACCCATCAG"
                + "AATGGTTTTGGCGATACTAGCCTTTTTGAGATTTACAGCAATCAAGCCATCACTGGGCCT"
                + "TATCAACAGATGGGGTTCCGTGGGGAAAAAAGAGGCTATGGAAATAATAAAGAAGTTCAA"
                + "GAAAGATCTTGCTGCCATGTTGAGAATAATCAATGCTAGGAAAGAGAGGAAGAGACGTGG"
                + "CGCAGACACCAGCATCGGAATCATTGGCCTCCTGCTGACTACAGCCATGGCAGCAGAGAT"
                + "CACTAGACGCGGGAGTGCATACTACATGTACTTGGATAGGAGCGATGCCGGGAAGGCCAT"
                + "TTCGTTTGCTACCACATTGGGAGTGAACAAGTGCCACGTACAGATCATGGACCTCGGGCA"
                + "CATGTGTGACGCCACCATGAGTTATGAGTGCCCTATGCTGGATGAGGGAGTGGAACCAGA"
                + "TGATGTCGATTGCTGGTGCAACACGACATCAACTTGGGTTGTGTACGGAACCTGTCATCA"
                + "CAAAAAAGGTGAGGCACGGCGATCTAGAAGAGCCGTGACGCTCCCTTCTCACTCTACAAG"
                + "GAAGTTGCAAACGCGGTCGCAGACCTGGTTAGAATCAAGAGAATACACGAAGCACTTGAT"
                + "CAAGGTTGAAAACTGGATATTCAGGAACCCCGGGTTTGCGCTAGTGGCCGTTGCCATTGC"
                + "CTGGCTTTTGGGAAGCTCGACGAGCCAAAAAGTCATATACTTGGTCATGATACTGCTGAT"
                + "TGCCCCGGCATACAGTATCAGGTGCATTGGAGTCAGCAATAGAGACTTCGTGGAGGGCAT"
                + "GTCAGGTGGGACCTGGGTTGATGTTGTCTTGGAACATGGAGGCTGCGTTACCGTGATGGC"
                + "ACAGGACAAGCCAACAGTTGACATAGAGTTGGTCACGACGACGGTTAGTAACATGGCCGA"
                + "GGTAAGATCCTATTGCTACGAGGCATCGATATCGGACATGGCTTCGGACAGTCGTTGCCC"
                + "AACACAAGGTGAAGCCTACCTTGACAAGCAATCAGACACTCAATATGTCTGCAAAAGAAC"
                + "ATTAGTGGACAGAGGTTGGGGAAACGGTTGTGGACTTTTTGGCAAAGGGAGCTTGGTGAC"
                + "ATGTGCCAAGTTTACGTGTTCTAAGAAGATGACCGGGAAGAGCATTCAACCGGAAAATCT"
                + "GGAGTATCGGATAATGCTATCAGTGCATGGCTCCCAGCATAGCGGGATGACTGTCAATGA"
                + "TATAGGATATGAAACTGACGAAAATAGAGCGAAAGTCGAGGTTACGCCTAATTCACCAAG"
                + "AGCGGAAGCAACCTTGGGAGGCTTTGGAAGCTTAGGACTTGACTGTGAACCAAGGACAGG"
                + "CCTTGACTTTTCAGATCTGTATTACCTGACCATGAACAATAAGCATTGGTTGGTGCACAA"
                + "AGAGTGGTTTCATGACATCCCATTGCCTTGGCATGCTGGGGCAGACACTGGAACTCCACA"
                + "CTGGAACAACAAAGAGGCATTGGTAGAATTCAAGGATGCCCACGCCAAGAGGCAAACCGT"
                + "CGTCGTTCTGGGGAGCCAGGAAGGAGCCGTTCACACGGCTCTCGCTGGAGCTCTAGAGGC"
                + "TGAGATGGATGGTGCAAAGGGAAAGCTGTTCTCTGGCCATTTGAAATGCCGCCTAAAAAT"
                + "GGACAAGCTTAGATTGAAGGGCGTGTCATATTCCTTGTGCACTGCGGCATTCACATTCAC"
                + "CAAGGTCCCAGCTGAAACACTGCATGGAACAGTCACAGTGGAGGTGCAGTATGCAGGGAC"
                + "AGATGGACCCTGCAAGATCCCAGTCCAGATGGCGGTGGACATGCAGACCCTGACCCCAGT"
                + "TGGAAGGCTGATAACCGCCAACCCCGTGATTACTGAAAGCACTGAGAACTCAAAGATGAT"
                + "GTTGGAGCTTGACCCACCATTTGGGGATTCTTACATTGTCATAGGAGTTGGGGACAAGAA"
                + "AATCACCCACCACTGGCATAGGAGTGGTAGCACCATCGGAAAGGCATTTGAGGCCACTGT"
                + "GAGAGGCGCCAAGAGAATGGCAGTCCTGGGGGATACAGCCTGGGACTTCGGATCAGTCGG"
                + "GGGTGTGTTCAACTCACTGGGTAAGGGCATTCACCAGATTTTTGGAGCAGCCTTCAAATC"
                + "ACTGTTTGGAGGAATGTCCTGGTTCTCACAGATCCTCATAGGCACGCTGCTAGTGTGGTT"
                + "AGGTTTGAACACAAAGAATGGATCTATCTCCCTCACATGCTTGGCCCTGGGGGGAGTGAT"
                + "GATCTTCCTCTCCACGGCTGTTTCTGCTGACGTGGGGTGCTCAGTGGACTTCTCAAAAAA"
                + "GGAAACGAGATGTGGCACGGGGGTATTCATCTATAATGATGTTGAAGCCTGGAGGGACCG"
                + "GTACAAGTACCATCCTGACTCCCCCCGCAGATTGGCAGCAGCAGTCAAGCAGGCCTGGGA"
                + "AGAGGGGATCTGTGGGATCTCATCCGTTTCAAGAATGGAAAACATCATGTGGAAATCAGT"
                + "AGAAGGGGAGCTCAATGCTATCCTAGAGGAGAATGGAGTTCAACTGACAGTTGTTGTGGG"
                + "ATCTGTAAAAAACCCCATGTGGAGAGGTCCACAAAGATTGCCAGTGCCTGTGAATGAGCT"
                + "GCCCCATGGCTGGAAAGCCTGGGGGAAATCGTATTTTGTTAGGGCGGCAAAGACCAACAA"
                + "CAGTTTTGTTGTCGACGGTGACACACTGAAGGAATGTCCGCTTGAGCACAGAGCATGGAA"
                + "TAGTTTTCTTGTGGAGGATCACGGGTTTGGAGTCTTCCACACCAGTGTCTGGCTTAAGGT"
                + "CAGAGAAGATTACTCATTAGAATGTGACCCAGCCGTCATAGGAACAGCTGTTAAGGGAAG"
                + "GGAGGCCGCGCACAGTGATCTGGGCTATTGGATTGAAAGTGAAAAGAATGACACATGGAG"
                + "GCTGAAGAGGGCCCACCTGATTGAGATGAAAACATGTGAATGGCCAAAGTCTCACACATT"
                + "GTGGACAGATGGAGTAGAAGAAAGTGATCTTATCATACCCAAGTCTTTAGCTGGTCCACT"
                + "CAGCCACCACAACACCAGAGAGGGTTACAGAACCCAAGTGAAAGGGCCATGGCACAGTGA"
                + "AGAGCTTGAAATCCGGTTTGAGGAATGTCCAGGCACCAAGGTTTACGTGGAGGAGACATG"
                + "CGGAACTAGAGGACCATCTCTGAGATCAACTACTGCAAGTGGAAGGGTCATTGAGGAATG"
                + "GTGCTGTAGGGAATGCACAATGCCCCCACTATCGTTTCGAGCAAAAGACGGCTGCTGGTA"
                + "TGGAATGGAGATAAGGCCCAGGAAAGAACCAGAGAGCAACTTAGTGAGGTCAATGGTGAC"
                + "AGCGGGGTCAACCGATCATATGGACCACTTCTCTCTTGGAGTGCTTGTGATTCTACTCAT"
                + "GGTGCAGGAGGGGTTGAAGAAGAGAATGACCACAAAGATCATCATGAGCACATCAATGGC"
                + "AGTGCTGGTAGTCATGATCTTGGGAGGATTTTCAATGAGTGACCTGGCCAAGCTTGTGAT"
                + "CCTGATGGGTGCTACTTTCGCAGAAATGAACACTGGAGGAGATGTAGCTCACTTGGCATT"
                + "GGTAGCGGCATTTAAAGTCAGACCAGCCTTGCTGGTCTCCTTCATTTTCAGAGCCAATTG"
                + "GACACCCCGTGAGAGCATGCTGCTAGCCCTGGCTTCGTGTCTTCTGCAAACTGCGATCTC"
                + "TGCTCTTGAAGGTGACTTGATGGTCCTCATTAATGGATTTGCTTTGGCCTGGTTGGCAAT"
                + "TCGAGCAATGGCCGTGCCACGCACTGACAACATCGCTCTACCAATCTTGGCTGCTCTAAC"
                + "ACCACTAGCTCGAGGCACACTGCTCGTGGCATGGAGAGCGGGCCTGGCTACTTGTGGAGG"
                + "GATCATGCTCCTCTCCCTGAAAGGGAAAGGTAGTGTGAAGAAGAACCTGCCATTTGTCAT"
                + "GGCCCTGGGATTGACAGCTGTGAGGGTAGTAGACCCTATTAATGTGGTAGGACTACTGTT"
                + "ACTCACAAGGAGTGGGAAGCGGAGCTGGCCCCCTAGTGAAGTTCTCACAGCCGTTGGCCT"
                + "GATATGTGCACTGGCCGGAGGGTTTGCCAAGGCAGACATTGAGATGGCTGGACCCATGGC"
                + "TGCAGTAGGCTTGCTAATTGTCAGCTATGTGGTCTCGGGAAAGAGTGTGGACATGTACAT"
                + "TGAAAGAGCAGGTGACATCACATGGGAAAAGGACGCGGAAGTCACTGGAAACAGTCCTCG"
                + "GCTTGACGTGGCACTGGATGAGAGTGGTGATTTCTCCTTGGTAGAGGAAGATGGTCCACC"
                + "CATGAGAGAGATCATACTTAAGGTGGTCCTGATGGCCATCTGTGGCATGAACCCAATAGC"
                + "TATACCTTTTGCTGCAGGAGCGTGGTATGTGTATGTGAAGACTGGGAAAAGGAGTGGCGC"
                + "CCTCTGGGACGTGCCTGCTCCCAAAGAAGTGAAGAAAGGAGAGACCACAGATGGAGTGTA"
                + "CAGAGTGATGACTCGCAGACTGCTAGGTTCAACACAGGTTGGAGTGGGAGTCATGCAAGA"
                + "GGGAGTCTTCCACACCATGTGGCACGTTACAAAAGGAGCCGCACTGAGGAGCGGTGAGGG"
                + "AAGACTTGATCCATACTGGGGGGATGTCAAGCAGGACTTGGTGTCATACTGTGGGCCTTG"
                + "GAAGTTGGATGCAGCTTGGGATGGACTCAGCGAGGTACAGCTTTTGGCCGTACCTCCCGG"
                + "AGAGAGGGCCAGAAACATTCAGACCCTGCCTGGAATATTCAAGACAAAGGACGGGGACAT"
                + "CGGAGCAGTTGCTCTGGACTACCCTGCAGGGACCTCAGGATCTCCGATCCTAGACAAATG"
                + "TGGAAGAGTGATAGGACTCTATGGCAATGGGGTTGTGATCAAGAATGGAAGCTATGTTAG"
                + "TGCTATAACCCAGGGAAAGAGGGAGGAGGAGACTCCGGTTGAATGTTTCGAACCCTCGAT"
                + "GCTGAAGAAGAAGCAGCTAACTGTCTTGGATCTGCATCCAGGAGCCGGAAAAACCAGGAG"
                + "AGTTCTTCCTGAAATAGTCCGTGAAGCCATAAAAAAGAGACTCCGGACAGTGATCTTGGC"
                + "ACCAACTAGGGTTGTCGCTGCTGAGATGGAGGAGGCCTTGAGAGGACTTCCGGTGCGTTA"
                + "CATGACAACAGCAGTCAACGTCACCCATTCTGGGACAGAAATCGTTGATTTGATGTGCCA"
                + "TGCCACTTTCACTTCACGCTTACTACAACCCATCAGAGTCCCTAATTACAATCTCTACAT"
                + "CATGGATGAAGCCCACTTCACAGACCCCTCAAGTATAGCTGCAAGAGGATATATATCAAC"
                + "AAGGGTTGAAATGGGCGAGGCGGCTGCCATTTTTATGACTGCCACACCACCAGGAACCCG"
                + "TGATGCGTTTCCTGACTCTAACTCACCAATCATGGACACAGAAGTGGAAGTCCCAGAGAG"
                + "AGCCTGGAGCTCAGGCTTTGATTGGGTGACAGACCATTCTGGGAAAACAGTTTGGTTCGT"
                + "TCCAAGCGTGAGAAACGGAAATGAAATCGCAGCCTGTCTGACAAAGGCTGGAAAGCGGGT"
                + "CATACAGCTCAGCAGGAAGACTTTTGAGACAGAATTTCAGAAAACAAAAAATCAAGAGTG"
                + "GGACTTTGTCATAACAACTGACATCTCAGAGATGGGCGCCAACTTCAAGGCTGACCGGGT"
                + "CATAGACTCTAGGAGATGCCTAAAACCAGTCATACTTGATGGTGAGAGAGTCATCTTGGC"
                + "TGGGCCCATGCCTGTCACGCATGCTAGTGCTGCTCAGAGGAGAGGACGTATAGGCAGGAA"
                + "CCCTAACAAACCTGGAGATGAGTACATGTATGGAGGTGGGTGTGCAGAGACTGATGAAGG"
                + "CCATGCACACTGGCTTGAAGCAAGAATGCTTCTTGACAACATCTACCTCCAGGATGGCCT"
                + "CATAGCCTCGCTCTATCGGCCTGAGGCCGATAAGGTAGCCGCCATTGAGGGAGAGTTTAA"
                + "GCTGAGGACAGAGCAAAGGAAGACCTTCGTGGAACTCATGAAGAGAGGAGACCTTCCCGT"
                + "CTGGCTAGCCTATCAGGTTGCATCTGCCGGAATAACTTACACAGACAGAAGATGGTGCTT"
                + "TGATGGCACAACCAACAACACCATAATGGAAGACAGCGTACCAGCAGAGGTGTGGACAAA"
                + "GTATGGAGAGAAGAGAGTGCTCAAACCGAGATGGATGGATGCTAGGGTCTGTTCAGACCA"
                + "TGCGGCCCTGAAGTCGTTCAAAGAATTCGCCGCTGGAAAAAGAGGAGCGGCTTTGGGAGT"
                + "AATGGAGGCCCTGGGAACACTGCCAGGACACATGACAGAGAGGTTTCAGGAAGCCATTGA"
                + "CAACCTCGCCGTGCTCATGCGAGCAGAGACTGGAAGCAGGCCTTATAAGGCAGCGGCAGC"
                + "CCAACTGCCGGAGACCCTAGAGACCATTATGCTCTTAGGTTTGCTGGGAACAGTTTCACT"
                + "GGGGATCTTCTTCGTCTTGATGCGGAATAAGGGCATCGGGAAGATGGGCTTTGGAATGGT"
                + "AACCCTTGGGGCCAGTGCATGGCTCATGTGGCTTTCGGAAATTGAACCAGCCAGAATTGC"
                + "ATGTGTCCTCATTGTTGTGTTTTTATTACTGGTGGTGCTCATACCCGAGCCAGAGAAGCA"
                + "AAGATCTCCCCAAGATAACCAGATGGCAATTATCATCATGGTGGCAGTGGGCCTTCTAGG"
                + "TTTGATAACTGCAAACGAACTTGGATGGCTGGAAAGAACAAAAAATGACATAGCTCATCT"
                + "AATGGGAAGGAGAGAAGAAGGAGCAACCATGGGATTCTCAATGGACATTGATCTGCGGCC"
                + "AGCCTCCGCCTGGGCTATCTATGCCGCATTGACAACTCTCATCACCCCAGCTGTCCAACA"
                + "TGCGGTAACCACTTCATACAACAACTACTCCTTAATGGCGATGGCCACACAAGCTGGAGT"
                + "GCTGTTTGGCATGGGCAAAGGGATGCCATTTTATGCATGGGACCTTGGAGTCCCGCTGCT"
                + "AATGATGGGTTGCTATTCACAATTAACACCCCTGACTCTGATAGTAGCTATCATTCTGCT"
                + "TGTGGCGCACTACATGTACTTGATCCCAGGCCTACAAGCGGCAGCAGCGCGTGCTGCCCA"
                + "GAAAAGGACAGCAGCTGGCATCATGAAGAATCCCGTTGTGGATGGAATAGTGGTAACTGA"
                + "CATTGACACAATGACAATAGACCCCCAGGTGGAGAAGAAGATGGGACAAGTGTTACTCAT"
                + "AGCAGTAGCCATCTCCAGTGCTGTGCTGCTGCGGACCGCCTGGGGATGGGGGGAGGCTGG"
                + "AGCTCTGATCACAGCAGCGACCTCCACCTTGTGGGAAGGCTCTCCAAACAAATACTGGAA"
                + "CTCCTCTACAGCCACCTCACTGTGCAACATCTTCAGAGGAAGCTATCTGGCAGGAGCTTC"
                + "CCTTATCTATACAGTGACGAGAAACGCTGGCCTGGTTAAGAGACGTGGAGGTGGGACGGG"
                + "AGAGACTCTGGGAGAGAAGTGGAAAGCTCGTCTGAATCAGATGTCGGCCCTGGAGTTCTA"
                + "CTCTTATAAAAAGTCAGGTATCACTGAAGTGTGTAGAGAGGAGGCTCGCCGTGCCCTCAA"
                + "GGATGGAGTGGCCACAGGAGGACATGCCGTATCCCGGGGAAGTGCAAAGCTCAGATGGTT"
                + "GGTGGAGAGAGGATATCTGCAGCCCTATGGGAAGGTTGTTGACCTCGGATGTGGCAGAGG"
                + "GGGCTGGAGCTATTATGCCGCCACCATCCGCAAAGTGCAGGAGGTGAGAGGATACACAAA"
                + "GGGAGGTCCCGGTCATGAAGAACCCATGCTGGTGCAAAGCTATGGGTGGAACATAGTTCG"
                + "TCTCAAGAGTGGAGTGGACGTCTTCCACATGGCGGCTGAGCCGTGTGACACTCTGCTGTG"
                + "TGACATAGGTGAGTCATCATCTAGTCCTGAAGTGGAAGAGACACGAACACTCAGAGTGCT"
                + "CTCTATGGTGGGGGACTGGCTTGAAAAAAGACCAGGGGCCTTCTGTATAAAGGTGCTGTG"
                + "CCCATACACCAGCACTATGATGGAAACCATGGAGCGACTGCAACGTAGGCATGGGGGAGG"
                + "ATTAGTCAGAGTGCCATTGTCTCGCAACTCCACACATGAGATGTACTGGGTCTCTGGGGC"
                + "AAAGAGCAACATCATAAAAAGTGTGTCCACCACAAGTCAGCTCCTCCTGGGACGCATGGA"
                + "TGGCCCCAGGAGGCCAGTGAAATATGAGGAGGATGTGAACCTCGGCTCGGGTACACGAGC"
                + "TGTGGCAAGCTGTGCTGAGGCTCCTAACATGAAAATCATCGGCAGGCGCATTGAGAGAAT"
                + "CCGCAATGAACATGCAGAAACATGGTTTCTTGATGAAAACCACCCATACAGGACATGGGC"
                + "CTACCATGGGAGCTACGAAGCCCCCACGCAAGGATCAGCGTCTTCCCTCGTGAACGGGGT"
                + "TGTTAGACTCCTGTCAAAGCCTTGGGACGTGGTGACTGGAGTTACAGGAATAGCCATGAC"
                + "TGACACCACACCATACGGCCAACAAAGAGTCTTCAAAGAAAAAGTGGACACCAGGGTGCC"
                + "AGATCCCCAAGAAGGCACTCGCCAGGTAATGAACATAGTCTCTTCCTGGCTGTGGAAGGA"
                + "GCTGGGGAAACGCAAGCGGCCACGCGTCTGCACCAAAGAAGAGTTTATCAACAAGGTGCG"
                + "CAGCAATGCAGCACTGGGAGCAATATTTGAAGAGGAAAAAGAATGGAAGACGGCTGTGGA"
                + "AGCTGTGAATGATCCAAGGTTTTGGGCCCTAGTGGATAGGGAGAGAGAACACCACCTGAG"
                + "AGGAGAGTGTCACAGCTGTGTGTACAACATGATGGGAAAAAGAGAAAAGAAGCAAGGAGA"
                + "GTTCGGGAAAGCAAAAGGTAGCCGCGCCATCTGGTACATGTGGTTGGGAGCCAGATTCTT"
                + "GGAGTTTGAAGCCCTTGGATTCTTGAACGAGGACCATTGGATGGGAAGAGAAAACTCAGG"
                + "AGGTGGAGTCGAAGGGTTAGGATTGCAAAGACTTGGATACATTCTAGAAGAAATGAATCG"
                + "GGCACCAGGAGGAAAGATGTACGCAGATGACACTGCTGGCTGGGACACCCGCATTAGTAA"
                + "GTTTGATCTGGAGAATGAAGCTCTGATTACCAACCAAATGGAGGAAGGGCACAGAACTCT"
                + "GGCGTTGGCCGTGATTAAATACACATACCAAAACAAAGTGGTGAAGGTTCTCAGACCAGC"
                + "TGAAGGAGGAAAAACAGTTATGGACATCATTTCAAGACAAGACCAGAGAGGGAGTGGACA"
                + "AGTTGTCACTTATGCTCTCAACACATTCACCAACTTGGTGGTGCAGCTTATCCGGAACAT"
                + "GGAAGCTGAGGAAGTGTTAGAGATGCAAGACTTATGGTTGTTGAGGAAGCCAGAGAAAGT"
                + "GACCAGATGGTTGCAGAGCAATGGATGGGATAGACTCAAACGAATGGCGGTCAGTGGAGA"
                + "TGACTGCGTTGTGAAGCCAATCGATGATAGGTTTGCACATGCCCTCAGGTTCTTGAATGA"
                + "CATGGGAAAAGTTAGGAAAGACACACAGGAGTGGAAACCCTCGACTGGATGGAGCAATTG"
                + "GGAAGAAGTCCCGTTCTGCTCCCACCACTTCAACAAGCTGTACCTCAAGGATGGGAGATC"
                + "CATTGTGGTCCCTTGCCGCCACCAAGATGAACTGATTGGCCGAGCTCGCGTCTCACCAGG"
                + "GGCAGGATGGAGCATCCGGGAGACTGCCTGTCTTGCAAAATCATATGCGCAGATGTGGCA"
                + "GCTCCTTTATTTCCACAGAAGAGACCTTCGACTGATGGCTAATGCCATTTGCTCGGCTGT"
                + "GCCAGTTGACTGGGTACCAACTGGGAGAACCACCTGGTCAATCCATGGAAAGGGAGAATG"
                + "GATGACCACTGAGGACATGCTCATGGTGTGGAATAGAGTGTGGATTGAGGAGAACGACCA"
                + "TATGGAGGACAAGACTCCTGTAACAAAATGGACAGACATTCCCTATCTAGGAAAAAGGGA"
                + "GGACTTATGGTGTGGATCCCTTATAGGGCACAGACCCCGCACCACTTGGGCTGAAAACAT"
                + "CAAAGACACAGTCAACATGGTGCGCAGGATCATAGGTGATGAAGAAAAGTACATGGACTA"
                + "TCTATCCACCCAAGTCCGCTACTTGGGTGAGGAAGGGTCCACACCCGGAGTGTTGTAAGC"
                + "ACCAATTTTAGTGTTGTCAGGCCTGCTAGTCAGCCACAGTTTGGGGAAAGCTGTGCAGCC"
                + "TGTAACCCCCCCAGGAGAAGCTGGGAAACCAAGCTCATAGTCAGGCCGAGAACGCCATGG"
                + "CACGGAAGAAGCCATGCTGCCTGTGAGCCCCTCAGAGGACACTGAGTCAAAAAACCCCAC"
                + "GCGCTTGGAAGCGCAGGATGGGAAAAGAAGGTGGCGACCTTCCCCACCCTTCAATCTGGG"
                + "GCCTGAACTGGAGACTAGCTGTGAATCTCCAGCAGAGGGACTAGTGGTTAGAGGAGACCC"
                + "CCCGGAAAACGCAAAACAGCATATTGACGCTGGGAAAGACCAGAGACTCCATGAGTTTCC"
                + "ACCACGCTGGCCGCCAGGCACAGATCGCCGAACAGCGGCGGCCGGTGTGGGGAAATCCAT"
                + "GGTTTCT", new String(s.getSequence()));
    }
}
