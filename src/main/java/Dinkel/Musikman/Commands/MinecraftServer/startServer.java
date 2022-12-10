package Dinkel.Musikman.Commands.MinecraftServer;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class startServer extends Command{

    final GpioController gpio = GpioFactory.getInstance();



    @Override
    public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
        GpioPinDigitalOutput myPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "My LED", PinState.HIGH);
        myPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
        
        String arg = args.get(1);

        if(arg.equals(getArgs()[0])){
            System.out.print("starting");
            myPin.pulse(100);
        }

        if(arg.equals(getArgs()[1])){
            System.out.print("stopping");
            myPin.pulse(2000);
        }
    }

    @Override
    public String[] getNames() {
        return new String[]{"startServer"};
    }

    @Override
    public String[] getArgs() {
        return new String[]{"start" , "shutdown"};
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean showInHelp() {
        return false;
    }
    
}
