package dev.tlacuilo.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MoneyTest {

  private Money mxnA = Money.ofMinor(2250, "MXN");
  private Money mxnB = Money.ofMinor(150, "MXN");
  private Money usdA = Money.ofMinor(120, "USD");

  @Test
  void testPlus() {
    assertThat(mxnA.plus(mxnB)).isEqualTo(Money.ofMinor(2400, "MXN"));
  }

  @Test
  void testMinus() {
    assertThat(mxnA.minus(mxnB)).isEqualTo(Money.ofMinor(2100, "MXN"));
  }

  @Test
  void testDiffCoins() {
    assertThatThrownBy(() -> usdA.plus(mxnA)).isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> mxnA.plus(usdA)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void testOverflow() {
    Money max = Money.ofMinor(Long.MAX_VALUE, "MXN");
    assertThatThrownBy(() -> max.plus(mxnA)).isInstanceOf(ArithmeticException.class);
  }

  @Test
  void testUnderflow() {
    Money min = Money.ofMinor(Long.MIN_VALUE, "MXN");
    assertThatThrownBy(() -> min.minus(mxnB)).isInstanceOf(ArithmeticException.class);
  }

  @ParameterizedTest
  @CsvSource({
    "-10, true",
    "0, false",
    "-1000000000000, true",
    "1, false",
  })
  void testIsNegative(long minor, boolean expected) {
    Money mon = Money.ofMinor(minor, "MXN");
    assertThat(mon.isNegative()).isEqualTo(expected);
  }
}
