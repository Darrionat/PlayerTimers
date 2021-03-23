package me.darrionat.playertimers.services;

import java.util.HashMap;
import java.util.UUID;

import me.darrionat.playertimers.PlayerTimer;
import me.darrionat.playertimers.repositories.TimesConfigRepository;

public class TimerService {
	private TimesConfigRepository timesRepo;
	public HashMap<UUID, PlayerTimer> activeTimers = new HashMap<UUID, PlayerTimer>();

	public TimerService(TimesConfigRepository timesConfigRepository) {
		this.timesRepo = timesConfigRepository;
	}

	/**
	 * Checks to see if the player has a timer
	 * 
	 * @param uuid the {@code UUID} of the player
	 * @return returns {@code true} if the player has an active {@code PlayerTimer}
	 */
	public boolean playerHasTimer(UUID uuid) {
		return activeTimers.containsKey(uuid);
	}

	/**
	 * Starts a timer for the player
	 * 
	 * @param uuid the {@code UUID} of the player
	 * @throws IllegalStateException thrown when the player has a timer
	 */
	public void startPlayerTimer(UUID uuid) {
		if (playerHasTimer(uuid))
			throw new IllegalStateException("Cannot start timer if player has timer");
		activeTimers.put(uuid, new PlayerTimer(uuid));
	}

	/**
	 * Stops the {@code PlayerTimer}
	 * 
	 * @param uuid the {@code UUID} of the player
	 * @return returns the {@code PlayerTimer} that was stopped
	 * @throws IllegalStateException thrown when the player does not have a timer
	 */
	public PlayerTimer stopPlayerTimer(UUID uuid) {
		if (!playerHasTimer(uuid))
			throw new IllegalStateException("Cannot stop timer if player does not have timer");

		PlayerTimer timer = activeTimers.remove(uuid).stop();
		timesRepo.savePlayerTime(uuid, timer.getDuration());
		return timer;
	}
}