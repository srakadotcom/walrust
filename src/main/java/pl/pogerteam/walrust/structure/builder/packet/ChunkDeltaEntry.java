package pl.pogerteam.walrust.structure.builder.packet;

import com.comphenix.protocol.wrappers.WrappedBlockData;

public class ChunkDeltaEntry {
  private final WrappedBlockData data;
  private final short location;

  public ChunkDeltaEntry(WrappedBlockData data, short location) {
    this.data = data;
    this.location = location;
  }

  public WrappedBlockData getData() {
    return data;
  }

  public short getLocation() {
    return location;
  }
}
