package com.warrows.plugins.EtherHardcore;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener
{
	@EventHandler(ignoreCancelled = true)
	public void onPlayerDeath(PlayerDeathEvent event)
	{
		Player mort = event.getEntity();
		int drop = 1;
		
		if ((mort.getLastDamageCause().getEntity() instanceof Player))
		{
			int money = (int) EtherHardcore.economy.getBalance(mort.getName());
			if (money>=10)
				drop = money / 10;
		}

		EtherHardcore.economy.withdrawPlayer(mort.getName(), drop);
		Antilogin.addDeadPlayer(mort);
		mort.getInventory().clear();
		ItemStack[] noArmor = new ItemStack[4];
		noArmor[0] = new ItemStack(0);
		noArmor[1] = new ItemStack(0);
		noArmor[2] = new ItemStack(0);
		noArmor[3] = new ItemStack(0);
		mort.getInventory().setArmorContents(noArmor);
		event.getDrops().add(new ItemStack(19, drop));
		mort.kickPlayer("Vous êtes mort");
	}
}
