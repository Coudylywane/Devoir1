package cours.ecole221.records;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record Money(BigDecimal amount) {

    private static final BigDecimal MIN =
            BigDecimal.valueOf(50_000);

    private static final BigDecimal MAX =
            BigDecimal.valueOf(5_000_000);

    public Money {
        Objects.requireNonNull(amount);

        amount = amount.setScale(2, RoundingMode.HALF_UP);

        if (amount.compareTo(MIN) < 0 ||
                amount.compareTo(MAX) > 0) {

            throw new IllegalArgumentException(
                    "Montant invalide"
            );
        }
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money percentage(int percent) {
        return new Money(
                amount.multiply(BigDecimal.valueOf(percent))
                        .divide(BigDecimal.valueOf(100))
        );
    }
}
