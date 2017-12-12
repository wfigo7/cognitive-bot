package wfigo.coginitive.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import wfigo.coginitive.SlackWebHookProperties;

@Service
@Slf4j
public class SlackWebhookService {

	@Autowired
	SlackWebHookProperties properties;

	public void sendTextMessage(String text) {
		
		if(Strings.isNullOrEmpty(properties.getUrl())) {
			return;
		}

		Gson gson = new Gson();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("text", text);
		
		try {
			Request.Post(properties.getUrl())
					.useExpectContinue()
					.version(HttpVersion.HTTP_1_1)
					.bodyString(gson.toJson(paramMap), ContentType.create("application/json", Consts.UTF_8))
					.execute()
					.returnContent().asBytes();
		} catch (IOException e) {
			log.error("Exception occurred.", e);
		}
	}
}
