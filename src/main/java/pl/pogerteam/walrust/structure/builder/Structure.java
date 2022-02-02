package pl.pogerteam.walrust.structure.builder;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.StructureLevel;
import pl.pogerteam.walrust.structure.builder.impl.Matrix2x3D;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public interface Structure {

  void createStructure(StructureBuilder builder, StructureLevel level, BlockFace direction);

  static void fillMatrix(StructureBuilder builder, Matrix2x3D matrix, Material material) {
    for (double x = (-matrix.xMin()); x <= (matrix.xMax()); x++) {
      for (double y = (-matrix.yMin()); y <= (matrix.yMax()); y++) {
        for (double z = (-matrix.zMin()); z <= (matrix.zMax()); z++) {
          builder.putBlock((int) x, (int) y, (int) z, material);
        }
      }
    }
  }


  static void fillMatrix(StructureBuilder builder, Matrix2x3D matrix, Material material, StructureFlag rule) {
    for (double x = (-matrix.xMin()); x <= (matrix.xMax()); x++) {
      for (double y = (-matrix.yMin()); y <= (matrix.yMax()); y++) {
        for (double z = (-matrix.zMin()); z <= (matrix.zMax()); z++) {
          builder.putBlock((int) x, (int) y, (int) z, material, rule);
        }
      }
    }
  }
}
