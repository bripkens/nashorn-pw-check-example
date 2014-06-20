package de.codecentric;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.fail;

/**
 * @author Ben Ripkens <ben.ripkens@codecentric.de>
 */
public class StrengthCheckerTest {

  private StrengthChecker checker;

  @Before
  public void before() {
    checker = new StrengthChecker();
  }

  @Test
  public void shouldIdentifyWeakPasswods() {
    Strength strength = checker.check("password");
    assertThat(strength.getScore(), is(0));
    assertThat(strength.getCrackTimeSeconds(), is(0));
    assertThat(strength.getEntropy(), is(0));
  }

  @Test
  public void shouldIdentifyStrongPasswods() {
    Strength strength = checker.check("jo'2131k'dsa9'?!");
    assertThat(strength.getScore(), is(4));
    assertThat(strength.getCrackTimeSeconds(), is(2147483647)); // ~68 years
    assertThat(strength.getEntropy(), is(69)); // bits
  }

  @Test
  public void shouldAvoidScriptInjection() {
    try {
      Strength strength = checker.check("');throw new Error();print('oh oh");
      assertThat(strength, is(not(nullValue())));
    } catch (RuntimeException e) {
      // should the script injection work, a ScriptException would
      // be thrown. This ScriptException would in turn be wrapped
      // in a RuntimeException.
      fail("Script Injection seems to have worked :-(.");
    }
  }
}
