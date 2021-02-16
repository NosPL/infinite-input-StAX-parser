package com.noscompany.gpx;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public final class LatLong {
    private String lat;
    private String lon;

    public static LatLong instance(Wpt wpt) {
        String lat = wpt.getLat();
        String lon = wpt.getLon();
        if (lat == null)
            lat = "UNKNOWN";
        if (lon == null)
            lon = "UNKNOWN";
        return new LatLong(lat, lon);
    }
}
