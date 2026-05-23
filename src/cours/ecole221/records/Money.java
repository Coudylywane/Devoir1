package cours.ecole221.records;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


public record Money(BigDecimal amount) {

    private static final BigDecimal MIN =
            BigDecimal.valueOf(50000);

    private static final BigDecimal MAX =
            BigDecimal.valueOf(5000000);

    public Money {

        Objects.requireNonNull(amount);

        amount = amount.setScale(2, RoundingMode.HALF_UP);

        if (amount.compareTo(MIN) < 0 && amount.compareTo(MAX) > 0) {
            throw new IllegalArgumentException(
                    "Montant doit être entre 50 000 et 5 000 000 FCFA"
            );
        }

    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money percentage(int percent) {
        return new Money(
                amount.multiply(BigDecimal.valueOf(percent))
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
        );
    }
}