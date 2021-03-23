package me.darrionat.playertimers.interfaces;

import java.util.List;

import me.darrionat.playertimers.commands.subcommands.SubCommand;

/**
 * An interface used to define which commands are able to have subcommands
 */
public interface Heritable {
	public List<SubCommand> getSubCommands();
}