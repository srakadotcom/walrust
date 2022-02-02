package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class WallDoorStructure extends WallStructure implements InteractableStructure{

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    super.createStructure(builder, level, direction);
    builder.putBlock(0, 0, 0, Material.LIME_STAINED_GLASS, StructureFlag.ALLOW_INTERACTION);
    builder.putBlock(0, -1, 0, Material.LIME_STAINED_GLASS, StructureFlag.ALLOW_INTERACTION);
  }

  @Override
  public void placeInteraction(Block block, BlockFace direction) {
    Block doorBottomBlock = block.getRelative(0, -1, 0);
    if(doorBottomBlock.getType() == Material.LIME_STAINED_GLASS && block.getType() == Material.LIME_STAINED_GLASS) {
      doorBottomBlock.setType(Material.AIR);
      Block doorUpBlock = doorBottomBlock.getRelative(BlockFace.UP);
      doorUpBlock.setType(Material.AIR);
      BlockData blockData = Bukkit.getServer().createBlockData(Material.IRON_DOOR);
      ((Door) blockData).setOpen(true);
      ((Door) blockData).setFacing(direction);
      Bisected bisected = (Bisected) blockData;

      bisected.setHalf(Bisected.Half.BOTTOM);
      doorBottomBlock.setBlockData(bisected, false);

      bisected.setHalf(Bisected.Half.TOP);
      doorUpBlock.setBlockData(bisected, false);
    } else {
      block.setType(Material.LIME_STAINED_GLASS, false);
      doorBottomBlock.setType(Material.LIME_STAINED_GLASS, false);
    }
  }
}
