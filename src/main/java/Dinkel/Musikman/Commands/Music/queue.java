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
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageCreateAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

public class queue extends Command{

	@Override
	public void textImplementation(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
		Guild guild = eventMessage.getGuild();

		mergedImplementation(guild, new ImplementationData(eventMessage, publicExec, InvokeMethod.TEXT));
	}

	@Override
	public void slashImplementation(SlashCommandInteractionEvent eventMessage, boolean publicExec) {
		Guild guild = eventMessage.getGuild();

		mergedImplementation(guild, new ImplementationData(eventMessage, publicExec, InvokeMethod.SLASH));
	}

	public void mergedImplementation(Guild guild, ImplementationData data){
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(guild);
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;
		
		if(queue.isEmpty()) {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent("the queue is empty").build(), data.type));
			return;
		}
		
		int trackCount = Math.min(queue.size(), 20);
		List<AudioTrack> trackList = new ArrayList<>(queue);
		StringBuilder messageAction = new StringBuilder("**Current Queue:**\n");
		
		for(int i=0;i<trackCount;i++) {
			AudioTrack track = trackList.get(i);
			AudioTrackInfo info = track.getInfo();
			
			messageAction.append("#")
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

				this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent(messageAction.toString()).build(), data.type, false, message -> {
					message.addReaction(Emoji.fromUnicode("U+23EC")).queue();
					TicketManager.getInstance().addTicket(new queueTXTTicket(message.getIdLong(), trackList));
				}, null));				
		}else {
			this.publicExec(data.publicExec, this.createMessageRunnable(data.event, new MessageCreateBuilder().setContent(messageAction.toString()).build(), data.type));
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
