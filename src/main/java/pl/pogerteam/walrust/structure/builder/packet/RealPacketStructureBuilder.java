package pl.pogerteam.walrust.structure.builder.packet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class RealPacketStructureBuilder extends PacketStructureBuilder {

  public RealPacketStructureBuilder(Location location) {
    super(location);
  }

  @Override
  public BlockData getBlockData(Block block) {
    return block.getBlockData();
  }
}
