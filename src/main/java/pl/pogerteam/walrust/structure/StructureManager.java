package pl.pogerteam.walrust.structure;

import java.util.HashMap;
import java.util.Map;
import pl.pogerteam.walrust.structure.builder.Structure;

public class StructureManager {

  private final Map<String, Structure> structureMap = new HashMap<>();

  public Structure getStructure(String str) {
    return structureMap.get(str);
  }

  public String getStructureId(Structure structure) {
    for (Map.Entry<String, Structure> structureEntry : structureMap.entrySet()) {
      if (structureEntry.getValue().equals(structure)) {
        return structureEntry.getKey();
      }
    }
    return null;
  }

  public void registerStructure(String id, Structure structure) {
    this.structureMap.put(id, structure);
  }
}
