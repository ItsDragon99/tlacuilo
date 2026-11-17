package dev.tlacuilo.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(long minorUnits, Currency currency) {

  public Money {
    Objects.requireNonNull(currency, "currency");
  }

  public static Money ofMinor(long minor, String isoCode) {
    return new Money(minor, Currency.getInstance(isoCode));
  }

  public Money plus(Money other) {
    requireSameCurrency(other);
    return new Money(Math.addExact(minorUnits, other.minorUnits), currency);
  }

  public Money minus(Money other) {
    requireSameCurrency(other);
    return new Money(Math.subtractExact(minorUnits, other.minorUnits), currency);
  }

  public boolean isNegative() {
    return minorUnits < 0;
  }

  public Money convert(BigDecimal rate, Currency target, RoundingMode mode) {

    BigDecimal major =
        BigDecimal.valueOf(minorUnits).movePointLeft(currency.getDefaultFractionDigits());

    BigDecimal converted = major.multiply(rate).setScale(target.getDefaultFractionDigits(), mode);

    return new Money(
        converted.movePointRight(target.getDefaultFractionDigits()).longValueExact(), target);
  }

  private void requireSameCurrency(Money other) {
    if (!currency.equals(other.currency)) {
      throw new IllegalArgumentException(
          "currency mismatch: " + currency + " vs " + other.currency);
    }
  }
}
