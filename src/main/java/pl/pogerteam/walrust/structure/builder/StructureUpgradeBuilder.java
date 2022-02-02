package pl.pogerteam.walrust.structure.builder;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.pogerteam.walrust.structure.nbt.NbtHelper;

public class StructureUpgradeBuilder extends BlockStructureBuilder{

  public StructureUpgradeBuilder(Location location) {
    super(location);
  }

  @Override
  public void putBlock(Block block, Material material) {
    Location baseLocation = NbtHelper.getStructureBaseLocation(block);
    if (baseLocation == null || !baseLocation.equals(super.baseLocation))
      return;

    block.setType(material);
  }
}
