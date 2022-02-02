package pl.pogerteam.walrust.structure.inventory;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.pogerteam.walrust.ItemHelper;
import pl.pogerteam.walrust.WalrustConfiguration;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.StructureManager;
import pl.pogerteam.walrust.structure.preview.StructurePreviewHandler;

public class StructureSelectInventory {

  private final StructureManager structureManager;
  private final StructurePreviewHandler structurePreviewHandler;
  private final WalrustConfiguration configuration;

  public StructureSelectInventory(StructureManager structureManager,
      StructurePreviewHandler structurePreviewHandler,
      WalrustConfiguration configuration) {
    this.structureManager = structureManager;
    this.structurePreviewHandler = structurePreviewHandler;
    this.configuration = configuration;
  }

  public void create(InventoryClickEvent event, String name) {
    Player player = (Player) event.getWhoClicked();
    if (!ItemHelper.hasItems(configuration.getPrice(StructureLevel.HAY), player)) {
      event.getWhoClicked().sendMessage(configuration.noItemsMessage);
      event.getWhoClicked().closeInventory();
      return;
    }

    ItemHelper.takeItems(configuration.getPrice(StructureLevel.HAY), player);
    structurePreviewHandler.createPreviewSession(((Player) event.getWhoClicked()),
        structureManager.getStructure(name), true);
    event.getWhoClicked().closeInventory();
  }
}
