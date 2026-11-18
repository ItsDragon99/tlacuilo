package dev.tlacuilo.money;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import org.junit.jupiter.api.Test;

public class MoneyPropertiesTest {

  private static final long LIMIT = 1_000_000_000_000L;
  private static final int ITERATIONS = 1000;
  private static final long SEED = 42;
  private static final String CURRENCY = "MXN";

  @Test
  void testPlusAndMinusEqOriginal() {
    Random rand = new Random(SEED);
    for (int i = 0; i < ITERATIONS; i++) {
      long a = rand.nextLong(-LIMIT, (LIMIT + 1));
      long b = rand.nextLong(-LIMIT, (LIMIT + 1));
      Money moneyA = Money.ofMinor(a, CURRENCY);
      Money moneyB = Money.ofMinor(b, CURRENCY);
      Money result = moneyA.plus(moneyB).minus(moneyB);
      assertThat(result).as("a=%d b=%d i=%d", a, b, i).isEqualTo(moneyA);
    }
  }

  @Test
  void testIsCommutative() {
    Random rand = new Random(SEED);
    for (int i = 0; i < ITERATIONS; i++) {
      long a = rand.nextLong(-LIMIT, (LIMIT + 1));
      long b = rand.nextLong(-LIMIT, (LIMIT + 1));
      Money moneyA = Money.ofMinor(a, CURRENCY);
      Money moneyB = Money.ofMinor(b, CURRENCY);
      Money APB = moneyA.plus(moneyB);
      Money BPA = moneyB.plus(moneyA);

      assertThat(APB).as("a=%d b=%d i=%d", a, b, i).isEqualTo(BPA);
    }
  }
}
