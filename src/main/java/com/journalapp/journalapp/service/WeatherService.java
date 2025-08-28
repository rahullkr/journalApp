package com.journalapp.journalapp.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.journalapp.journalapp.apiresponse.WeatherResponse;
import com.journalapp.journalapp.cache.AppCache;



@Component
public class WeatherService {
    @Value("${weather_api_key}")
    private String apikey ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getcity(String city) {
    String urlTemplate = appCache.APP_CACHE.get("Url");
    
    if (urlTemplate == null) {
        throw new IllegalStateException("Key 'Url' not found in APP_CACHE. Available keys: " 
                + appCache.APP_CACHE.keySet());
    }

    String finalApi = urlTemplate.replace("<apiKey>", apikey).replace("<city>", city);

    ResponseEntity<WeatherResponse> response =
            restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);

    WeatherResponse body = response.getBody();
    Logger.getAnonymousLogger().info(body.toString());
    return body;
}


}
