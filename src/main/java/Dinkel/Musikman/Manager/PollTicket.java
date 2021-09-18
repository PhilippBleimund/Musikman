package Dinkel.Musikman.Manager;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public abstract class PollTicket {

	public abstract void TicketCode(GuildMessageReactionAddEvent reactionEvent);
	
	public abstract boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent);
}
