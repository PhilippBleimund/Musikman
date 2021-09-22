package Dinkel.Musikman;

import javax.security.auth.login.LoginException;

import Dinkel.Musikman.Commands.catrandom;
import Dinkel.Musikman.Commands.help;
import Dinkel.Musikman.Commands.join;
import Dinkel.Musikman.Commands.leave;
import Dinkel.Musikman.Commands.loop;
import Dinkel.Musikman.Commands.nowPlaying;
import Dinkel.Musikman.Commands.pause;
import Dinkel.Musikman.Commands.play;
import Dinkel.Musikman.Commands.queue;
import Dinkel.Musikman.Commands.restart;
import Dinkel.Musikman.Commands.rule34random;
import Dinkel.Musikman.Commands.shuffle;
import Dinkel.Musikman.Commands.skip;
import Dinkel.Musikman.Commands.stop;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;

public class Musikman_Main {

	public JDA jda;
	
	public static String prefix = "!";
	
	public static void main(String[] args) throws LoginException {
		// TODO Auto-generated method stub
		new Musikman_Main(new String[] {"888441323785420844"});
	}
	
	public Musikman_Main(String[] args) throws LoginException {
		jda = JDABuilder.createDefault(Secret.Token).build();
		jda.getPresence().setStatus(OnlineStatus.IDLE);
		
		CommandManager manager = CommandManager.getInstance();
		manager.addCommand(new help());
		manager.addCommand(new join());
		manager.addCommand(new play());
		manager.addCommand(new stop());
		manager.addCommand(new skip());
		manager.addCommand(new nowPlaying());
		manager.addCommand(new queue());
		manager.addCommand(new leave());
		manager.addCommand(new pause());
		manager.addCommand(new restart());
		manager.addCommand(new rule34random());
		manager.addCommand(new catrandom());
		manager.addCommand(new loop());
		manager.addCommand(new shuffle());
		jda.addEventListener(manager);
		jda.addEventListener(TicketManager.getInstance());
		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(args.length != 0) {
			TextChannel channel = jda.getTextChannelById(args[0]);
			channel.sendMessage("im back on").queue();
		}
	}

}