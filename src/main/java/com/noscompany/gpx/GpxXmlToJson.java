package com.noscompany.gpx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterators;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class GpxXmlToJson {

    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public void map(InputStream input, OutputStream output) throws IOException, XMLStreamException {
        final Iterator<Wpt> waypoints = wptIterator(input);
        final Iterator<LatLong> coordinates = toCoordinates(waypoints);
        jsonMapper.writeValue(output, coordinates);
    }

    private Iterator<Wpt> wptIterator(InputStream input) throws XMLStreamException {
        final XMLInputFactory factory = XMLInputFactory.newInstance();
        final XMLStreamReader reader = factory.createXMLStreamReader(input);
        return new GpxXmlIterator(reader);
    }

    private static Iterator<LatLong> toCoordinates(Iterator<Wpt> waypoints) {
        return Iterators.transform(waypoints, LatLong::instance);
    }
}