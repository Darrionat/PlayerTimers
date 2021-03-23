package me.darrionat.playertimers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.darrionat.playertimers.PlayerTimersPlugin;
import me.darrionat.playertimers.commands.subcommands.SubCommand;
import me.darrionat.playertimers.interfaces.Heritable;

public abstract class BaseCommand implements CommandExecutor {
	private PlayerTimersPlugin plugin;

	public BaseCommand(PlayerTimersPlugin plugin) {
		this.plugin = plugin;
		plugin.getCommand(getCommandLabel()).setExecutor(this);
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (this instanceof Heritable && args.length > 0) {
			Heritable heritableCommand = (Heritable) this;
			for (SubCommand subCommand : heritableCommand.getSubCommands())
				if (subCommand.isSubCommand(args)) {
					subCommand.run(sender, args);
					return true;
				}
		}
		runNoArgs(sender, command, label, args);
		return true;
	}

	public abstract String getCommandLabel();

	protected abstract void runNoArgs(CommandSender sender, Command command, String label, String[] args);

	public PlayerTimersPlugin getPlugin() {
		return plugin;
	}
}