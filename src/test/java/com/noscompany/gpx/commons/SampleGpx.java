package com.noscompany.gpx.commons;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import static com.google.common.base.Charsets.UTF_8;

public class SampleGpx {

    private static final Charset CHARSET = UTF_8;
    private static final String HEADER = "<gpx>\n";
    private static final String TIME = "    <time>2002-02-27T17:18:33Z</time>\n";
    private static final String WPT = "    <wpt lat=\"42.438878\" lon=\"-71.119277\"></wpt>\n";
    private static final String FOOTER = "</gpx>";

    public static InputStream asInputStream(long totalSizeInBytes) {
        long minimalWptSize = totalSizeInBytes - sizeInBytesOf(HEADER, TIME, FOOTER);
        long repeats = calculateRepeats(WPT, minimalWptSize);
        return MergeInputStream.merge(
                stream(HEADER),
                stream(TIME),
                repeatedStream(WPT, repeats),
                stream(FOOTER));
    }

    private static long sizeInBytesOf(String... strings) {
        return Arrays
                .stream(strings)
                .mapToInt(s -> s.getBytes(CHARSET).length)
                .sum();
    }

    private static long calculateRepeats(String string, long requiredSize) {
        if (requiredSize <= 0)
            return 1;
        long stringSize = sizeInBytesOf(string);
        long repeats = requiredSize / stringSize;
        long reminder = requiredSize % stringSize;
        if (reminder > 0)
            repeats++;
        return repeats;
    }

    private static InputStream repeatedStream(String string, long repeats) {
        byte[] bytes = string.getBytes(CHARSET);
        return RepeatedInputStream.instance(bytes, repeats);
    }

    private static InputStream stream(String string) {
        return new ByteArrayInputStream(string.getBytes(CHARSET));
    }
}