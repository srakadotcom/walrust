package pl.pogerteam.walrust.structure.builder.packet;

import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import pl.pogerteam.walrust.structure.builder.BlockStructureBuilder;

public abstract class PacketStructureBuilder extends BlockStructureBuilder {

  private final Multimap<ChunkId, ChunkDeltaEntry> multimap = MultimapBuilder
      .hashKeys()
      .arrayListValues().build();

  protected PacketStructureBuilder(Location location) {
    super(location);
  }

  @Override
  public void putBlock(Block block, Material material) {
    multimap.put(new ChunkId(block.getX() >> 4, block.getY() >> 4, block.getZ() >> 4), new ChunkDeltaEntry(
        WrappedBlockData.createData(getBlockData(block)),
        shortify(block)));
  }

  public abstract BlockData getBlockData(Block block);

  public void send(Player player) {
    for (ChunkId key : multimap.keySet()) {
      Collection<ChunkDeltaEntry> entries = multimap.get(key);
      send(player, key.x(), key.y(), key.z(), entries.stream()
          .map(ChunkDeltaEntry::getData)
          .toArray(WrappedBlockData[]::new), ArrayUtils.toPrimitive(entries.stream()
          .map(ChunkDeltaEntry::getLocation)
          .toArray(Short[]::new)));
    }
  }

  private static void send(Player player, int x, int y, int z, WrappedBlockData[] data,
      short[] positions) {

    PacketContainer container = ProtocolLibrary.getProtocolManager()
        .createPacket(Server.MULTI_BLOCK_CHANGE);

    container.getSectionPositions().write(0,
        new BlockPosition(x, y, z));
    container.getBlockDataArrays()
        .writeSafely(0, data);
    container.getShortArrays()
        .writeSafely(0, positions);

    try {
      ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private static short shortify(Block block) {
    return shortify(block.getX(), block.getY(), block.getZ());
  }

  private static short shortify(int x, int y, int z) {
    x = x & 0xF;
    y = y & 0xF;
    z = z & 0xF;
    return (short) (x << 8 | z << 4 | y);
  }

}
