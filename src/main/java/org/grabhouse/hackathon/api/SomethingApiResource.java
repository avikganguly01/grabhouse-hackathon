package org.grabhouse.hackathon.api;

import org.grabhouse.hackathon.core.infra.FromJsonHelper;
import org.grabhouse.hackathon.service.ReadPlatformServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@RequestMapping("/households") 
public class SomethingApiResource {
	
	private final ReadPlatformServiceImpl readPlatformService;
	private final FromJsonHelper fromApiJsonHelper;
	
	@Autowired
    public SomethingApiResource(final ReadPlatformServiceImpl readPlatformService,
    		final FromJsonHelper fromApiJsonHelper) {
		this.readPlatformService = readPlatformService;
		this.fromApiJsonHelper = fromApiJsonHelper;
	}
	
	@RequestMapping(value = "/latlong", method = RequestMethod.GET, produces = "application/json; charset=utf-8")  
	@Transactional(readOnly = true)
    public void retrieveByLatLong(@RequestParam("lat") final double latitude,
    		@RequestParam("long") final double longitude) {
       this.readPlatformService.retrieveByLatLong(latitude,
        		longitude);
    }
	
	@RequestMapping(value = "/geocode", method = RequestMethod.GET, produces = "application/json; charset=utf-8")  
	@Transactional(readOnly = true)
    public void retrieveByGeocoding(@RequestParam("address") final String address) {
        this.readPlatformService.retrieveByAddress(address);
    }
	

}
