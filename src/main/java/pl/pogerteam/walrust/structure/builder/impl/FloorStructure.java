package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class FloorStructure implements Structure {

  public Matrix2x3D getMatrix(int size) {
    return new Matrix2x3D(size, size, 0, 0, size, size);
  }

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    Structure.fillMatrix(builder, getMatrix(2), level.getSecondary(), StructureFlag.FLOOR_COLLISION);
    Structure.fillMatrix(builder, getMatrix(1), level.getFirst());
  }
}
