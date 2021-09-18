package Dinkel.Musikman.Manager;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketManager extends ListenerAdapter{

	private List<PollTicket> tickets = new ArrayList<PollTicket>();
	
	public void addTicket(PollTicket ticket) {
		tickets.add(ticket);
		System.out.println("hehe");
	}
	
	public void removeTicket(PollTicket ticket) {
		tickets.remove(ticket);
	}
	
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent reactionEvent) {
		for(PollTicket t : tickets) {
			if(t.isRightTicket(reactionEvent)) {
				t.TicketCode(reactionEvent);
			}
		}
	}
}
