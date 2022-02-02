package pl.pogerteam.walrust.structure.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface ClickableInventory extends InventoryHolder {
  void handleClick(InventoryClickEvent event);
}
