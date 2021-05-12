package me.rhys.client.command;

import me.rhys.base.Lite;
import me.rhys.base.command.Command;
import me.rhys.client.module.player.Timer;
import net.minecraft.client.entity.EntityPlayerSP;

public class TimerCommand extends Command {
    public TimerCommand(String label, String usage, String description, String... aliases) {
        super(label, usage, description, aliases);
    }

    @Override
    public boolean handle(EntityPlayerSP player, String label, String[] args) {
        if(args != null && args.length > 0) {
            Timer timer = (Timer) Lite.MODULE_FACTORY.findByClass(Timer.class);

            if(timer != null) {
                try {
                    timer.timerSpeed = Double.parseDouble(args[0]);
                    player.sendMessage(String.format("Set timer speed: %.2f", timer.timerSpeed));
                } catch(NumberFormatException e) {
                    player.sendMessage("Error in formatting of (not a double): \"" + args[0] + "\"");
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }
}
