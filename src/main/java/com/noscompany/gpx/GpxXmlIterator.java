package com.noscompany.gpx;

import com.google.common.collect.AbstractIterator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static lombok.AccessLevel.PUBLIC;

@AllArgsConstructor(access = PUBLIC)
public class GpxXmlIterator extends AbstractIterator<Wpt> {
    private final XMLStreamReader reader;

    @SneakyThrows
    @Override
    protected Wpt computeNext() {
        return tryPullNextWaypoint();
    }

    private Wpt tryPullNextWaypoint() throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if (reader.getLocalName().equals("wpt")) {
                        return parseWaypoint();
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    if (reader.getLocalName().equals("gpx")) {
                        return endOfData();
                    }
                    break;
            }
        }
        throw new IllegalStateException("XML file didn't finish with </gpx> element, malformed?");
    }

    private Wpt parseWaypoint() {
        final String lat = reader.getAttributeValue("", "lat");
        final String lon = reader.getAttributeValue("", "lon");
        return new Wpt(lat, lon);
    }

}