package com.warrows.plugins.EtherHardcore;

import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Loads Plugin and manages Data/Permissions
 * 
 * @author Warrows
 */
public class EtherHardcore extends JavaPlugin
{
	protected static Economy	economy;
	private Logger				log;

	@Override
	public void onEnable()
	{
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			economy = economyProvider.getProvider();

		log = this.getLogger();
		log.info("EtherHard activé");
	}

	@Override
	public void onDisable()
	{
		log.info("EtherHard desactivé");
	}
}