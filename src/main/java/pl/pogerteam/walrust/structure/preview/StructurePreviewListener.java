package pl.pogerteam.walrust.structure.preview;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.pogerteam.walrust.WalrustConfiguration;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.StructureManager;
import pl.pogerteam.walrust.structure.builder.CollisionException;
import pl.pogerteam.walrust.structure.builder.DefaultBlockStructureBuilder;
import pl.pogerteam.walrust.structure.builder.packet.RealPacketStructureBuilder;
import pl.pogerteam.walrust.structure.preview.event.StructurePreviewEndEvent;

public class StructurePreviewListener implements Listener {

  private final StructurePreviewHandler handler;
  private final StructureManager manager;
  private final WalrustConfiguration configuration;

  private final Map<UUID, Long> throttle = new HashMap<>();
  private static final long THROTTLE_DELAY = 250;

  public StructurePreviewListener(
      StructurePreviewHandler handler,
      StructureManager manager, WalrustConfiguration configuration) {
    this.handler = handler;
    this.manager = manager;
    this.configuration = configuration;
  }

  @EventHandler
  private void onInteract(PlayerInteractEvent event) {
    if (event.getAction() == Action.PHYSICAL || event.getHand() == EquipmentSlot.OFF_HAND
        || !handler.hasSessionActive(event.getPlayer())) {
      return;
    }

    event.setCancelled(true);
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK
        || event.getAction() == Action.RIGHT_CLICK_AIR) {
      handler.disablePreviewSession(event.getPlayer(), false);
      event.getPlayer().sendMessage(ChatColor.RED + "Anulowano stawianie kloca!");
    } else if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
      handler.disablePreviewSession(event.getPlayer(), true);
    }
  }

  @EventHandler
  private void onHotbar(PlayerItemHeldEvent event) {
    if (!handler.hasSessionActive(event.getPlayer())) {
      return;
    }

    StructurePreviewEntry entry = handler.getEntry(event.getPlayer());
    if (event.getNewSlot() < event.getPreviousSlot()) {
      entry.incrementZoom();
    } else {
      entry.decrementZoom();
    }

    event.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onStructurePreviewEnd(StructurePreviewEndEvent event) {
    {
      RealPacketStructureBuilder builder = new RealPacketStructureBuilder(event.getBaseLocation());
      event.getStructure().createStructure(builder, StructureLevel.NONE, event.getDirection());
      builder.send(event.getPlayer());
    }

    if (throttle.containsKey(event.getPlayer().getUniqueId())
        && System.currentTimeMillis() <= throttle.get(event.getPlayer().getUniqueId())) {
      event.setCancelled(true);
      return;
    } else {
      this.throttle.put(event.getPlayer().getUniqueId(),
          System.currentTimeMillis() + THROTTLE_DELAY);
    }

    if (event.isBuild()) {
      try {
        DefaultBlockStructureBuilder.createStructure(event.getBaseLocation(),
            event.getDirection(),
            event.getStructure(),
            manager.getStructureId(event.getStructure()),
            StructureLevel.HAY,
            configuration.getHealth(StructureLevel.HAY));
        event.getPlayer().sendMessage(ChatColor.GREEN + "Postawiono kloca!");
      } catch (CollisionException e) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.RED + "Nie mozesz tutaj postawic struktury!");
      }
    }
  }
}
