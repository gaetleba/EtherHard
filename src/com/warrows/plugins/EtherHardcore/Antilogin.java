package com.warrows.plugins.EtherHardcore;

import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Antilogin implements Listener
{
	private static HashMap<String, Long>	joueursMorts	= new HashMap<String, Long>();
	
	public static void setDeadPlayersMap(HashMap<String, Long> map)
	{
		joueursMorts = map;
	}
	
	public static HashMap<String, Long> getDeadPlayersMap()
	{
		return joueursMorts;
	}

	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event)
	{
		String joueur = event.getPlayer().getName();
		
		if (!EtherHardcore.economy.hasAccount(joueur))
			return;

		/**
		 * Si le joueur n'a plus de point, on ne le connecte pas.
		 */
		if ((int) EtherHardcore.economy.getBalance(joueur) < 1)
		{
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage("Vous n'avez plus de Horbrun. Demandez à un ami de vous en préter pour revenir.");
			return;
		}

		/**
		 * si il a des points et n'est pas mort récement, on le laisse
		 * tranquille.
		 */
		if (!joueursMorts.containsKey(joueur))
			return;

		long time = System.currentTimeMillis()
				- joueursMorts.get(joueur);

		/**
		 * Si le joueur est mort moins d'une heure avant, il doit attendre
		 */
		if (time < 1200000)
		{
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage("Votre corps se régénere. Attendez encore "
					+ ( (1200000 - time) / 60000 + 1) +" minute(s).");
		}
		else
		{
			joueursMorts.remove(event.getPlayer().getName());
		}
	}

	public static boolean addDeadPlayer(String mort)
	{
		if (joueursMorts.containsKey(mort))
			return false;
		joueursMorts.put(mort, System.currentTimeMillis());
		return true;
	}
}
