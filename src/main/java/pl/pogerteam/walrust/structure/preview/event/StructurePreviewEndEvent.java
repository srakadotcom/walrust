package pl.pogerteam.walrust.structure.preview.event;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import pl.pogerteam.walrust.structure.builder.Structure;

public class StructurePreviewEndEvent extends PlayerEvent implements Cancellable {

  private static final HandlerList handlerList = new HandlerList();
  private final Structure structure;
  private final Location baseLocation;
  private final BlockFace direction;

  private boolean build;
  private boolean cancelled;

  public StructurePreviewEndEvent(Player who,
      Structure structure, Location location, BlockFace blockFace, boolean build) {
    super(who);
    this.structure = structure;
    baseLocation = location;
    this.direction = blockFace;
    this.build = build;
  }

  public Structure getStructure() {
    return structure;
  }

  public Location getBaseLocation() {
    return baseLocation;
  }

  public BlockFace getDirection() {
    return direction;
  }

  /**
   * Checks if structure will be placed (or cancelled)
   */
  public boolean isBuild() {
    return build;
  }

  /**
   * Use this if you want to cancel structure placement
   */
  public void setBuild(boolean build) {
    this.build = build;
  }

  @Override
  public HandlerList getHandlers() {
    return handlerList;
  }

  public static HandlerList getHandlerList() {
    return handlerList;
  }

  /**
   * Use this if you want to end player's sesssion
   */
  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
}
