package pl.pogerteam.walrust;

import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ItemHelper {

  private ItemHelper() {
  }


  public static boolean hasItems(Map<Material, Integer> map, Player player) {
    for (Map.Entry<Material, Integer> entry : map.entrySet()) {
      if (!player.getInventory().contains(entry.getKey(), entry.getValue())) {
        return false;
      }
    }
    return true;
  }

  public static void takeItems(Map<Material, Integer> map, Player player) {
    player.getInventory().removeItem(createItemStacks(map));
  }

  private static ItemStack[] createItemStacks(Map<Material, Integer> map) {
    return map.entrySet()
        .stream()
        .map(entry -> new ItemStack(entry.getKey(), entry.getValue()))
        .toArray(ItemStack[]::new);
  }

  public static String translateItems(Map<Material, Integer> map) {
    StringBuilder builder = new StringBuilder();
    for (Entry<Material, Integer> entry : map.entrySet()) {
      if (entry.getValue() <= 0) {
        continue;
      }

      builder.append(entry.getValue()).append("x ").append(entry.getKey().name());
    }

    if(builder.isEmpty())
      return "Nic xd";
    return builder.toString();
  }

  public static void giveItems(Map<Material, Integer> map, Player player) {
    player.getInventory().addItem(createItemStacks(map));
  }

}
