package wfigo.coginitive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "open.weather.map")
public class OpenWeatherMapProperties {

	private String apikey = "";
	
	private String url;
}
