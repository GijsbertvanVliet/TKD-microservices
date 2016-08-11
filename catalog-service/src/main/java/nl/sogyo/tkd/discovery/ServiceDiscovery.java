package nl.sogyo.tkd.discovery;

import rx.Observable;

import java.util.UUID;

public interface ServiceDiscovery {
  /**
   * Register a service
   * @param name The name of the service
   * @param port A port the service is listening on
   * @param tags Optional tags for the service
   * @return An observable with the registration result
   */
  Observable<RegisterResult> Register(String name, int port, String... tags);

  /**
   * Deregister a service. Must be a service registered by this client.
   * @param id The id to register (retrieved after registration)
   * @return An Observable with the removed ID, if successfull
   */
  Observable<String> Deregister(UUID id);
}
