package wfigo.coginitive.service;

import java.io.IOException;

import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;
import wfigo.coginitive.OpenWeatherMapProperties;

@Service
@Slf4j
public class OpenWeatherMapService {

	@Autowired
	OpenWeatherMapProperties properties;

	public String getWeather(String city) {
		
		if(Strings.isNullOrEmpty(properties.getUrl())) {
			return "";
		}

		String url = properties.getUrl() + "?q=" + city + "&APPID=" + properties.getApikey();
		try {
			String responseStr = Request.Get(url)
					.version(HttpVersion.HTTP_1_1)
					.execute()
					.returnContent().asString();
			
			JsonObject jsonObj = new Gson().fromJson(responseStr, JsonObject.class);
			
			return jsonObj.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").getAsString();
		} catch (IOException e) {
			log.error("Exception occurred.", e);
			return "";
		}
	}
}
