package pl.pogerteam.walrust.structure.builder.impl;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.builder.Structure;

public interface InteractableStructure extends Structure {

  default void placeInteraction(Block block, BlockFace direction) {
    throw new UnsupportedOperationException();
  }
}
