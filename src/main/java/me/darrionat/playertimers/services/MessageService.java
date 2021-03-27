package me.darrionat.playertimers.services;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.darrionat.playertimers.PlayerTimersPlugin;
import me.darrionat.playertimers.repositories.FileRepository;
import me.darrionat.playertimers.statics.Utils;

public class MessageService {
	private PlayerTimersPlugin plugin;
	private FileRepository fileRepository;
	private FileConfiguration messagesConfig;

	private static final String PREFIX_ENABLED = "prefix.enabled";
	private static final String PREFIX = "prefix.prefix";

	private static final String ERR_SECTION = "errors.";
	private static final String ONLY_PLAYERS = ERR_SECTION + "onlyPlayers";
	private static final String NO_PERMISSION = ERR_SECTION + "noPermission";
	private static final String PLAYER_NOT_ONLINE = ERR_SECTION + "playerOffline";
	private static final String PLAYER_NO_TIMER = ERR_SECTION + "noTimer";
	private static final String PLAYER_HAS_TIMER = ERR_SECTION + "hasTimer";
	private static final String NOT_A_NUMBER = ERR_SECTION + "notANumber";

	private static final String MESSAGES_SECTION = "messages.";
	private static final String TIMER_START = MESSAGES_SECTION + "timerStart";
	private static final String TIMER_STOP = MESSAGES_SECTION + "timerStop";

	private static final String COMMAND_MSGS_SECTION = "commandMessages.";
	private static final String HELP_SECTION = COMMAND_MSGS_SECTION + "help";
	private static final String BASE_MESSAGES = COMMAND_MSGS_SECTION + "baseMessages";

	public MessageService(PlayerTimersPlugin plugin, FileRepository fileRepository) {
		this.plugin = plugin;
		this.fileRepository = fileRepository;
		initConfig();
	}

	public void initConfig() {
		messagesConfig = fileRepository.getDataConfig(FileRepository.MESSAGES);
	}

	public void sendCommandHelpMessage(CommandSender sender, String subCommand) {
		sendMessage(sender, messagesConfig.getString(HELP_SECTION + "." + subCommand));
	}

	public void sendOnlyPlayers(CommandSender sender) {
		sendMessage(sender, messagesConfig.getString(ONLY_PLAYERS));
	}

	public void noPermission(Player p, String permission) {
		String msg = getMessage(NO_PERMISSION);
		msg = msg.replace("%permission%", permission);
		sendMessage(p, msg);
	}

	public void sendPlayerOffline(CommandSender sender, String playerName) {
		String msg = getMessage(PLAYER_NOT_ONLINE);
		msg = msg.replace("%player%", playerName);
		sendMessage(sender, msg);
	}

	public void sendTimerStart(CommandSender sender, Player p) {
		String msg = getMessage(TIMER_START);
		msg = msg.replace("%player%", p.getName());
		sendMessage(sender, msg);
	}

	public void sendTimerStop(CommandSender sender, Player p) {
		String msg = getMessage(TIMER_STOP);
		msg = msg.replace("%player%", p.getName());
		sendMessage(sender, msg);
	}

	public void playerHasTimerErr(CommandSender sender, Player p) {
		String msg = getMessage(PLAYER_HAS_TIMER);
		msg = msg.replace("%player%", p.getName());
		sendMessage(sender, msg);
	}

	public void playerNoTimerErr(CommandSender sender, Player p) {
		String msg = getMessage(PLAYER_NO_TIMER);
		msg = msg.replace("%player%", p.getName());
		sendMessage(sender, msg);
	}

	public void notANumberErr(CommandSender sender) {
		sendMessage(sender, NOT_A_NUMBER);
	}

	public void sendBaseMessages(CommandSender sender) {
		for (String s : messagesConfig.getStringList(BASE_MESSAGES)) {
			s = s.replace("%version%", plugin.getDescription().getVersion());
			sender.sendMessage(Utils.chat(s));
		}
	}

	/**
	 * Sends a message with the prefix, if enabled
	 * 
	 * @param sender the {@code CommandSender} to send to
	 * @param msg    the message to send
	 */
	private void sendMessage(CommandSender sender, String msg) {
		if (messagesConfig.getBoolean(PREFIX_ENABLED))
			msg = messagesConfig.getString(PREFIX) + msg;
		sender.sendMessage(Utils.chat(msg));
	}

	public String getMessage(String path) {
		return messagesConfig.getString(path);
	}
}