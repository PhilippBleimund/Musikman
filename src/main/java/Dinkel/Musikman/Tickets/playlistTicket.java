package Dinkel.Musikman.Tickets;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Dinkel.Musikman.Manager.PollTicket;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class playlistTicket extends PollTicket{

	long messageId;
	
	public playlistTicket(List<AudioTrack> tracks, long messageId) {
		this.messageId = messageId;
	}
	
	@Override
	public void TicketCode(GuildMessageReactionAddEvent reactionEvent) {
		// TODO Auto-generated method stub
		System.out.println(messageId);
	}

	@Override
	public boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getMessageIdLong() == messageId && !reactionEvent.getUser().isBot())
			return true;
		else
			return false;
	}

}
