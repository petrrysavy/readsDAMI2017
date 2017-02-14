package cz.cvut.fel.ida.reads.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author petr
 */


public class CommentsBufferedReader extends BufferedReader {
    
    private String commentMark;
    
    public CommentsBufferedReader(String commentMark, Reader in) {
        super(in);
        this.commentMark = commentMark;
    }

    public String getCommentMark() {
        return commentMark;
    }

    public void setCommentMark(String commentMark) {
        this.commentMark = commentMark;
    }

    public String readLineNoComment() throws IOException {
        String line = readLine();
        int index = line.indexOf(commentMark);
        return index == -1 ? line : line.substring(0, index);
    }
    
    public String readNonemptyLine() throws IOException {
        String line;
        do {
            line = readLineNoComment().trim();
        } while(line != null && line.isEmpty());
        return line;
    }
}
