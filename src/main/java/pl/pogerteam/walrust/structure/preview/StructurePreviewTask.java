package pl.pogerteam.walrust.structure.preview;

import java.util.Map;
import java.util.UUID;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.impl.FloorStructure;
import pl.pogerteam.walrust.structure.builder.packet.PacketStructureBuilder;
import pl.pogerteam.walrust.structure.builder.packet.RealPacketStructureBuilder;

public class StructurePreviewTask implements Runnable {

  private final StructurePreviewHandler handler;

  public StructurePreviewTask(
      StructurePreviewHandler handler) {
    this.handler = handler;
  }

  @Override
  public void run() {
    for (Map.Entry<UUID, StructurePreviewEntry> previewEntryEntry : handler.getStructures()
        .entrySet()) {
      Player player = Bukkit.getPlayer(previewEntryEntry.getKey());
      if (player == null) {
        return;
      }

      StructurePreviewEntry entry = previewEntryEntry.getValue();

      Location location;
      if(entry.getStructure() instanceof FloorStructure) {
        location = player.getEyeLocation()
            .add(player.getEyeLocation().getDirection().normalize().multiply(entry.getZoom() * 2.5));

        location.setX(Math.floor(location.getX() / 5.0) * 5.0);
        location.setZ(Math.floor(location.getZ() / 5.0) * 5.0);
        location.add(2.5, 0, 2.5);
      } else {
        location = player.getEyeLocation()
            .add(player.getEyeLocation().getDirection().normalize().multiply(entry.getZoom()));
      }

      BlockFace direction = DirectionHelper.getDirection(player);

      if (entry.hasChanged(direction, location)) {
        PacketStructureBuilder builder = new RealPacketStructureBuilder(
            entry.getPreviousLocation());
        entry.getStructure()
            .createStructure(builder, StructureLevel.NONE, entry.getPreviousDirection());
        builder.send(player);
      }

      entry.setPreviousData(direction, location);
      PacketStructureBuilder builder = new PacketStructureBuilder(location) {
        @Override
        public BlockData getBlockData(Block block) {
          if (block.getType() != Material.AIR) {
            return Material.RED_STAINED_GLASS.createBlockData();
          } else {
            return Material.LIME_STAINED_GLASS.createBlockData();
          }
        }
      };
      entry.getStructure()
          .createStructure(builder, StructureLevel.NONE, entry.getPreviousDirection());
      builder.send(player);

      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
          String.format("Zoom: %.1f | LPM - postaw, PPM - anuluj stawianie kloca",
              entry.getZoom())));
    }
  }
}
