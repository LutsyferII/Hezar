package main.blocks;

import org.bukkit.configuration.ConfigurationSection;

public class BlockSubTag{
	public String name;
	public String value;
	public BlockSubTag(ConfigurationSection c) {
		name = c.getString("name");
		value = c.getString("value");
	}
}