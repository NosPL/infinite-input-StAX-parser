package com.noscompany.gpx.test;

import com.google.common.io.NullOutputStream;
import com.noscompany.gpx.GpxXmlToJson;
import com.noscompany.gpx.commons.SampleGpx;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class GpxInputTest {

    private static final long ONE_KB = 1024;
    private static final long MB = 1024 * ONE_KB;
    private static final long GB = 1024 * MB;

    @ParameterizedTest
    @ValueSource(longs = {10 * MB, 100 * MB, GB, 2 * GB})
    public void test1(long sizeInBytes) throws IOException, XMLStreamException {
        InputStream input = SampleGpx.asInputStream(sizeInBytes);
        OutputStream output = new NullOutputStream();
        new GpxXmlToJson().map(input, output);
    }
}