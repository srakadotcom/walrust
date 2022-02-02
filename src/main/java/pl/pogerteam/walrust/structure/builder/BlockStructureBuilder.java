package pl.pogerteam.walrust.structure.builder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public abstract class BlockStructureBuilder implements StructureBuilder {

  protected final Location baseLocation;

  protected BlockStructureBuilder(Location location) {
    baseLocation = location;
  }

  @Override
  public void putBlock(int offsetX, int offsetY, int offsetZ, Material material) {
    putBlock(offsetX, offsetY, offsetZ, material, null);
  }

  @Override
  public void putBlock(int offsetX, int offsetY, int offsetZ, Material material, StructureFlag rule) {
    putBlock(baseLocation.getWorld().getBlockAt(
        offsetX + baseLocation.getBlockX(),
        offsetY + baseLocation.getBlockY(),
        offsetZ + baseLocation.getBlockZ()
    ), material, rule);
  }

  public void putBlock(Block block, Material material, StructureFlag rule) {
    putBlock(block, material);
  }

  public void putBlock(Block block, Material material) {
    throw new UnsupportedOperationException();
  }
}
