package pl.pogerteam.walrust;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.exception.OkaeriException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.pogerteam.walrust.structure.StructureLevel;

@Header("# Walrust - ZAJEBISTY (NA SERIO) PLAGIN NA RDZE!")
@Header("# STWORZONE PRZEZ POGERTEAM(MEMERURKI) PONDRO")
public class WalrustConfiguration extends OkaeriConfig {
  private Map<StructureLevel, LevelEntry> prices = Map.of(
      StructureLevel.HAY, new LevelEntry(
          Map.of(Material.HAY_BLOCK, 8, Material.OAK_WOOD, 20),
          10
      ),
      StructureLevel.WOOD, new LevelEntry(
          Map.of(Material.OAK_PLANKS, 8, Material.OAK_WOOD, 20),
          50
      ),
      StructureLevel.METAL, new LevelEntry(
          Map.of(Material.IRON_INGOT, 64),
          250
      ),
      StructureLevel.HEAVY_METAL, new LevelEntry(
          Map.of(Material.DIAMOND, 64),
          1000
      )
  );
  @Comment("Item uzywany do budowania")
  public ItemStack item = new ItemStack(Material.PAPER, 1);
  @Comment("Wiadomosc wyswietlana jezeli gracz nie bedzie mial itemow do ulepszenia")
  public String noUpgradeMessage = "&cNie masz itemow do ulepszenia struktury!";
  @Comment("Wiadomosc wyswietlana jezeli gracz nie bedzie mial itemow do utworzenia struktury")
  public String noItemsMessage = "&cNie masz itemow do utworzenia struktury!";
  @Comment("Wiadomosc wyswietlana jak uda sie ulepszyc strukture")
  public String upgradeSuccessMessage = "&aPomyslnei ulepsono strukture!";
  @Comment("Wiadomosc wyswietlana przy usunieciu struktury")
  public String removeSuccessMessage = "&cPomyslnie usunieto strukture!";

  @Override
  public OkaeriConfig load() throws OkaeriException {
    OkaeriConfig config = super.load();
    this.noUpgradeMessage = ChatColor.translateAlternateColorCodes('&', this.noUpgradeMessage);
    this.noItemsMessage = ChatColor.translateAlternateColorCodes('&', this.noItemsMessage);
    this.upgradeSuccessMessage = ChatColor.translateAlternateColorCodes('&', this.upgradeSuccessMessage);
    this.removeSuccessMessage = ChatColor.translateAlternateColorCodes('&', this.removeSuccessMessage);
    return config;
  }

  public Map<Material, Integer> getPrice(StructureLevel level) {
    return prices.containsKey(level) ? prices.get(level).price : Collections.emptyMap();
  }

  public Map<Material, Integer> getPrice(StructureLevel level, double multiplier) {
    return getPrice(level).entrySet()
        .stream()
        .collect(Collectors.toMap(Entry::getKey, entry -> (int) (entry.getValue() * multiplier)));
  }

  public int getHealth(StructureLevel level) {
    return prices.containsKey(level) ? prices.get(level).health : 0;
  }

  public static final class LevelEntry extends OkaeriConfig {

    @Comment("Cena struktury")
    private final Map<Material, Integer> price;
    @Comment("Ilosc HP struktury")
    private final int health;

    public LevelEntry(Map<Material, Integer> price, int health) {
      this.price = price;
      this.health = health;
    }
  }
}
