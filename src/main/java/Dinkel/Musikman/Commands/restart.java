package Dinkel.Musikman.Commands;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class restart implements Command {

	@Override
	public void commandCode(GuildMessageReceivedEvent eventMessage, List<String> args) {
		long channelId = eventMessage.getChannel().getIdLong();
		restartApplication(Long.toString(channelId));
	}

	public void restartApplication(String arg) {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		File currentJar = null;
		try {
			currentJar = new File(restart.class.getProtectionDomain().getCodeSource().getLocation().toURI());
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
		return new String[] { "restart" };
	}

	@Override
	public String getDescription() {
		return "reboots the bot";
	}

	@Override
	public String[] getArgs() {
		// TODO Auto-generated method stub
		return null;
	}

}
