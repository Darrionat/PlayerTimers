package me.darrionat.playertimers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.darrionat.playertimers.PlayerTimersPlugin;
import me.darrionat.playertimers.services.TimerService;

public class ResetTimesCommand extends BaseCommand {
	private TimerService timerService;

	public ResetTimesCommand(PlayerTimersPlugin plugin) {
		super(plugin);
		this.timerService = plugin.getTimerService();
	}

	@Override
	public String getCommandLabel() {
		return "resettimes";
	}

	@Override
	protected void runNoArgs(CommandSender sender, Command command, String label, String[] args) {
		timerService.resetAllRecordedTimes();
	}
}