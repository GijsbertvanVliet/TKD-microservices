package nl.sogyo.tkd.discovery.internal;

import nl.sogyo.tkd.discovery.RegisterResult;

import java.util.UUID;

class ConsulRegisterResult implements RegisterResult {

  private final UUID id;

  ConsulRegisterResult(UUID id) {
    this.id = id;
  }
  @Override
  public UUID getID() {
    return this.id;
  }
}
