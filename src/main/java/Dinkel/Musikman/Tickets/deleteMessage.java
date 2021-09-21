package Dinkel.Musikman.Tickets;

import Dinkel.Musikman.Manager.PollTicket;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class deleteMessage extends PollTicket{

	long[] messageId;
	
	/**messageIds[0] must be the message with the delete reaction
	 * 
	 * @param messageIds
	 */
	public deleteMessage(long[] messageIds) {
		this.messageId = messageId;
	}
	
	@Override
	public void TicketCode(GuildMessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getReactionEmote().getEmoji().equals("❌")) {
			for(int i=0;i<messageId.length;i++) {
				reactionEvent.getChannel().deleteMessageById(messageId[i]).queue();				
			}
			TicketManager.getInstance().removeTicket(this);
		}
	}

	@Override
	public boolean isRightTicket(GuildMessageReactionAddEvent reactionEvent) {
		return (reactionEvent.getMessageIdLong() == this.messageId[0] && !reactionEvent.getUser().isBot());
	}

}
