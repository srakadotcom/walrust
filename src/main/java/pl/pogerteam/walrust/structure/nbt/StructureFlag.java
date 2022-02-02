package pl.pogerteam.walrust.structure.nbt;

import java.util.Arrays;

public enum StructureFlag {
  FLOOR_COLLISION,
  CEILING_COLLISION,
  ALLOW_INTERACTION;

  public int getValue() {
    return 1 << this.ordinal() ;
  }

  public static int serialize(StructureFlag... rules) {
    return Arrays.stream(rules).mapToInt(
        StructureFlag::getValue).sum();
  }

  public static boolean isAllowed(int value, StructureFlag rule) {
    return (value & rule.getValue()) == rule.getValue();
  }
}
