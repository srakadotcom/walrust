package pl.pogerteam.walrust.structure.builder.impl;

import pl.pogerteam.walrust.structure.builder.Structure;

public interface ColidableStructure extends Structure {
  int getRequiredCollisions();
}
