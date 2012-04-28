package com.warrows.plugins.EtherHardcore;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class Antilogin implements Listener
{
	private static HashMap<Player, Long> joueursMorts = new HashMap<Player, Long>();

	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event)
	{
		Player joueur = event.getPlayer();
		/**
		 * Si le joueur n'a plus de point, on ne le connecte pas.
		 */
		if ((int) EtherHardcore.economy.getBalance(joueur.getName()) < 1)
		{
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage("Vous n'avez plus de Horbrun. Demandez à un ami de vous en préter pour revenir.");
		}
		
		/** 
		 * si il a des points et n'est pas mort récement, on le laisse tranquille.
		 */
		if (!joueursMorts.containsKey(event.getPlayer()))
			return;
		
		
		long time = System.currentTimeMillis() - joueursMorts.get(event.getPlayer());
		

		/**
		 * Si le joueur est mort moins d'une heure avant, il doit attendre
		 */
		if (time < 3600000)
		{
			event.setResult(Result.KICK_OTHER);
			event.setKickMessage("Votre corps se régénere. Attendez encore "+(3600000-time)/60000+" minute(s).");
		}
	}
	
	public static boolean addDeadPlayer(Player mort)
	{
		if (joueursMorts.containsKey(mort))
			return false;
		joueursMorts.put(mort, System.currentTimeMillis());
		return true;
	}
}
