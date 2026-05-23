package cours.ecole221.entity;

import cours.ecole221.records.DocumentGarantie;
import cours.ecole221.records.DureeCredit;
import cours.ecole221.records.Money;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportateurFluxCredit {
    public List<DemandeCredit> traiterFlux(
            Stream<String> lignesBrutes
    ) {
        return lignesBrutes
                .filter(ligne -> !ligne.isBlank())
                .map(this::parserLigne)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
    private Optional<DemandeCredit> parserLigne(
            String ligne
    ) {
        try {
            String[] colonnes = ligne.split(";");
            if (colonnes.length < 4) {
                return Optional.empty();
            }
            BigDecimal montant =
                    new BigDecimal(colonnes[1]);
            int duree =
                    Integer.parseInt(colonnes[2]);
            List<DocumentGarantie> garanties =
                    Arrays.stream(colonnes[3].split(","))
                            .map(String::trim)
                            .filter(g -> !g.isBlank())
                            .map(DocumentGarantie::new)
                            .toList();

            DemandeCredit demande =
                    new DemandeCredit(
                            new Money(montant),
                            new DureeCredit(duree),
                            garanties
                    );
            return Optional.of(demande);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
