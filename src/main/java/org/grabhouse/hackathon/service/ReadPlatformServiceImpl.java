package org.grabhouse.hackathon.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.grabhouse.hackathon.exception.ConstituencyNotFoundException;
import org.grabhouse.hackathon.utils.ProcessorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import retrofit.client.Response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class ReadPlatformServiceImpl {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ReadPlatformServiceImpl(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate();
	    this.jdbcTemplate.setDataSource(dataSource);
	}
	
	public void retrieveByLatLong(final double latitude, final double longitude) {
//			" inner join c_assemblies cpo on cpo.objectid=cc.polygon_id " +
//			" where cpo.objectid = (select cpoo.objectid from c_assemblies cpoo where st_contains(cpoo.`the_geom`, point(?, ?))) order by p.name";
//        return this.jdbcTemplate.query(sql, pm, new Object[] {longitude, latitude});
	}
	
	public void retrieveByAddress(final String address) {
		try {
			final GeocodingService service = ProcessorHelper
					.createWebHookService("https://maps.googleapis.com/maps/api/geocode");
			final Map<String, String> queryParams = new HashMap<String, String> ();
			queryParams.put("address", address);
			queryParams.put("bounds", "28.3247893,76.7824797|28.9184529,77.3331994");
			
			String sql = "select `key` from c_api_keys limit 1";
	        final List<String> apiKeys = this.jdbcTemplate.queryForList(sql, String.class);
	        final String geocodingApiKey =  apiKeys.get(0);
	        queryParams.put("key", geocodingApiKey);
			
			final Response response = service.sendRequestWithBounds(queryParams);
			final JsonObject location = new JsonParser().parse(ProcessorHelper.getResponse(response)).getAsJsonObject()
			.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry")
			.getAsJsonObject().get("location").getAsJsonObject();
			final double lat = location.get("lat").getAsDouble();
			final double longitude = location.get("lng").getAsDouble();
//	            " inner join c_assemblies cpo on cpo.objectid=cc.polygon_id " +
//	        		" where cpo.objectid = (select cpoo.objectid from c_assemblies cpoo where st_contains(cpoo.`the_geom`, point(?, ?))) order by p.name";

//	        return this.jdbcTemplate.query(sql, pm, new Object[] {longitude, lat});
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConstituencyNotFoundException();
		}
	}
	
	public String retrieveApiKey(int index) {
		String sql = "select `key` from c_api_keys";
        final List<String> apiKeys = this.jdbcTemplate.queryForList(sql, String.class);
        return apiKeys.get(index);
	}
	
	

}
