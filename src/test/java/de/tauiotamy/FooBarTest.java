package de.tauiotamy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Demo test class.
 */
class FooBarTest {

  @Test
  void dummyTest() {
    FooBar f = new FooBar();
    Assertions.assertEquals(0, f.dummy(), "Dummy value is not correct!");
  }

}
