package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.TrapDoor;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class LadderCeilingStructure extends CeilingStructure implements InteractableStructure {

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    super.createStructure(builder, level, direction);
    builder.putBlock(0, 0, 0, Material.OAK_TRAPDOOR, StructureFlag.ALLOW_INTERACTION);
  }

  @Override
  public void placeInteraction(Block block, BlockFace direction) {
    TrapDoor trapDoor = (TrapDoor) block.getBlockData();
    if (trapDoor.isOpen()) {
      while ((block = block.getRelative(BlockFace.DOWN)).getType() == Material.LADDER) {
        block.setType(Material.AIR, false);
      }
    } else {
      while ((block = block.getRelative(BlockFace.DOWN)).getType() == Material.AIR) {
        block.setType(Material.LADDER, false);
      }
    }
  }
}
