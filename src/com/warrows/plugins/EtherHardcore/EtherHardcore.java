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
	protected static Logger		log;
	
	@Override
	public void onEnable()
	{
		log = this.getLogger();	
		log.info("EtherHard loading");
		
		//Enregistrement du service economique
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			economy = economyProvider.getProvider();
		log.info("EtherHard: economie li�e");
		
		//Enregistrement des listeners
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		getServer().getPluginManager().registerEvents(new EndermanDeathListener(), this);
		getServer().getPluginManager().registerEvents(new Antilogin(), this);
		log.info("EtherHard: listener activ�");

		log.info("EtherHard activ�");
	}

	@Override
	public void onDisable()
	{
		log.info("EtherHard desactiv�");
	}
}