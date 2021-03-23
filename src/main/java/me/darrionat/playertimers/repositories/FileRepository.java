package me.darrionat.playertimers.repositories;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.darrionat.playertimers.PlayerTimersPlugin;

public class FileRepository {

	private PlayerTimersPlugin plugin;

	public FileRepository(PlayerTimersPlugin plugin) {
		this.plugin = plugin;
		setupFiles();
	}

	// public static final String CONFIG = "config";
	public static final String MESSAGES = "messages";
	public static final String TIMES = "times";

	private static final String[] CONFIG_NAMES = { MESSAGES };
	private static final String[] DATA_CONFIG_NAMES = { TIMES };
	private static final String[] DIRECTORIES = {};

	private void setupFiles() {
		plugin.systemLog("Setting up files");
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir();
		}

		for (String fileName : CONFIG_NAMES) {
			if (!fileExists(fileName)) {
				plugin.systemLog("Saving " + fileName + ".yml");
				plugin.saveResource(fileName + ".yml", false);
				continue;
			}
			plugin.systemLog("Updating " + fileName + ".yml");
			matchConfig(fileName);
		}

		for (String fileName : DATA_CONFIG_NAMES) {
			if (!fileExists(fileName)) {
				plugin.systemLog("Saving " + fileName + ".yml");
				plugin.saveResource(fileName + ".yml", false);
				continue;
			}
		}

		for (String dir : DIRECTORIES) {
			if (!dirExists(dir))
				mkDir(dir);
		}
	}

	public void setupFile(String fileName) {
		File file = new File(plugin.getDataFolder(), fileName + ".yml");
		setupFile(file);
	}

	private void setupFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				plugin.systemLog("Saving " + file.getName());
			} catch (IOException exe) {
				plugin.systemLog("Failed to create " + file.getName());
				exe.printStackTrace();
			}
		}
	}

	public boolean fileExists(String fileName) {
		return new File(plugin.getDataFolder(), fileName + ".yml").exists();
	}

	public boolean dirExists(String dir) {
		return new File(plugin.getDataFolder(), File.separator + dir).exists();
	}

	public void deleteFile(String fileName) {
		new File(plugin.getDataFolder(), fileName + ".yml").delete();
	}

	public FileConfiguration getDataConfig(String fileName) {
		File file = new File(plugin.getDataFolder(), fileName + ".yml");
		return YamlConfiguration.loadConfiguration(file);
	}

	public File getDataFile(String fileName) {
		return new File(plugin.getDataFolder(), fileName + ".yml");
	}

	public void saveConfigFile(String fileName, FileConfiguration dataConfig) {
		try {
			dataConfig.save(new File(plugin.getDataFolder(), fileName + ".yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void mkDir(String dir) {
		new File(plugin.getDataFolder(), dir).mkdir();
	}

	public void matchConfig(String fileName) {
		InputStream is = plugin.getResource(fileName + ".yml");

		if (is == null)
			return;

		FileConfiguration config = getDataConfig(fileName);
		YamlConfiguration jarConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(is));

		for (String key : jarConfig.getKeys(true))
			if (!config.contains(key)) {
				config.createSection(key);
				config.set(key, jarConfig.get(key));
			}

		for (String key : config.getConfigurationSection("").getKeys(true))
			if (!jarConfig.contains(key))
				config.set(key, null);

		config.set("version", plugin.getDescription().getVersion());
		saveConfigFile(fileName, config);
	}
}