package Dinkel.Musikman.Commands;

import java.util.List;

import Dinkel.Musikman.Manager.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

public class status extends Command{

    @Override
    public void commandCode(MessageReceivedEvent eventMessage, List<String> args, boolean publicExec) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();

        System.out.println(cpu.getLogicalProcessorCount());
        double load = 100 * cpu.getSystemCpuLoad(1000);
        System.out.println((int)load);

        List<NetworkIF> list = hal.getNetworkIFs();

    }

    @Override
    public String[] getNames() {
        return new String[]{"status"};
    }

    @Override
    public String[] getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "get the current staus of the server";
    }

    @Override
    public boolean showInHelp() {
        return true;
    }
    
}
