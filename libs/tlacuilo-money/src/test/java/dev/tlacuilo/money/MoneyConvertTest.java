package dev.tlacuilo.money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class MoneyConvertTest {

  @ParameterizedTest
  @CsvSource({
    "1050, MXN, USD, 0.05, HALF_EVEN, 52",
    "1050, MXN, USD, 0.05, HALF_UP,   53",
  })
  void testConvert(
      long minor, String from, String to, String rate, RoundingMode mode, long expected) {
    Money source = Money.ofMinor(minor, from);
    BigDecimal rateConverted = new BigDecimal(rate);
    Currency toConverted = Currency.getInstance(to);
    Money result = source.convert(rateConverted, toConverted, mode);
    assertThat(result.minorUnits()).isEqualTo(expected);
  }

  @Test
  void testOverflowException() {
    Money test = Money.ofMinor(Long.MAX_VALUE, "MXN");
    assertThatThrownBy(
            () ->
                test.convert(
                    new BigDecimal(10), Currency.getInstance("USD"), RoundingMode.HALF_EVEN))
        .isInstanceOf(ArithmeticException.class)
        .hasMessageContaining("Overflow");
  }
}
