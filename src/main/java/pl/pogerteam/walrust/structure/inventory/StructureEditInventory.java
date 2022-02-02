package pl.pogerteam.walrust.structure.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pl.pogerteam.walrust.ItemHelper;
import pl.pogerteam.walrust.WalrustConfiguration;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.StructureManager;
import pl.pogerteam.walrust.structure.builder.DestroyStructureBuilder;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.builder.StructureUpgradeBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureNbtBlock;

public class StructureEditInventory {

  private final StructureManager structureManager;
  private final StructureNbtBlock block;
  private final WalrustConfiguration configuration;

  StructureEditInventory(StructureManager structureManager,
      StructureNbtBlock block, WalrustConfiguration configuration) {
    this.structureManager = structureManager;
    this.block = block;
    this.configuration = configuration;
  }

  public void create(InventoryClickEvent event, String structureLevel) {
    StructureLevel level = StructureLevel.valueOf(structureLevel);
    Player player = (Player) event.getWhoClicked();
    Structure structure = structureManager.getStructure(block.getStructureId());
    if (level == StructureLevel.NONE) {
      ItemHelper.giveItems(configuration.getPrice(block.getStructureLevel(),
              (double) block.getHealth() / block.getMaxHealth()),
          player);
      DestroyStructureBuilder builder = new DestroyStructureBuilder(block.getBaseLocation());
      structure.createStructure(builder, StructureLevel.NONE, block.getDirection());
      event.getWhoClicked().sendMessage(configuration.removeSuccessMessage);
    } else {
      if (!ItemHelper.hasItems(configuration.getPrice(level), player)) {
        event.getWhoClicked().sendMessage(configuration.noUpgradeMessage);
        event.getWhoClicked().closeInventory();
        return;
      }

      ItemHelper.takeItems(configuration.getPrice(level), player);
      StructureUpgradeBuilder builder = new StructureUpgradeBuilder(
          block.getBaseLocation());
      structure.createStructure(builder, level, block.getDirection());
      block.setLevel(level, configuration.getHealth(level));
      event.getWhoClicked().sendMessage(configuration.upgradeSuccessMessage);
    }
    event.getWhoClicked().closeInventory();
  }
}
