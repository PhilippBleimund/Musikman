package Dinkel.Musikman.Tickets;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Manager.PollTicket;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;

public class queueTXTTicket extends PollTicket{

	private long messageId;
	List<AudioTrack> trackList;
	
	public queueTXTTicket(long messageId, List<AudioTrack> trackList) {
		this.messageId = messageId;
		this.trackList = trackList;
	}
	
	@Override
	public void TicketCode(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getReactionEmote().getEmoji().equals("⏬")) {
			System.out.println("helo");
			List<String> lines = new ArrayList<String>();
			for(int i=0;i<trackList.size();i++) {
				AudioTrack track = trackList.get(i);
				String line = "#";
				line = line + String.valueOf(i + 1);
				line = line + " '" + track.getInfo().title + "'";
				line = line + " by " + track.getInfo().author;
				line = line + " [" + formatTime(track.getDuration()) + "]\n";
				lines.add(line);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			for (String element : lines) {
			    try {
					out.writeUTF(element);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			byte[] bytes = baos.toByteArray();
			reactionEvent.getChannel().sendFile(bytes, "queue"+ new SimpleDateFormat("yyyy-MM-dd-HH:mm").format(new Date()) +".txt").queue();
		}
	}
	
	private String formatTime(long timeInMillis) {
		long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
		
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	@Override
	public boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent) {
		return (reactionEvent.getMessageIdLong() == this.messageId && !reactionEvent.getUser().isBot());
	}

}
