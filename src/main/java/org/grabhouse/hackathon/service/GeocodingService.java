package org.grabhouse.hackathon.service;

import java.util.Map;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.QueryMap;


public interface GeocodingService {
	
	@GET("/json")
	Response sendRequestWithBounds(@QueryMap Map<String, String> map);

}
