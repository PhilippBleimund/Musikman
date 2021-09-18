package Dinkel.Musikman.Commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import Dinkel.Musikman.Musikman_Main;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class play extends Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args, TicketManager ticketManager) {
		TextChannel channel = eventMessage.getChannel();
		
		if(args.isEmpty()) {
			channel.sendMessage("add arguments").queue();
			return;
		}
		
		Member self = eventMessage.getGuild().getSelfMember();
		GuildVoiceState selfVoiceState = self.getVoiceState();
		
		if(!selfVoiceState.inVoiceChannel()) {
			channel.sendMessage("I need to be in a voice channel").queue();
			return;
		}
		
		Member member = eventMessage.getMember();
		GuildVoiceState memberVoiceState = member .getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel()) {
			channel.sendMessage("You are not in a channel").queue();
			return;
		}
		
		if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
			channel.sendMessage("we are not in the same voice channel");
			return;
		}
		
		String link = String.join(" ", args);
		
		if(!isURL(link)) {
			link = "ytsearch:" + link;
		}
		
		PlayerManager.getInstance().loadAndPlay(channel, link, ticketManager);
	}

	@Override
	public String getName() {
		return "play";
	}

	private boolean isURL(String url) {
		try {
			new URI(url);
			return true;
		}catch(URISyntaxException e) {
			return false;
		}
	}
}
