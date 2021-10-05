package Dinkel.Musikman;

import javax.security.auth.login.LoginException;

import Dinkel.Musikman.Commands.admin;
import Dinkel.Musikman.Commands.help;
import Dinkel.Musikman.Commands.Music.join;
import Dinkel.Musikman.Commands.Music.leave;
import Dinkel.Musikman.Commands.Music.loop;
import Dinkel.Musikman.Commands.Music.move;
import Dinkel.Musikman.Commands.Music.nowPlaying;
import Dinkel.Musikman.Commands.Music.pause;
import Dinkel.Musikman.Commands.Music.play;
import Dinkel.Musikman.Commands.Music.queue;
import Dinkel.Musikman.Commands.Music.remove;
import Dinkel.Musikman.Commands.Music.saveQueue;
import Dinkel.Musikman.Commands.Music.shuffle;
import Dinkel.Musikman.Commands.Music.skip;
import Dinkel.Musikman.Commands.Music.stop;
import Dinkel.Musikman.Commands.Music.volume;
import Dinkel.Musikman.Commands.RandomGenerators.catrandom;
import Dinkel.Musikman.Commands.RandomGenerators.konachan;
import Dinkel.Musikman.Commands.RandomGenerators.rule34random;
import Dinkel.Musikman.Manager.CommandManager;
import Dinkel.Musikman.Manager.LogManager;
import Dinkel.Musikman.Manager.TicketManager;
import Dinkel.Musikman.Manager.customJoinSounds;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;

public class Musikman_Main {

	public JDA jda;
	
	public static String prefix = "!";
	
	public static void main(String[] args) throws LoginException {
		// TODO Auto-generated method stub
		Thread printingHook = new Thread(() -> System.out.println("In the middle of a shutdown"));
		new Musikman_Main(args);
	}
	
	public Musikman_Main(String[] args) throws LoginException {
		jda = JDABuilder.createDefault(Information.Token).build();
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
		manager.addCommand(new rule34random());
		manager.addCommand(new catrandom());
		manager.addCommand(new loop());
		manager.addCommand(new shuffle());
		manager.addCommand(new admin());
		manager.addCommand(new remove());
		manager.addCommand(new move());
		manager.addCommand(new konachan());
		manager.addCommand(new saveQueue());
		manager.addCommand(new volume());
		jda.addEventListener(manager);
		jda.addEventListener(TicketManager.getInstance());
		jda.addEventListener(new LogManager());
		jda.addEventListener(new customJoinSounds());
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