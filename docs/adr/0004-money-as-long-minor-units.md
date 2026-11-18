# 4. Money as long minor units

Date: 2026-11-18

## Status

Accepted

## Context

The ledger is the source of truth for money. Floating point types cannot represent decimal amounts exactly (`0.1 + 0.2` is not `0.3`), and silent overflow or implicit rounding would corrupt balances without raising any error. Currencies also differ in their number of decimals (JPY has 0, MXN and USD have 2).

## Decision

Represent an amount as a `long` in minor units (for example cents) together with its `Currency`, in an immutable `Money` record.

- Addition and subtraction use `Math.addExact` and `Math.subtractExact`, so overflow throws `ArithmeticException`. Operating on different currencies throws `IllegalArgumentException`.
- Currency conversion uses `BigDecimal` only for the calculation and returns a `long` again. The rounding mode is an explicit parameter; the default policy is `HALF_EVEN` (banker's rounding), which avoids a systematic bias when many conversions are summed.
- The result is converted back with `longValueExact`, so a value that does not fit fails loudly.

## Consequences

- Exact arithmetic with no hidden rounding in the domain model.
- Every conversion states its rounding mode, and ties (x.5) must be tested because `HALF_EVEN` and `HALF_UP` give different results.
- Callers must handle minor units and each currency's fraction digits.
- `BigDecimal` for the whole model was rejected: it is heavier, and exact values in `long` are enough here.
