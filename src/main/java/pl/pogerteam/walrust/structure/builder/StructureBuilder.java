package pl.pogerteam.walrust.structure.builder;

import org.bukkit.Material;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;

public interface StructureBuilder {

  default void putBlock(int offsetX, int offsetY, int offsetZ, Material material) {
    putBlock(offsetX, offsetY, offsetZ, material, null);
  }

  default void putBlock(int offsetX, int offsetY, int offsetZ, Material material, StructureFlag rule) {
    throw new UnsupportedOperationException();
  }
}
