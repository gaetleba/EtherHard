package com.warrows.plugins.EtherHardcore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.World;
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
	protected static World		world;
	protected static File		directory;

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable()
	{
		log = this.getLogger();
		log.info("EtherHard loading");

		directory = getDataFolder();
		if (!directory.exists())
			directory.mkdir();

		world = this.getServer().getWorlds().get(0);

		// Enregistrement du service economique
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			economy = economyProvider.getProvider();
		log.info("EtherHard: economy found");

		// Enregistrement des listeners
		getServer().getPluginManager().registerEvents(
				new PlayerDeathListener(), this);
		getServer().getPluginManager().registerEvents(
				new EndermanDeathListener(), this);
		getServer().getPluginManager().registerEvents(new Antilogin(), this);
		getServer().getPluginManager().registerEvents(new EpongesListener(),
				this);

		HashMap<String, Long> map = null;
		try
		{
			map = (HashMap<String, Long>) (new ObjectInputStream(
					new FileInputStream(new File(directory, "morts.obj"))))
					.readObject();
		} catch (FileNotFoundException e)
		{
			map = null;
		} catch (IOException e)
		{
			map = null;
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		if (map != null)
		{
			Antilogin.setDeadPlayersMap(map);
			log.info("Liste des morts chargée");
		}

		log.info("EtherHard enabled");
	}

	@Override
	public void onDisable()
	{
		try
		{
			(new ObjectOutputStream(new FileOutputStream(new File(directory,
					"morts.obj")))).writeObject(Antilogin.getDeadPlayersMap());
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		log.info("DeadPlayers saved");
		log.info("EtherHard disabled");
	}
}