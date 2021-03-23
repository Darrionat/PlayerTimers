package me.darrionat.playertimers.commands.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.darrionat.playertimers.PlayerTimersPlugin;
import me.darrionat.playertimers.commands.BaseCommand;
import me.darrionat.playertimers.services.MessageService;
import me.darrionat.playertimers.services.TimerService;

public abstract class SubCommand {
	private BaseCommand parentCommand;

	private PlayerTimersPlugin plugin;
	private String permission;
	protected MessageService messageService;
	protected TimerService timerService;

	public SubCommand(BaseCommand parentCommand) {
		this.parentCommand = parentCommand;
		this.plugin = parentCommand.getPlugin();

		this.messageService = plugin.getMessageService();
		this.timerService = plugin.getTimerService();

		permission = parentCommand.getCommandLabel() + "." + getSubCommand();
	}

	public BaseCommand getParentCommand() {
		return parentCommand;
	}

	/**
	 * Compares sent arguments and sees if they match with this subcommand
	 * 
	 * @param args the arguments sent by the player from the base command
	 * @return returns {@code true} if the subcommand is this command
	 */
	public boolean isSubCommand(String[] args) {
		return args[0].equalsIgnoreCase(getSubCommand());
	}

	public String getPermission() {
		return permission;
	}

	/**
	 * Sends a message with information about the command to the sender
	 * 
	 * @param sender the sender
	 */
	public void sendHelpMessage(CommandSender sender) {
		messageService.sendCommandHelpMessage(sender, getSubCommand());
	}

	/**
	 * Runs the command
	 * 
	 * @param sender the person who ran the command
	 * @param args   the arguments sent
	 */
	public void run(CommandSender sender, String[] args) {
		if (args.length < getRequiredArgs()) {
			sendHelpMessage(sender);
			return;
		}

		if (!(sender instanceof Player) && onlyPlayers()) {
			messageService.sendOnlyPlayers(sender);
			return;
		}

		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (!p.hasPermission(permission)) {
				messageService.noPermission(p, permission);
				return;
			}
		}
		runCommand(sender, args);
	}

	public abstract int getRequiredArgs();

	public abstract boolean onlyPlayers();

	public abstract String getSubCommand();

	protected abstract void runCommand(CommandSender sender, String[] args);
}