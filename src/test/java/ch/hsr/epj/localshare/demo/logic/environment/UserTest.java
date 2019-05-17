package ch.hsr.epj.localshare.demo.logic.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class UserTest {

  @Test
  public void testInstanceCreation() {
    User user = User.getInstance();
    assertNotNull(user);
  }

  @Test
  public void testSettingAndGettingUser() {
    User user = User.getInstance();
    user.setFriendlyName("Foo");
    assertEquals("Foo", user.getFriendlyName());
  }

}