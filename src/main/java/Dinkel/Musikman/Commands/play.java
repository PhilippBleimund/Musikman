package Dinkel.Musikman.Commands;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import Dinkel.Musikman.Musikman_Main;
import Dinkel.Musikman.Lavaplayer.GuildMusicManager;
import Dinkel.Musikman.Lavaplayer.PlayerManager;
import Dinkel.Musikman.Manager.Command;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.helper.helper;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class play implements Command{

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
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
		
		GuildMusicManager musicManager = PlayerManager.getInstance().getMusikManager(eventMessage.getGuild());
		
		if(!helper.isURL(link)) {
			link = "ytsearch:" + link;
		}
		
		String arg1 = args.get(0);
		if(helper.isInteger(arg1)) {
			int number = Integer.parseInt(arg1);
			musicManager.scheduler.directPlay(number);
			channel.sendMessage("skiped to postion `" + number + "`").queue();
			return;
		}
		
		PlayerManager.getInstance().loadAndPlay(channel, link);
	}
	
	@Override
	public String[] getNames() {
		return new String[]{"play", "p"};
	}

	@Override
	public String getDescription() {
		return "add the song to the queue";
	}

	@Override
	public String[] getArgs() {
		return new String[] {"URL", "smart search(title)", "position in queue"};
	}

	@Override
	public boolean showInHelp() {
		return true;
	}
}
