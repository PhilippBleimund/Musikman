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
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;

public class queue extends Command{

	@Override
	public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		TextChannel channel = eventMessage.getChannel().asTextChannel();
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
		
		if(queue.isEmpty()) {
			this.publicExec(publicExec, () -> {channel.sendMessage("the queue is empty").queue();});
			return;
		}
		
		int trackCount = Math.min(queue.size(), 20);
		List<AudioTrack> trackList = new ArrayList<>(queue);
		MessageCreateAction messageAction = channel.sendMessage("**Current Queue:**\n");
		
		for(int i=0;i<trackCount;i++) {
			AudioTrack track = trackList.get(i);
			AudioTrackInfo info = track.getInfo();
			
			messageAction.addContent("#")
				.addContent(String.valueOf(i + 1))
				.addContent(" `")
				.addContent(info.title)
				.addContent(" by ")
				.addContent(info.author)
				.addContent("` [`")
				.addContent(formatTime(track.getDuration()))
				.addContent("`]\n");
		}
		
		if(trackList.size() > trackCount) {
			messageAction.addContent("And `")
				.addContent(String.valueOf(trackList.size() - trackCount))
				.addContent("` more...");
				this.publicExec(publicExec, () -> {
					messageAction.queue(message -> {
						message.addReaction(Emoji.fromUnicode("U+23EC")).queue();
						TicketManager.getInstance().addTicket(new queueTXTTicket(message.getIdLong(), trackList));
				});});
				
		}else {
			this.publicExec(publicExec, () -> {messageAction.queue();});
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

	@Override
	public boolean NSFW() {
		return false;
	}
}
