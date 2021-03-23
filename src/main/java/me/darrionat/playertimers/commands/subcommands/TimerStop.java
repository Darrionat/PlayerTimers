package me.darrionat.playertimers.commands.subcommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.darrionat.playertimers.commands.BaseCommand;

public class TimerStop extends SubCommand {

	public TimerStop(BaseCommand parentCommand) {
		super(parentCommand);
	}

	@Override
	public int getRequiredArgs() {
		return 2;
	}

	@Override
	public boolean onlyPlayers() {
		return false;
	}

	@Override
	public String getSubCommand() {
		return "stop";
	}

	@Override
	protected void runCommand(CommandSender sender, String[] args) {
		Player target = Bukkit.getPlayer(args[1]);
		if (target == null) {
			messageService.sendPlayerOffline(sender, args[1]);
			return;
		}

		UUID uuid = target.getUniqueId();
		if (timerService.playerHasTimer(uuid)) {
			timerService.stopPlayerTimer(uuid);
			messageService.sendTimerStop(sender, target);
		} else {
			messageService.playerNoTimerErr(sender, target);
		}
	}
}