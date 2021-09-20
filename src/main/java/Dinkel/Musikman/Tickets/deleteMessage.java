package Dinkel.Musikman.Tickets;

import Dinkel.Musikman.Manager.PollTicket;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class deleteMessage extends PollTicket{

	long messageId;
	long commandId;
	
	public deleteMessage(long messageId, long commandId) {
		this.messageId = messageId;
		this.commandId = commandId;
	}
	
	@Override
	public void TicketCode(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getReactionEmote().getEmoji().equals("❌")) {
			reactionEvent.getChannel().deleteMessageById(commandId).queue();
			reactionEvent.getChannel().deleteMessageById(messageId).queue();
			TicketManager.getInstance().removeTicket(this);
		}
	}

	@Override
	public boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent) {
		return (reactionEvent.getMessageIdLong() == this.messageId && !reactionEvent.getUser().isBot());
	}

}
