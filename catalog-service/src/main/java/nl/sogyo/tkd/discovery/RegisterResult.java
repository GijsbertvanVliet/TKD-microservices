package nl.sogyo.tkd.discovery;

import java.util.UUID;

public interface RegisterResult {
  /**
   * Retrieve the ID of this announcement (for example used by deregistering a service)
   * @return UUID The unique id of this announcement.
   */
  public UUID getID();
}
