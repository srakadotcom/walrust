package pl.pogerteam.walrust.structure.builder;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.impl.ColidableStructure;
import pl.pogerteam.walrust.structure.builder.impl.FloorStructure;
import pl.pogerteam.walrust.structure.nbt.NbtHelper;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class DefaultBlockStructureBuilder extends BlockStructureBuilder {

  private final Map<Block, Pair> blockMap = new HashMap<>();
  private final Map<Location, Integer> collisions = new HashMap<>();
  private final int collisionCount;

  private int troled;
  private final boolean isTrole;

  private DefaultBlockStructureBuilder(Location location, int collisionCount, boolean isTrole) {
    super(location);
    this.collisionCount = collisionCount;
    this.isTrole = isTrole;
  }

  public static void createStructure(Location baseLocation, BlockFace direction,
      Structure structure, String id, StructureLevel level, int health) {
    DefaultBlockStructureBuilder builder = new DefaultBlockStructureBuilder(baseLocation,
        structure instanceof ColidableStructure colidableStructure
            ? colidableStructure.getRequiredCollisions() : -1, structure instanceof FloorStructure);
    structure.createStructure(builder, level, direction);
    NbtHelper.applyBaseNbtData(baseLocation.getBlock(), id, direction, level, health);
    builder.applyChanges();
  }

  @Override
  public void putBlock(Block block, Material material, StructureFlag rule) {
    if (collisionCount > 0 && NbtHelper.hasFlag(block, rule)) {
      for (Location location : NbtHelper.getStructureBaseLocations(block)) {
        collisions.merge(location, 1, Integer::sum);
      }
    }

    if (isTrole) {
      Block highest = block.getWorld().getHighestBlockAt(block.getLocation());
      if (NbtHelper.getStructureBaseLocation(highest) == null && highest.getRelative(BlockFace.UP)
          .getLocation().equals(block.getLocation())) {
        troled++;
      }
    }

    blockMap.put(block, new Pair(material, rule));
  }

  private int getCollisions() {
    int count = 0;
    for (Integer values : collisions.values()) {
      if (values >= 5) {
        count++;
      }
    }

    return count;
  }

  public void applyChanges() {
    if (collisionCount > 0 && getCollisions() < collisionCount || (isTrole
        && troled <= 8)) { //polowa struktury
      throw new CollisionException();
    }

    for (Map.Entry<Block, Pair> entry : blockMap.entrySet()) {
      Location location = NbtHelper.getStructureBaseLocation(entry.getKey());
      if (location == null || location.equals(baseLocation)) {
        NbtHelper.applyNbtData(entry.getKey(), baseLocation, entry.getValue().rule);
        entry.getKey().setType(entry.getValue().material);
      } else {
        NbtHelper.addShared(entry.getKey(), super.baseLocation);
      }
    }
  }

  private record Pair(Material material, StructureFlag rule) {

  }
}
