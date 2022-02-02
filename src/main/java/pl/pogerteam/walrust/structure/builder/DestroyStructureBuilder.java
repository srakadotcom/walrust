package pl.pogerteam.walrust.structure.builder;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import pl.pogerteam.walrust.structure.nbt.NbtHelper;

public class DestroyStructureBuilder extends BlockStructureBuilder {

  public DestroyStructureBuilder(Location location) {
    super(location);
  }

  @Override
  public void putBlock(Block block, Material material) {
    if(NbtHelper.removeShared(block, super.baseLocation)) {
      block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
      block.setType(Material.AIR);
      NbtHelper.clearNbtData(block);
    }
  }
}
