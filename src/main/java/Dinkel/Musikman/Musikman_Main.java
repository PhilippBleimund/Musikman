package Dinkel.Musikman;

import javax.security.auth.login.LoginException;

import Dinkel.Musikman.Commands.help;
import Dinkel.Musikman.Commands.join;
import Dinkel.Musikman.Commands.leave;
import Dinkel.Musikman.Commands.nowPlaying;
import Dinkel.Musikman.Commands.play;
import Dinkel.Musikman.Commands.queue;
import Dinkel.Musikman.Commands.repeat;
import Dinkel.Musikman.Commands.skip;
import Dinkel.Musikman.Commands.stop;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.Manager.TicketManager;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Musikman_Main {

	public JDA jda;
	
	public static String prefix = "!";
	
	public static void main(String[] args) throws LoginException {
		// TODO Auto-generated method stub
		new Musikman_Main();
	}
	
	public Musikman_Main() throws LoginException {
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
		manager.addCommand(new repeat());
		manager.addCommand(new leave() );
		jda.addEventListener(manager);
		jda.addEventListener(TicketManager.getInstance());
	}

}