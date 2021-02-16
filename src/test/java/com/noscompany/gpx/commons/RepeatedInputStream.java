package com.noscompany.gpx.commons;

import lombok.AllArgsConstructor;

import java.io.InputStream;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public class RepeatedInputStream extends InputStream {
    private final byte[] bytes;
    private final long limit;
    private long position;

    @Override
    public int read() {
        if (position < limit) {
            int index = (int) (position++ % bytes.length);
            return bytes[index];
        } else
            return -1;
    }

    public static InputStream instance(byte[] bytes, long repeats) {
        final long limit = (long) bytes.length * repeats;
        return new RepeatedInputStream(bytes, limit, 0);
    }
}
