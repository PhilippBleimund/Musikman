package Dinkel.Musikman.Tickets;

import Dinkel.Musikman.Manager.PollTicket;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

public class deleteMessage extends PollTicket{

	long[] messageId;
	
	/**messageIds[0] must be the message with the delete reaction
	 * 
	 * @param messageIds
	 */
	public deleteMessage(long[] messageIds) {
		this.messageId = messageIds;
	}
	
	@Override
	public void TicketCode(MessageReactionAddEvent reactionEvent) {
		if(reactionEvent.getEmoji().asUnicode().equals("U+274C")) {
			for(int i=0;i<messageId.length;i++) {
				reactionEvent.getChannel().deleteMessageById(messageId[i]).queue();				
			}
			TicketManager.getInstance().removeTicket(this);
		}
	}

	@Override
	public boolean isRightTicket(MessageReactionAddEvent reactionEvent) {
		return (reactionEvent.getMessageIdLong() == this.messageId[0] && !reactionEvent.getUser().isBot());
	}

}
