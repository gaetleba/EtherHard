package com.warrows.plugins.EtherHardcore;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player mort = event.getEntity();
		Player tueur = null;

		if ((mort.getLastDamageCause() instanceof Player))
		{
			tueur = ((Player) mort).getKiller();
		} else
		{
			if ((mort.getLastDamageCause() instanceof Projectile))
			{
				if (((Projectile) mort).getShooter() instanceof Player)
				{
					tueur = (Player) ((Projectile) mort).getShooter();
				}
			}
		}

		if (tueur == null)
		{
			EtherHardcore.log.info("Perds un dolls");
			EtherHardcore.economy.withdrawPlayer(mort.getName(), 1.0);
			mort.sendMessage("Vous êtes mort comme un faible, un Hurbron vous a quitté.");
		}
	}
}
