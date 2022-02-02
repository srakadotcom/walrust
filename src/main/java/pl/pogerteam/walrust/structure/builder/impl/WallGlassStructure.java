package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;

public class WallGlassStructure extends WallStructure {

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    super.createStructure(builder, level, direction);
    Structure.fillMatrix(builder, getMatrix(direction, 1), switch (level) {
      case HAY, WOOD -> Material.GLASS;
      case STONE, METAL -> Material.WHITE_STAINED_GLASS;
      default -> Material.TINTED_GLASS;
    });
  }

}
