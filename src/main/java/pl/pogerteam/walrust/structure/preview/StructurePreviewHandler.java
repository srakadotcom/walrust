package pl.pogerteam.walrust.structure.preview;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.preview.event.StructurePreviewEndEvent;

public class StructurePreviewHandler {

  private final Map<UUID, StructurePreviewEntry> structures = new HashMap<>();

  public void disablePreviewSession(Player player, boolean place) {
    StructurePreviewEntry entry =
        structures.get(player.getUniqueId());
    if (entry == null) {
      return;
    }

    StructurePreviewEndEvent event = new StructurePreviewEndEvent(player, entry.getStructure(),
        entry.getPreviousLocation(),
        entry.getPreviousDirection(), place);
    Bukkit.getServer().getPluginManager().callEvent(event);

    if(!event.isBuild() || !event.isCancelled())
      structures.remove(player.getUniqueId());
  }

  public void createPreviewSession(Player player, Structure structure, boolean isFirst) {
    ItemStack heldItem = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
    if(heldItem != null) {
      player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
      player.getInventory().setItem(4, heldItem);
    } else {
      ItemStack offHand = player.getInventory().getItemInOffHand();
      player.getInventory().setItemInOffHand(null);
      player.getInventory().setItem(4, offHand);
    }
    player.getInventory().setHeldItemSlot(4);
    structures.put(player.getUniqueId(), new StructurePreviewEntry(structure, isFirst));
  }

  public boolean hasSessionActive(Player player) {
    return structures.containsKey(player.getUniqueId());
  }

  public Map<UUID, StructurePreviewEntry> getStructures() {
    return structures;
  }

  StructurePreviewEntry getEntry(Player player) {
    return structures.get(player.getUniqueId());
  }
}
