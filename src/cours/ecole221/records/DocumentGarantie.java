package cours.ecole221.records;
import java.util.Objects;

public record DocumentGarantie(String valeur) {

    public DocumentGarantie {
        Objects.requireNonNull(valeur);

        if (valeur.isBlank()) {
            throw new IllegalArgumentException(
                    "Garantie invalide"
            );
        }
    }
}
