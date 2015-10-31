package org.grabhouse.hackathon.data;

import java.io.Serializable;

public class CoordData implements Serializable {

	private final Double lat;
	private final Double lng;
	
	public static CoordData instance(final Double lat, final Double lng) {
        return new CoordData(lat, lng);
    }

    private CoordData(final Double lat, final Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

}
