package me.darrionat.playertimers.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import me.darrionat.playertimers.PlayerTimer;

public class TimesConfigRepository {
	private FileRepository fileRepository;
	private FileConfiguration timesConfig;

	public TimesConfigRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
		initConfig();
	}

	public void initConfig() {
		this.timesConfig = fileRepository.getDataConfig(FileRepository.TIMES);
	}

	public List<UUID> getPlayersWithTimes() {
		List<UUID> players = new ArrayList<UUID>();
		for (String key : timesConfig.getKeys(false))

			players.add(UUID.fromString(key));

		return players;
	}

	public List<PlayerTimer> getAllSavedTimes() {
		List<PlayerTimer> times = new ArrayList<PlayerTimer>();

		for (String key : timesConfig.getKeys(false))
			for (long duration : timesConfig.getLongList(key))
				times.add(new PlayerTimer(UUID.fromString(key), duration));

		return times;
	}

	public void savePlayerTime(UUID uuid, long duration) {
		String uuidStr = uuid.toString();
		List<Long> timesToSave = new ArrayList<Long>();

		for (long savedTime : timesConfig.getLongList(uuidStr))
			timesToSave.add(savedTime);

		timesToSave.add(duration);
		timesConfig.set(uuidStr, timesToSave);
		fileRepository.saveConfigFile(FileRepository.TIMES, timesConfig);
	}
}