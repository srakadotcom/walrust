package pl.pogerteam.walrust.structure;

import org.bukkit.Material;

public enum StructureLevel {

  NONE(Material.AIR, Material.AIR),
  HAY(Material.HAY_BLOCK, Material.OAK_LOG),
  WOOD(Material.OAK_LOG, Material.OAK_PLANKS),
  STONE(Material.OAK_PLANKS, Material.COBBLESTONE),
  METAL(Material.IRON_BLOCK, Material.STONE),
  HEAVY_METAL(Material.CRYING_OBSIDIAN, Material.NETHERITE_BLOCK);

  private final Material first, secondary;

  StructureLevel(Material first, Material secondary) {
    this.first = first;
    this.secondary = secondary;
  }

  public Material getFirst() {
    return first;
  }

  public Material getSecondary() {
    return secondary;
  }
}
