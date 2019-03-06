package com.webnms.rest.authentication.oauth;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import WeakConcurrentHashMap;
import WeakConcurrentHashMapListener;

/**
 * This servlet filter is used to track the number of api hits module wise on the FE Side. For now it just prints the api hit count once in 5 mintues in the FE logs. 
 * 
 * @author Vivekananthan M
 *
 */
@Provider
public class RESTMeteringFilter implements ContainerRequestFilter {
	
	private WeakConcurrentHashMap<String, Long> apiCounter = new WeakConcurrentHashMap<>(5 * 60 * 1000);	// Keys in the map will expire in 5 minutes
	
	public RESTMeteringFilter() {
		apiCounter.registerRemovalListener(new PrintAPIHits());
	}
	
	@Override
	public void filter(ContainerRequestContext paramContainerRequestContext) throws IOException 
	{
		RestLogger.debug("Processing Request for Metering. URI " + paramContainerRequestContext.getUriInfo().getPath());   //No I18N
		String uriHit = paramContainerRequestContext.getUriInfo().getPath();
		
		if(uriHit != null) {
			String[] pathList = uriHit.split("/");
			String key = null;
			if(pathList.length >= 4) {
				key = pathList[2] + "/" + pathList[3];
			} else {
				key = pathList[pathList.length-1];
			}
			
			if(key != null && !key.isEmpty()) {
				if(apiCounter.containsKey(key)) {
					apiCounter.put(key, apiCounter.get(key) + 1);
				} else {
					apiCounter.put(key, 1l);
				}
			}
		}
	}
}

class PrintAPIHits implements WeakConcurrentHashMapListener<String, Long> {

	@Override
	public void notifyOnAdd(String key, Long value) {}

	@Override
	public void notifyOnRemoval(String key, Long value) {
		System.out.println("Refreshing API Hits " + key + " : " + value);
	}
}
