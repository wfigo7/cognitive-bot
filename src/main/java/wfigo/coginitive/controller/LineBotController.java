package wfigo.coginitive.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.extern.slf4j.Slf4j;
import wfigo.coginitive.service.SlackWebhookService;

@LineMessageHandler
@Slf4j
public class LineBotController {

	@Autowired
	SlackWebhookService slackService;

	private Map<String, String> stickerMap = ImmutableMap.of("1", "13", "2", "175", "3", "191", "4", "276");

	@EventMapping
	public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		log.info("event: " + event);
		String text = event.getMessage().getText();
		slackService.sendTextMessage(event.getMessage().getText());
		// sticker pattern message ex. ${packageId}:${stickerId}
		if(text.indexOf(":") != -1) {
			String[] textArray = text.split(":");
			return new StickerMessage(textArray[0], textArray[1]);
		}
		return new TextMessage(event.getMessage().getText());
	}
	
	@EventMapping
	public StickerMessage handleStickerMessageEvent(MessageEvent<StickerMessageContent> event) {
		log.info("event: " + event);
		StickerMessageContent message = event.getMessage();
		String packageId = message.getPackageId();
		String stickerId = message.getStickerId();
		slackService.sendTextMessage(packageId + ":" + stickerId);
		if(stickerMap.containsKey(packageId)) {
			// defined packageId
			return new StickerMessage(packageId, stickerMap.get(packageId));
		} else {
			// sorry sticker
			return new StickerMessage("2", "38");
		}
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		log.info("event: " + event);
	}
}
