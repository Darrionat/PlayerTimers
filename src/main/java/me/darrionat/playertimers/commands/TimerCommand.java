package me.darrionat.playertimers.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import me.darrionat.playertimers.PlayerTimersPlugin;
import me.darrionat.playertimers.commands.subcommands.SubCommand;
import me.darrionat.playertimers.commands.subcommands.TimerStart;
import me.darrionat.playertimers.commands.subcommands.TimerStop;
import me.darrionat.playertimers.interfaces.Heritable;
import me.darrionat.playertimers.services.MessageService;

public class TimerCommand extends BaseCommand implements Heritable {

	private MessageService messageService;
	private List<SubCommand> subcommands = new ArrayList<SubCommand>();

	public TimerCommand(PlayerTimersPlugin plugin) {
		super(plugin);
		messageService = plugin.getMessageService();
		subcommands.add(new TimerStart(this));
		subcommands.add(new TimerStop(this));
	}

	@Override
	public String getCommandLabel() {
		return "timer";
	}

	@Override
	protected void runNoArgs(CommandSender sender, Command command, String label, String[] args) {
		messageService.sendBaseMessages(sender);
	}

	public List<SubCommand> getSubCommands() {
		return subcommands;
	}
}