package pl.pogerteam.walrust.structure.nbt;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;

public class StructureNbtBlock {

  private final NBTCompound baseNbt;
  private final Location baseLocation;

  public StructureNbtBlock(NBTCompound baseNbt, Location location) {
    this.baseNbt = baseNbt;
    baseLocation = location;
  }

  public int getHealth() {
    return baseNbt.getInteger("StructureHealth");
  }

  public int getMaxHealth() {
    return baseNbt.getInteger("StructureMaxHealth");
  }

  public void setHealth(int value) {
    baseNbt.setInteger("StructureHealth", value);
  }

  public String getStructureId() {
    return baseNbt.getString("StructureId");
  }

  public StructureLevel getStructureLevel() {
    return StructureLevel.valueOf(baseNbt.getString("StructureLevel"));
  }

  public BlockFace getDirection() {
    return BlockFace.valueOf(baseNbt.getString("StructureDirection"));
  }

  public Location getBaseLocation() {
    return baseLocation;
  }

  public void setLevel(StructureLevel level, int health) {
    baseNbt.setInteger("StructureHealth", (int) (health * (getHealth() / (double) getMaxHealth())));
    baseNbt.setInteger("StructureMaxHealth", health);
    baseNbt.setString("StructureLevel", level.name());
  }
}
