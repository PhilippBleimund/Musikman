package Dinkel.Musikman.Commands.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Tickets.queueTXTTicket;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class queue implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		TextChannel channel = eventMessage.getChannel();
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
		
		if(queue.isEmpty()) {
			channel.sendMessage("the queue is empty");
			return;
		}
		
		int trackCount = Math.min(queue.size(), 20);
		List<AudioTrack> trackList = new ArrayList<>(queue);
		MessageAction messageAction = channel.sendMessage("**Current Queue:**\n");
		
		for(int i=0;i<trackCount;i++) {
			AudioTrack track = trackList.get(i);
			AudioTrackInfo info = track.getInfo();
			
			messageAction.append('#')
				.append(String.valueOf(i + 1))
				.append(" `")
				.append(info.title)
				.append(" by ")
				.append(info.author)
				.append("` [`")
				.append(formatTime(track.getDuration()))
				.append("`]\n");
		}
		
		if(trackList.size() > trackCount) {
			messageAction.append("And `")
				.append(String.valueOf(trackList.size() - trackCount))
				.append("` more...");
			messageAction.queue(message -> {
				message.addReaction("⏬").queue();
				System.out.println("moin");
				TicketManager.getInstance().addTicket(new queueTXTTicket(message.getIdLong(), trackList));
			});
		}else {
			messageAction.queue();
		}
	}
	
	private String formatTime(long timeInMillis) {
		long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
		long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
		long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);
		
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	@Override
	public String[] getNames() {
		return new String[]{"queue", "q"};
	}

	@Override
	public String getDescription() {
		return "shows the queue";
	}

	@Override
	public String[] getArgs() {
		return new String[] {"all"};
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}