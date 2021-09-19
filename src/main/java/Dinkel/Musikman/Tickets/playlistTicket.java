package Dinkel.Musikman.Tickets;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Manager.PollTicket;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

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
	public void TicketCode(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getReactionEmote().getEmoji().equals("👍")) {
			List<AudioTrack> tracks = playlist.getTracks();
			for(AudioTrack track : tracks) {
				musicManager.scheduler.queue(track);
			}
			reactionEvent.getChannel().sendMessage("Adding to queue: `")
			.append(String.valueOf(tracks.size()))
			.append("` tracks from playlist `")
			.append(playlist.getName())
			.append("`")
			.queue();
		}
		TicketManager.getInstance().removeTicket(this);
	}

	@Override
	public boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getMessageIdLong() == messageId && !reactionEvent.getUser().isBot())
			return true;
		else
			return false;
	}

}
