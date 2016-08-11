package nl.sogyo.tkd.discovery.internal;

import nl.sogyo.tkd.discovery.ServiceDiscovery;

public class Factory {
  /**
   * Create a new service discovery instance
   * @param location The location of the service discovery backend
   * @return An initialized service discovery client
   */
  public static ServiceDiscovery consul(String location) {
    return new ConsulServiceDiscovery(location);
  }
}
