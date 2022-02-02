package pl.pogerteam.walrust.structure.preview;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public final class DirectionHelper {

  public DirectionHelper() {
  }

  private static final BlockFace[] AXIS = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH,
      BlockFace.WEST};

  public static BlockFace getDirection(Player player) {
    return AXIS[Math.round(player.getLocation().getYaw() / 90f) & 0x3].getOppositeFace();
  }

}
