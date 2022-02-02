package pl.pogerteam.walrust.structure.inventory;

import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.pogerteam.walrust.WalrustConfiguration;
import pl.pogerteam.walrust.structure.StructureManager;
import pl.pogerteam.walrust.structure.nbt.NbtHelper;
import pl.pogerteam.walrust.structure.nbt.StructureNbtBlock;

public class StructureEditCache {

  private final Map<Location, Gui> editInventoryMap = new HashMap<>();
  private final StructureManager manager;
  private final Plugin plugin;
  private final WalrustConfiguration configuration;

  public StructureEditCache(StructureManager manager, Plugin plugin,
      WalrustConfiguration configuration) {
    this.manager = manager;
    this.plugin = plugin;
    this.configuration = configuration;
  }

  public boolean openInventory(Block block, Player player) {
    StructureNbtBlock nbtBlock = NbtHelper.getStructureNbt(block);
    if (nbtBlock == null) {
      return false;
    }

    Gui gui = editInventoryMap.get(nbtBlock.getBaseLocation());
    if (gui == null) {
      editInventoryMap.put(nbtBlock.getBaseLocation(), gui = load(nbtBlock));
    }
    gui.show(player);
    return true;
  }

  public void invalidate(StructureNbtBlock block) {
    Gui gui = editInventoryMap.remove(block.getBaseLocation());
    if (gui != null) {
      gui.getViewers().forEach(HumanEntity::closeInventory);
    }
  }

  private Gui load(StructureNbtBlock block) {
    try (FileInputStream inputStream = new FileInputStream(
        new File(plugin.getDataFolder(), "structureEdit.xml"))) {
      return Gui.load(
          new StructureEditInventory(manager, block, configuration),
          inputStream);
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
