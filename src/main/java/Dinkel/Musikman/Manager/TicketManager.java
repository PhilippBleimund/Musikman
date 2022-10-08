package Dinkel.Musikman.Manager;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketManager extends ListenerAdapter{

	private static TicketManager INSTANCE;
	
	private List<PollTicket> tickets = new ArrayList<PollTicket>();
	
	public void addTicket(PollTicket ticket) {
		tickets.add(ticket);
	}
	
	public void removeTicket(PollTicket ticket) {
		tickets.remove(ticket);
	}
	
	public void onGuildMessageReactionAdd(MessageReactionAddEvent reactionEvent) {
		System.out.println("reactionAdd");
		for(int i=0;i<tickets.size();i++) {
			PollTicket t = tickets.get(i);
			if(t.isRightTicket(reactionEvent)) {
				t.TicketCode(reactionEvent);
			}
		}
	}
	
	public static TicketManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new TicketManager();
		}
		
		return INSTANCE;
	}
}
