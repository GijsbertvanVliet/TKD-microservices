package nl.sogyo.tkd.discovery;

import nl.sogyo.tkd.discovery.internal.Factory;

import java.util.concurrent.TimeUnit;

public class Example {

  public static void main(String[] args) throws InterruptedException {
    ServiceDiscovery sd = Factory.consul("localhost:8500");

    sd.Register("foo", 1234, "some-kind-of-tag", "a-differend-kind-of-tag", "spaces are fine as well")
        .map(RegisterResult::getID)
        .doOnNext(id -> System.out.println("Registered with id: " + id.toString()))
        .delay(10, TimeUnit.SECONDS)
        .flatMap(sd::Deregister)
        .doOnNext(id -> System.out.println("Deregistered " + id))
        .subscribe(
            id -> {},
            e -> {
              System.err.println("An exception occurred:");
              e.printStackTrace();
            },
            () -> {
              System.out.println("Completed!");
              System.exit(0);
            }
        );


    long second = 1000;
    Thread.sleep(30 * second);
    System.exit(1);
  }
}
