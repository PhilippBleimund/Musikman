package Dinkel.Musikman.Manager;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public abstract class PollTicket {

	public abstract void TicketCode(MessageReactionAddEvent reactionEvent);
	
	public abstract boolean isRightTicket(MessageReactionAddEvent reactionEvent);
}
