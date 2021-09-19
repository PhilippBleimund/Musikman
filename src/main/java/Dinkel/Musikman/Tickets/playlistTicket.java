package Dinkel.Musikman.Tickets;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Manager.PollTicket;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class playlistTicket extends PollTicket{

	List<AudioTrack> tracks;	
	long messageId;	
	GuildMusicManager musicManager;
	
	public playlistTicket(List<AudioTrack> tracks, long messageId, GuildMusicManager musicManager) {
		this.messageId = messageId;
		this.musicManager = musicManager;
	}
	
	@Override
	public void TicketCode(GuildMessageReactionAddEvent reactionEvent) {
		for(AudioTrack track : tracks) {
			musicManager.scheduler.queue(track);
		}
	}

	@Override
	public boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getMessageIdLong() == messageId && !reactionEvent.getUser().isBot())
			return true;
		else
			return false;
	}

}
