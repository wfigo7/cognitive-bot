package wfigo.coginitive.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import wfigo.coginitive.SlackWebHookProperties;

@Service
@Slf4j
public class SlackWebhookService {

	@Autowired
	SlackWebHookProperties properties;
	
	public void sendTextMessage(String text) {
		
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(properties.getUrl());
			Gson gson = new Gson();
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("text", text);
			StringEntity input = new StringEntity( gson.toJson(paramMap));
			input.setContentType("application/json");
			post.setEntity(input);
			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				log.info(line);
			}
		} catch(Exception e) {
			log.error("Exception occurred.", e);
		}
	}
}
