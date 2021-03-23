package me.darrionat.playertimers;

import org.bukkit.plugin.java.JavaPlugin;

import me.darrionat.playertimers.commands.TimerCommand;
import me.darrionat.playertimers.repositories.FileRepository;
import me.darrionat.playertimers.repositories.TimesConfigRepository;
import me.darrionat.playertimers.services.MessageService;
import me.darrionat.playertimers.services.TimerService;
import me.darrionat.playertimers.statics.Bootstrapper;
import me.darrionat.playertimers.statics.Utils;

public class PlayerTimersPlugin extends JavaPlugin {

	private FileRepository fileRepository;
	private TimesConfigRepository timesRepository;

	private MessageService messageService;
	private TimerService timerService;

	@Override
	public void onEnable() {
		Bootstrapper bootstrapper = Bootstrapper.getBootstrapper();
		System.out.println(bootstrapper);
		bootstrapper.init(this);

		fileRepository = bootstrapper.getFileRepository();
		timesRepository = bootstrapper.getTimesConfigRepository();
		messageService = bootstrapper.getMessageService();
		timerService = bootstrapper.getTimerService();

		new TimerCommand(this);
	}

	@Override
	public void onDisable() {

	}

	public void systemLog(String s) {
		System.out.println(Utils.chat("[" + getName() + " " + getDescription().getVersion() + "] " + s));
	}

	public FileRepository getFileRepository() {
		return fileRepository;
	}

	public TimesConfigRepository getTimesRepository() {
		return timesRepository;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public TimerService getTimerService() {
		return timerService;
	}
}