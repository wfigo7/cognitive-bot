package wfigo.coginitive.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			JSONObject json = new JSONObject();
			json.put("text", text);
			StringEntity input = new StringEntity( json.toString());
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
