package pl.pogerteam.walrust.structure;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.pogerteam.walrust.structure.builder.DestroyStructureBuilder;
import pl.pogerteam.walrust.structure.inventory.StructureEditCache;
import pl.pogerteam.walrust.structure.nbt.NbtHelper;
import pl.pogerteam.walrust.structure.nbt.StructureNbtBlock;

public class StructureDestroyListener implements Listener {

  private final StructureManager manager;
  private final StructureEditCache editCache;

  public StructureDestroyListener(StructureManager manager,
      StructureEditCache editCache) {
    this.manager = manager;
    this.editCache = editCache;
  }

  @EventHandler
  private void onDestroy(BlockBreakEvent event) {
    StructureNbtBlock block = NbtHelper.getStructureNbt(event.getBlock());
    if (block == null) {
      return;
    }

    editCache.invalidate(block);
    event.setCancelled(true);

    int health = block.getHealth();
    if (--health <= 0) {
      DestroyStructureBuilder builder = new DestroyStructureBuilder(block.getBaseLocation());
      manager.getStructure(block.getStructureId())
          .createStructure(builder, StructureLevel.NONE, block.getDirection());
      return;
    }
    block.setHealth(health);

    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
        TextComponent.fromLegacyText(
            health + "/" + block.getMaxHealth() + " HP struktury"));
  }
}
