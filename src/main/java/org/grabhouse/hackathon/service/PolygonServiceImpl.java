package org.grabhouse.hackathon.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.grabhouse.hackathon.data.PolygonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKBReader;

@Service
public class PolygonServiceImpl {
	
private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PolygonServiceImpl(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate();
	    this.jdbcTemplate.setDataSource(dataSource);
	}
	
	public List<PolygonData> retrieveAll() {
		String sql = "select the_geom, ac_name from c_assemblies where st_code=07";
		final List<PolygonData> polygons = new ArrayList<>();
		try{
			SqlRowSet rowSet =  this.jdbcTemplate.queryForRowSet(sql);
			while(rowSet.next()) {
				final Geometry geometry = retrieveGeometry(rowSet);
				final String areaName = rowSet.getString("ac_name");
				final List<Coordinate> coords = Arrays.asList(geometry.getCoordinates());
				polygons.add(PolygonData.instance(areaName, coords));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return polygons;
	}
	
	private Geometry retrieveGeometry(SqlRowSet rowSet) throws Exception {
		byte[] geometryAsBytes = (byte[]) rowSet.getObject("the_geom");
		 
        if (geometryAsBytes.length < 5) {
            throw new Exception("Invalid geometry inputStream - less than five bytes");
        }

        //first four bytes of the geometry are the SRID,
        //followed by the actual WKB.  Determine the SRID
        //here
        byte[] sridBytes = new byte[4];
        System.arraycopy(geometryAsBytes, 0, sridBytes, 0, 4);
        boolean bigEndian = (geometryAsBytes[4] == 0x00);

        int srid = 0;
        if (bigEndian) {
           for (int i = 0; i < sridBytes.length; i++) {
              srid = (srid << 8) + (sridBytes[i] & 0xff);
           }
        } else {
           for (int i = 0; i < sridBytes.length; i++) {
             srid += (sridBytes[i] & 0xff) << (8 * i);
           }
        }
        //use the JTS WKBReader for WKB parsing
        WKBReader wkbReader = new WKBReader();

        //copy the byte array, removing the first four
        //SRID bytes
        byte[] wkb = new byte[geometryAsBytes.length - 4];
        System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
        Geometry dbGeometry = wkbReader.read(wkb);
        dbGeometry.setSRID(srid);
        return dbGeometry;
	}


}
