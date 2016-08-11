package nl.sogyo.tkd.service;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HelloWorldServiceTest {
  @Test
  public void HelloTest() {
    HelloWorldService svc = new HelloWorldService();
    assertEquals("Hi!", svc.hi());
  }
}
