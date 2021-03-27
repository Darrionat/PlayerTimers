package me.darrionat.playertimers;

import java.util.UUID;

public class PlayerTimer {
	private UUID uuid;
	private int id;
	private long start = -1;
	private long end = -1;
	private long duration = -1;

	/**
	 * Creates a new {@code PlayerTimer} and starts the timer
	 * 
	 * @param p the player
	 */
	public PlayerTimer(UUID uuid, int id) {
		this.uuid = uuid;
		this.id = id;
		this.start = System.currentTimeMillis();
	}

	/**
	 * Creates a new {@code PlayerTimer} object with an already defined duration
	 * 
	 * @param p        the player
	 * @param duration the duration of the timer
	 */
	public PlayerTimer(UUID uuid, int id, long duration) {
		this.uuid = uuid;
		this.id = id;
		this.duration = duration;
	}

	/**
	 * Gets the player that the timer is keeping track of
	 * 
	 * @return returns the {@code UUID} of the player
	 */
	public UUID getPlayer() {
		return uuid;
	}

	/**
	 * Checks to see if the timer has stopped
	 * 
	 * @return returns {@code true} if the timer has not stopped
	 */
	public boolean isRunning() {
		return end == -1 && duration == -1;
	}

	/**
	 * Stops the timer
	 * 
	 * @return returns the timer
	 * @throws IllegalStateException thrown when the timer is not running
	 */
	public PlayerTimer stop() {
		if (!isRunning())
			throw new IllegalStateException("Timer cannot be stopped because it is not running");
		this.end = System.currentTimeMillis();
		return this;
	}

	/**
	 * Gets the duration of the timer
	 * 
	 * @return returns the duration of the timer
	 * @throws IllegalStateException thrown when the timer is running
	 */
	public long getDuration() {
		if (isRunning())
			throw new IllegalStateException("Cannot get duration because timer is running");

		if (duration == -1)
			duration = end - start;
		return duration;
	}

	public int getId() {
		return this.id;
	}
}