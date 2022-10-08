package Dinkel.Musikman.Manager;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.SaveData.saveManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
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
	File locationLogIndex = Information.getLogFile();
	
	JSONObject File = getWorkingIndex();
	
	public JSONObject getWorkingIndex() {
		JSONObject parse = saveManager.loadJSON(locationLogIndex);
		if(parse == null) {
			JSONObject object = new JSONObject();
			JSONArray array = new JSONArray();
			object.put("Messages", array);
			File = object;
			saveManager.saveJSON(object, locationLogIndex);
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
		
		MessageChannelUnion channel = event.getChannel();
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
		saveManager.saveJSON(File, locationLogIndex);
	}
}