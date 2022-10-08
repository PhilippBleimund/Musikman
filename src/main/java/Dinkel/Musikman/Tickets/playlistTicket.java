package Dinkel.Musikman.Tickets;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Manager.PollTicket;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class playlistTicket extends PollTicket{

	AudioPlaylist playlist;	
	long messageId;	
	GuildMusicManager musicManager;
	
	public playlistTicket(AudioPlaylist playlist, long messageId, GuildMusicManager musicManager) {
		this.playlist = playlist;
		this.messageId = messageId;
		this.musicManager = musicManager;
	}
	
	@Override
	public void TicketCode(MessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getEmoji().asUnicode().equals("U+1F44D")) {
			List<AudioTrack> tracks = playlist.getTracks();
			for(AudioTrack track : tracks) {
				musicManager.scheduler.queue(track);
			}
			reactionEvent.getChannel().sendMessage("Adding to queue: `")
			.addContent(String.valueOf(tracks.size()))
			.addContent("` tracks from playlist `")
			.addContent(playlist.getName())
			.addContent("`")
			.queue();
		}
		TicketManager.getInstance().removeTicket(this);
	}

	@Override
	public boolean isRightTicket(MessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getMessageIdLong() == messageId && !reactionEvent.getUser().isBot())
			return true;
		else
			return false;
	}

}
