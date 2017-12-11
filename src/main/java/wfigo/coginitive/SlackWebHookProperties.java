package wfigo.coginitive;

import javax.validation.Valid;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "slack.webhook")
public class SlackWebHookProperties {

	@Valid
	private String url = "";
}
