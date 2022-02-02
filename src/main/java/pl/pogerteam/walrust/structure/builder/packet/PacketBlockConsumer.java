package pl.pogerteam.walrust.structure.builder.packet;

import org.bukkit.Material;
import org.bukkit.block.Block;

public interface PacketBlockConsumer {
  Material getMaterial(Block block);
}
