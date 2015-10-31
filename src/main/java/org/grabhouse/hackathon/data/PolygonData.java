package org.grabhouse.hackathon.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;

public class PolygonData implements Serializable {

    private final List<CoordData> coords;
    private final String name;
    
    public static PolygonData instance(final String name, final List<Coordinate> coords) {
    	final List<CoordData> coordinates = new ArrayList<>();
    	for(Coordinate coord: coords) {
    		coordinates.add(CoordData.instance(coord.y, coord.x));
    	}
        return new PolygonData(name, coordinates);
    }

    private PolygonData(final String name, final List<CoordData> coords) {
        this.name = name;
        this.coords = coords;
    }
    
}
