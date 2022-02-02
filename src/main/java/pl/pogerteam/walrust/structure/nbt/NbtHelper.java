package pl.pogerteam.walrust.structure.nbt;

import de.tr7zw.changeme.nbtapi.NBTBlock;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;

public final class NbtHelper {

  private NbtHelper() {
  }

  public static void clearNbtData(Block block) {
    NBTBlock nbtBlock = new NBTBlock(block);
    nbtBlock.getData().getKeys().forEach(key -> nbtBlock.getData().removeKey(key));
  }

  public static void applyNbtData(Block block, Location location, StructureFlag flag) {
    NBTBlock nbtBlock = new NBTBlock(block);
    nbtBlock.getData().getStringList("StructureLocation").add(serializeLocation(location));
    nbtBlock.getData().setInteger("StructureFlags", flag == null ? 0 : StructureFlag.serialize(flag));
  }

  public static void applyBaseNbtData(Block block, String id, BlockFace face,
      StructureLevel level, int health) {
    NBTBlock nbtBlock = new NBTBlock(block);
    nbtBlock.getData().setString("StructureId", id);
    nbtBlock.getData().setString("StructureDirection", face.name());
    nbtBlock.getData().setInteger("StructureHealth", health);
    nbtBlock.getData().setInteger("StructureMaxHealth", health);
    nbtBlock.getData().setString("StructureLevel", level.name());
  }

  private static Location deserializeLocation(World world, String location) {
    String[] splitted = location.split(":");
    return new Location(
        world,
        Integer.parseInt(splitted[0]),
        Integer.parseInt(splitted[1]),
        Integer.parseInt(splitted[2])
    );
  }

  private static String serializeLocation(Location location) {
    return location.getBlockX() + ":" + location.getBlockY() + ":" + location.getBlockZ();
  }

  public static StructureNbtBlock getStructureNbt(Block block) {
    Location location = getStructureBaseLocation(block);
    if (location == null) {
      return null;
    }

    Block baseBlock = location.getBlock();
    if (getStructureBaseLocation(baseBlock) == null) {
      throw new IllegalArgumentException("Broken structure!");
    }

    return new StructureNbtBlock(new NBTBlock(baseBlock).getData(), location);
  }

  public static List<Location> getStructureBaseLocations(Block block) {
     return new NBTBlock(block).getData().getStringList("StructureLocation")
        .stream()
        .map(str -> deserializeLocation(block.getWorld(), str))
        .toList();
  }

  public static Location getStructureBaseLocation(Block block) {
    List<Location> locations = getStructureBaseLocations(block);
    if (locations.isEmpty()) {
      return null;
    }

    return locations.get(0);
  }

  public static boolean hasFlag(Block block, StructureFlag rule) {
    if(rule == null)
      return false;

    NBTBlock nbtBlock = new NBTBlock(block);

    if (!nbtBlock.getData().hasKey("StructureFlags")) {
      return false;
    }

    return StructureFlag.isAllowed(nbtBlock.getData().getInteger("StructureFlags"), rule);
  }

  public static boolean removeShared(Block block, Location baseLocation) {
    NBTBlock nbtBlock = new NBTBlock(block);

    if (!nbtBlock.getData().hasKey("StructureLocation")) {
      return true;
    }

    nbtBlock.getData().getStringList("StructureLocation").remove(serializeLocation(baseLocation));
    return nbtBlock.getData().getStringList("StructureLocation").isEmpty();
  }

  public static void addShared(Block block, Location baseLocation) {
    NBTBlock nbtBlock = new NBTBlock(block);

    if (!nbtBlock.getData().hasKey("StructureLocation")) {
      return;
    }

    nbtBlock.getData().getStringList("StructureLocation").add(serializeLocation(baseLocation));
  }
}
