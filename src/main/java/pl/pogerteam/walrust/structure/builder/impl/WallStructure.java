package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.builder.StructureBuilder;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public class WallStructure implements ColidableStructure {

  public Matrix2x3D getMatrix(BlockFace direction, int size) {
    if (direction == BlockFace.NORTH || direction == BlockFace.SOUTH) {
      return new Matrix2x3D(size, size, size, size, 0, 0);
    } else if (direction == BlockFace.EAST || direction == BlockFace.WEST) {
      return new Matrix2x3D(0, 0, size, size, size, size);
    }
    throw new IllegalArgumentException("Illegal direction");
  }

  @Override
  public void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction) {
    fillMatrix(builder, getMatrix(direction, 2), level.getSecondary());
    Structure.fillMatrix(builder, getMatrix(direction, 1), level.getFirst());
  }


  static void fillMatrix(StructureBuilder builder, Matrix2x3D matrix, Material material) {
    for (double x = (-matrix.xMin()); x <= (matrix.xMax()); x++) {
      for (double y = (-matrix.yMin()); y <= (matrix.yMax()); y++) {
        for (double z = (-matrix.zMin()); z <= (matrix.zMax()); z++) {
          if (y == -matrix.yMin()) {
            builder.putBlock((int) x, (int) y, (int) z, material, StructureFlag.FLOOR_COLLISION);
          } else if(y == matrix.yMax()) {
            builder.putBlock((int) x, (int) y, (int) z, material, StructureFlag.CEILING_COLLISION);
          } else {
            builder.putBlock((int) x, (int) y, (int) z, material);
          }
        }
      }
    }
  }

  @Override
  public int getRequiredCollisions() {
    return 1;
  }
}
