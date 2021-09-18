package Dinkel.Musikman;

import javax.security.auth.login.LoginException;

import Dinkel.Musikman.Commands.help;
import Dinkel.Musikman.Commands.join;
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
		
		CommandManager manager = new CommandManager();
		manager.addCommand(new help());
		manager.addCommand(new join());
		jda.addEventListener(manager);
	}

}
