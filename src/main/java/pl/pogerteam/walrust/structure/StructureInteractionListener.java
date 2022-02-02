package pl.pogerteam.walrust.structure;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.pogerteam.walrust.structure.builder.Structure;
import pl.pogerteam.walrust.structure.builder.impl.InteractableStructure;
import pl.pogerteam.walrust.structure.nbt.NbtHelper;
import pl.pogerteam.walrust.structure.nbt.StructureFlag;
import pl.pogerteam.walrust.structure.nbt.StructureNbtBlock;

public class StructureInteractionListener implements Listener {

  private final StructureManager manager;

  public StructureInteractionListener(StructureManager manager) {
    this.manager = manager;
  }

  @EventHandler
  private void onDestroy(PlayerInteractEvent event) {
    if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() == EquipmentSlot.OFF_HAND) {
      return;
    }

    if (!NbtHelper.hasFlag(event.getClickedBlock(), StructureFlag.ALLOW_INTERACTION)) {
      return;
    }

    StructureNbtBlock structureNbtBlock = NbtHelper.getStructureNbt(event.getClickedBlock());
    Structure structure = manager.getStructure(structureNbtBlock.getStructureId());
    if (!(structure instanceof InteractableStructure interactableStructure)) {
      throw new IllegalArgumentException(
          "Specified structure does not extend InteractableStructure interface!");
    }

    interactableStructure.placeInteraction(structureNbtBlock.getBaseLocation().getBlock(), structureNbtBlock.getDirection());
  }
}
