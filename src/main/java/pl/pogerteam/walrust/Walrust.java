package pl.pogerteam.walrust;

import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pogerteam.walrust.structure.StructureDestroyListener;
import pl.pogerteam.walrust.structure.StructureInteractionListener;
import pl.pogerteam.walrust.structure.StructureManager;
import pl.pogerteam.walrust.structure.builder.impl.CeilingStructure;
import pl.pogerteam.walrust.structure.builder.impl.FloorStructure;
import pl.pogerteam.walrust.structure.builder.impl.LadderCeilingStructure;
import pl.pogerteam.walrust.structure.builder.impl.WallDoorStructure;
import pl.pogerteam.walrust.structure.builder.impl.WallGateStructure;
import pl.pogerteam.walrust.structure.builder.impl.WallGlassStructure;
import pl.pogerteam.walrust.structure.builder.impl.WallStructure;
import pl.pogerteam.walrust.structure.inventory.ClickableInventory;
import pl.pogerteam.walrust.structure.inventory.StructureEditCache;
import pl.pogerteam.walrust.structure.inventory.StructureSelectInventory;
import pl.pogerteam.walrust.structure.preview.StructurePreviewHandler;
import pl.pogerteam.walrust.structure.preview.StructurePreviewListener;
import pl.pogerteam.walrust.structure.preview.StructurePreviewTask;

public final class Walrust extends JavaPlugin implements Listener {

  private final StructurePreviewHandler previewHandler = new StructurePreviewHandler();
  private final StructureManager structureManager = new StructureManager();
  private StructureEditCache editCache;
  private WalrustConfiguration configuration;

  @Override
  public void onEnable() {

    try {
      this.configuration = ConfigManager.create(WalrustConfiguration.class, (it) -> {
        it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
        it.withBindFile(new File(this.getDataFolder(), "config.yml"));
        it.saveDefaults();
        it.load(true);
      });
    } catch (Exception exception) {
      this.getLogger().log(Level.SEVERE, "Error loading config.yml", exception);
      this.getPluginLoader().disablePlugin(this);
      return;
    }

    this.editCache = new StructureEditCache(structureManager, this, configuration);

    structureManager.registerStructure("floor", new FloorStructure()); //fundament
    structureManager.registerStructure("ceiling", new CeilingStructure()); //dziala
    structureManager.registerStructure("ladder_ceiling", new LadderCeilingStructure());

    structureManager.registerStructure("wall", new WallStructure()); //dziala
    structureManager.registerStructure("door_wall", new WallDoorStructure()); //nie
    structureManager.registerStructure("glass_wall", new WallGlassStructure()); //dziala

    structureManager.registerStructure("wall_gate", new WallGateStructure()); //dziala

    saveResource("structureSelect.xml", false);
    saveResource("structureEdit.xml", false);

    getServer().getScheduler()
        .runTaskTimer(this, new StructurePreviewTask(previewHandler), 0L, 1L);
    getServer().getPluginManager().registerEvents(new StructurePreviewListener(previewHandler,
        structureManager, configuration), this);
    getServer().getPluginManager()
        .registerEvents(new StructureDestroyListener(structureManager, editCache), this);
    getServer().getPluginManager()
        .registerEvents(new StructureInteractionListener(structureManager), this);
    getServer().getPluginManager().registerEvents(this, this);
  }

  @EventHandler(priority = EventPriority.HIGH)
  private void onItemClick(PlayerInteractEvent event) {
    if(event.useItemInHand() == Result.DENY)
      return;

    if (event.getItem() != null && (event.getAction() == Action.RIGHT_CLICK_BLOCK
        || event.getAction() == Action.RIGHT_CLICK_AIR) &&
        event.getItem().isSimilar(configuration.item)) {
      if (event.getClickedBlock() != null && editCache.openInventory(event.getClickedBlock(),
          event.getPlayer())) {
        return;
      }

      try (FileInputStream inputStream = new FileInputStream(
          new File(getDataFolder(), "structureSelect.xml"))) {
        Gui.load(new StructureSelectInventory(
            structureManager, previewHandler, configuration), inputStream).show(event.getPlayer());
      } catch (IOException exception) {
        event.getPlayer()
            .sendMessage(ChatColor.RED + "Elo, cos sie popsulo: " + exception.getMessage());
      }
    }
  }

  @EventHandler
  private void onInventoryClick(InventoryClickEvent event) {
    if (event.getClickedInventory() == null || !(event.getClickedInventory()
        .getHolder() instanceof ClickableInventory)) {
      return;
    }

    event.setCancelled(true);
    ((ClickableInventory) event.getClickedInventory().getHolder()).handleClick(event);
  }

  public StructurePreviewHandler getPreviewHandler() {
    return previewHandler;
  }

  public StructureManager getStructureManager() {
    return structureManager;
  }
}
