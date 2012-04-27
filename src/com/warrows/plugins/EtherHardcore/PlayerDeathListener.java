package com.warrows.plugins.EtherHardcore;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener
{
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		if (((Cancellable) event).isCancelled())
			return;

		Player mort = event.getEntity();
		Player tueur = null;

		if ((mort.getLastDamageCause() instanceof Player))
			tueur = ((Player) mort).getKiller();
		else
		{
			if ((mort.getLastDamageCause() instanceof Projectile))
			{
				if (((Projectile) mort).getShooter() instanceof Player)
					tueur = (Player) ((Projectile) mort).getShooter();
			}
			else 
				EtherHardcore.economy.depositPlayer("Vous êtes mort comme un faible, un Hurbron vous a quitté.", -1.0);
		}
	}
}
