package pl.pogerteam.walrust.structure.preview;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import pl.pogerteam.walrust.structure.builder.Structure;

final class StructurePreviewEntry {

  private final Structure structure;
  private BlockFace previousDirection;
  private Location previousLocation;
  private final boolean isFirst;
  private double zoom = 3.0d;

  public StructurePreviewEntry(Structure structure, boolean isFirst) {
    this.structure = structure;
    this.isFirst = isFirst;
  }

  public boolean hasChanged(BlockFace previousDirection, Location previousLocation) {
    return this.previousLocation != null && (!previousDirection.equals(this.previousDirection) ||
        !previousLocation.equals(this.previousLocation));
  }

  public void setPreviousData(BlockFace previousDirection, Location previousLocation) {
    this.previousDirection = previousDirection;
    this.previousLocation = previousLocation;
  }

  public Structure getStructure() {
    return structure;
  }

  public double getZoom() {
    return zoom;
  }

  public void incrementZoom() {
    if (this.zoom < 10) {
      this.zoom += 0.5;
    }
  }

  public void decrementZoom() {
    if (this.zoom > 3) {
      this.zoom -= 0.5;
    }
  }

  public boolean isFirst() {
    return isFirst;
  }

  public BlockFace getPreviousDirection() {
    return previousDirection;
  }

  public Location getPreviousLocation() {
    return previousLocation;
  }
}
