package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class CeilingStructure implements ColidableStructure {

  public Matrix2x3D getMatrix(int size) {
    return new Matrix2x3D(size, size, 0, 0, size, size);
  }

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    Structure.fillMatrix(builder, getMatrix(2), level.getSecondary(),
        StructureFlag.CEILING_COLLISION);
    Structure.fillMatrix(builder, getMatrix(1), level.getFirst());
  }

  @Override
  public int getRequiredCollisions() {
    return 2;
  }
}
