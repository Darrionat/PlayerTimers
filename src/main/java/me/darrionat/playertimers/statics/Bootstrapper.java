package me.darrionat.playertimers.statics;

import me.darrionat.playertimers.PlayerTimersPlugin;
import me.darrionat.playertimers.repositories.FileRepository;
import me.darrionat.playertimers.repositories.TimesConfigRepository;
import me.darrionat.playertimers.services.MessageService;
import me.darrionat.playertimers.services.TimerService;

public class Bootstrapper {
	private static Bootstrapper instance;

	private FileRepository fileRepository;
	private TimesConfigRepository timesRepository;

	private MessageService messageService;
	private TimerService timerService;

	private Bootstrapper() {

	}

	public void init(PlayerTimersPlugin plugin) {
		this.fileRepository = new FileRepository(plugin);
		this.timesRepository = new TimesConfigRepository(fileRepository);

		this.messageService = new MessageService(plugin, fileRepository);
		this.timerService = new TimerService(timesRepository);
	}

	public static Bootstrapper getBootstrapper() {
		if (instance == null)
			instance = new Bootstrapper();
		return instance;
	}

	public FileRepository getFileRepository() {
		return fileRepository;
	}

	public TimesConfigRepository getTimesConfigRepository() {
		return timesRepository;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public TimerService getTimerService() {
		return timerService;
	}
}