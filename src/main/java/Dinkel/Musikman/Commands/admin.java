package Dinkel.Musikman.Commands;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import Dinkel.Musikman.Information;
import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class admin implements Command {

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		long idLong = eventMessage.getAuthor().getIdLong();
		for(int i=0;i<Information.admins.length;i++) {
			if(idLong == Information.admins[i]) {
				i = Integer.MAX_VALUE;
				if (args.get(0).equalsIgnoreCase("restart")) {
					long channelId = eventMessage.getChannel().getIdLong();
					restartApplication(Long.toString(channelId));
					return;
				}else if(args.get(0).equalsIgnoreCase("shutdown")) {
					System.exit(0);
					return;
				}
			}
		}
	}

	public void restartApplication(String arg) {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File currentJar = null;
		try {
			currentJar = new File(admin.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* is it a jar file? */
		if (!currentJar.getName().endsWith(".jar"))
			return;

		/* Build command: java -jar application.jar */
		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());
		command.add(arg);

		final ProcessBuilder builder = new ProcessBuilder(command);
		try {
			builder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public String[] getNames() {
		return new String[] { "admin" };
	}

	@Override
	public String[] getArgs() {
		return new String[] { "shutdown", "restart" };
	}

	@Override
	public String getDescription() {
		return "special commands for the admin";
	}

	@Override
	public boolean showInHelp() {
		return true;
	}

}
