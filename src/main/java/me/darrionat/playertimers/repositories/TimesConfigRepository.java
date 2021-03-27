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

	public void clearPlayerTimes(UUID uuid) {
		timesConfig.set(uuid.toString(), null);
		fileRepository.saveConfigFile(FileRepository.TIMES, timesConfig);
	}

	public void savePlayerTime(PlayerTimer timer) {
		String uuidStr = timer.getPlayer().toString();
		List<Long> timesToSave = new ArrayList<Long>();

		for (long savedTime : timesConfig.getLongList(uuidStr))
			timesToSave.add(savedTime);

		timesToSave.add(timer.getDuration());
		timesConfig.set(uuidStr + "." + timer.getId(), timesToSave);
		fileRepository.saveConfigFile(FileRepository.TIMES, timesConfig);
	}

	/**
	 * Gets all saved timers.
	 * 
	 * @return returns all saved times.
	 */
	public List<PlayerTimer> getAllSavedTimes() {
		List<PlayerTimer> times = new ArrayList<PlayerTimer>();

		// UUIDs
		for (String key : timesConfig.getKeys(false))
			// Different timers
			for (String savedId : timesConfig.getConfigurationSection(key).getKeys(false))
				// Timer's List
				for (long duration : timesConfig.getLongList(key + "." + savedId))
					times.add(new PlayerTimer(UUID.fromString(key), Integer.parseInt(savedId), duration));
		return times;
	}

	/**
	 * Gets the saved times for a particular timer
	 * 
	 * @param id the id of the timer
	 * @return returns all saved times for a certain timer
	 */
	public List<PlayerTimer> getSavedTimes(int id) {
		List<PlayerTimer> times = new ArrayList<PlayerTimer>();
		for (PlayerTimer timer : getAllSavedTimes()) {
			if (timer.getId() == id)
				times.add(timer);
		}
		return times;
	}
}