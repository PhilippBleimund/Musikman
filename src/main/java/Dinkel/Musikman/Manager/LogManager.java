package Dinkel.Musikman.Manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Dinkel.Musikman.SaveData.saveManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LogManager extends ListenerAdapter{
	
	/*
	 * LogIndex.json structure:
	 * 
	 * Object: Indices[
	 * 		Array: LogFiles[
	 * 			Object: File[]
	 * 			Object: Entry's[]
	 * 		]
	 * ]
	 */
	String locationLogIndex = System.getProperty("user.dir") + System.getProperty("file.separator") + "LogIndex.json";
	
	JSONObject File = getWorkingIndex();
	
	public JSONObject getWorkingIndex() {
		JSONObject parse = saveManager.loadJSON(new File(locationLogIndex));
		if(parse == null) {
			JSONObject object = new JSONObject();
			JSONArray array = new JSONArray();
			object.put("Messages", array);
			File = object;
			saveManager.saveJSON(parse, new File(locationLogIndex));
		}
		return parse;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		JSONObject MessageObject = new JSONObject();
		LocalDateTime timeCreated = event.getMessage().getTimeCreated().toLocalDateTime();
		ZonedDateTime utcTimeZoned = ZonedDateTime.of(timeCreated, ZoneId.ofOffset("UTC", ZoneOffset.ofHours(2)));
		MessageObject.put("time", utcTimeZoned.toString());

		User author = event.getAuthor();
		MessageObject.put("authorName", author.getName());
		MessageObject.put("authorId", author.getId());
		
		MessageChannel channel = event.getChannel();
		MessageObject.put("chanelName", channel.getName());
		MessageObject.put("channelId", channel.getId());
		
		Guild guild = event.getGuild();
		MessageObject.put("ServerName", guild.getName());
		MessageObject.put("ServerId", guild.getId());
		
		Message message = event.getMessage();
		List<Attachment> attachments = message.getAttachments();
		for(int i=0;i<attachments.size();i++) {
			MessageObject.put("attachment#" + i, attachments.get(i).getUrl());
		}
		MessageObject.put("messageId", message.getId());
		MessageObject.put("messageString", message.getContentRaw());
		
		JSONArray messages = (JSONArray) File.get("Messages");
		messages.add(MessageObject);
		saveManager.saveJSON(File, new File(locationLogIndex));
	}
}