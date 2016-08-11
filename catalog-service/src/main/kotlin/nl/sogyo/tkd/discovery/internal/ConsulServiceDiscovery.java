package nl.sogyo.tkd.discovery.internal;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.OperationException;
import com.ecwid.consul.v1.agent.model.NewService;
import nl.sogyo.tkd.discovery.RegisterResult;
import nl.sogyo.tkd.discovery.ServiceDiscovery;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.*;

class ConsulServiceDiscovery implements ServiceDiscovery {
  private ConsulClient client;
  private Map<UUID, String> cache;
  private Scheduler scheduler;

  ConsulServiceDiscovery(String location) {
    client = new ConsulClient(location);
    scheduler = Schedulers.io();
    cache = new HashMap<>();
  }

  @Override
  public Observable<RegisterResult> Register(final String name, int port, final String... tags) {
    return Observable.<RegisterResult>create((obs) -> {
      if (obs.isUnsubscribed()) {
        return;
      }
      UUID id = UUID.randomUUID();
      String serviceid = name + "_" + id.toString();
      NewService newService = new NewService();
      newService.setId(serviceid);
      newService.setName(name);
      newService.setPort(port);
      newService.setTags(Arrays.asList(tags));
      cache.put(id, serviceid);
      try {
        client.agentServiceRegister(newService);
        RegisterResult res = new ConsulRegisterResult(id);
        obs.onNext(res);
        obs.onCompleted();
      } catch (OperationException e) {
        obs.onError(e);
      }
    }).subscribeOn(scheduler);
  }

  @Override
  public Observable<String> Deregister(final UUID id) {
    if (!cache.containsKey(id)) {
      return Observable.error(new IllegalArgumentException("Service with id " + id + " is not know to this client -- cannot register."));
    }
    return Observable.<String>create((obs) -> {
      if (obs.isUnsubscribed()) {
        return;
      }
      try {
        String serviceid = cache.get(id);
        client.agentServiceDeregister(serviceid);
        cache.remove(id);
        obs.onNext(serviceid);
        obs.onCompleted();
      } catch (OperationException e) {
        obs.onError(e);
      }
    }).subscribeOn(scheduler);
  }
}
