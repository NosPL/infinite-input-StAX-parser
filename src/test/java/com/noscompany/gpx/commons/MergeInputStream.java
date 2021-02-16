package com.noscompany.gpx.commons;

import java.io.InputStream;
import java.io.SequenceInputStream;

public class MergeInputStream {

    public static InputStream merge(InputStream first, InputStream second, InputStream... other) {
        SequenceInputStream sequenceStream = new SequenceInputStream(first, second);
        for (InputStream next : other)
            sequenceStream = new SequenceInputStream(sequenceStream, next);
        return sequenceStream;
    }
}
