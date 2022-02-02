package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class WallGateStructure extends WallStructure implements InteractableStructure {

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    super.createStructure(builder, level, direction);

    switch (direction) {
      case NORTH, SOUTH -> {
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            builder.putBlock(i, j, 0, Material.IRON_BARS, StructureFlag.ALLOW_INTERACTION);
          }
        }
      }
      case EAST, WEST -> {
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            builder.putBlock(0, i, j, Material.IRON_BARS, StructureFlag.ALLOW_INTERACTION);
          }
        }
      }
    }
  }

  @Override
  public void placeInteraction(Block block, BlockFace direction) {
    if (block.getRelative(BlockFace.DOWN).getType() == Material.IRON_BARS) {
      fill(block, direction, Material.AIR);
    } else {
      fill(block, direction, Material.IRON_BARS);
    }
  }

  private static void fill(Block block, BlockFace direction, Material type) {
    switch (direction) {
      case NORTH, SOUTH -> {
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 0; j++) {
            block.getRelative(i, j, 0).setType(type);
          }
        }
      }
      case EAST, WEST -> {
        for (int i = -1; i <= 0; i++) {
          for (int j = -1; j <= 1; j++) {
            block.getRelative(0, i, j).setType(type);
          }
        }
      }
    }
  }
}
