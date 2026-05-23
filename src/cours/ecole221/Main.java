package cours.ecole221;
import cours.ecole221.entity.DemandeCredit;
import cours.ecole221.entity.ImportateurFluxCredit;
import cours.ecole221.enumeration.StatutDemande;
import cours.ecole221.records.DocumentGarantie;
import cours.ecole221.records.DureeCredit;
import cours.ecole221.records.Money;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

    public class Main {

        public static void main(String[] args) {

            // TEST 1 : CREATION VALIDE

            try {
                DemandeCredit demande = new DemandeCredit(
                        new Money(BigDecimal.valueOf(100000)),
                        new DureeCredit(12),
                        List.of(
                                new DocumentGarantie("TITRE_FONCIER"),
                                new DocumentGarantie("CONTRAT_TRAVAIL")
                        )
                );

                System.out.println("========== TEST CREATION ==========");
                System.out.println("ID : " + demande.id());
                System.out.println("Statut : " + demande.statut());
                System.out.println("Montant total : "
                        + demande.montantTotal().amount());

            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            // TEST 2 : MONTANT INVALIDE

            try {
                new DemandeCredit(

                        new Money(BigDecimal.valueOf(5000)),
                        new DureeCredit(12),
                        List.of(
                                new DocumentGarantie("CNI"),
                                new DocumentGarantie("BULLETIN_SALAIRE")
                        )
                );

            } catch (Exception e) {
                System.out.println("\n========== TEST MONTANT INVALID ==========");
                System.out.println(e.getMessage());
            }

            // TEST 3 : DUREE INVALIDE

            try {
                new DemandeCredit(
                        new Money(BigDecimal.valueOf(300_000)),
                        new DureeCredit(100),
                        List.of(
                                new DocumentGarantie("CNI"),
                                new DocumentGarantie("BULLETIN_SALAIRE")
                        )
                );

            } catch (Exception e) {
                System.out.println("\n========== TEST DUREE INVALID ==========");
                System.out.println(e.getMessage());
            }

            // TEST 4 : GARANTIES INSUFFISANTES

            try {
                new DemandeCredit(
                        new Money(BigDecimal.valueOf(300_000)),
                        new DureeCredit(12),
                        List.of(
                                new DocumentGarantie("CNI")
                        )
                );

            } catch (Exception e) {
                System.out.println("\n========== TEST GARANTIES ==========");
                System.out.println(e.getMessage());
            }

            // TEST 5 : CYCLE DE VIE

            try {
                DemandeCredit demande = new DemandeCredit(
                        new Money(BigDecimal.valueOf(800_000)),
                        new DureeCredit(10),
                        List.of(
                                new DocumentGarantie("MAISON"),
                                new DocumentGarantie("CONTRAT")
                        )
                );
                System.out.println("\n========== TEST WORKFLOW ==========");
                System.out.println("Etat initial : " + demande.statut());

                demande.passerEnAnalyse();

                System.out.println("Après analyse : "
                        + demande.statut());

                demande.valider();

                System.out.println("Après validation : "
                        + demande.statut());
                demande.rejeter();
            } catch (Exception e) {
                System.out.println("Erreur transition : "
                        + e.getMessage());
            }

            // TEST 6 : IMPORTATION FLUX

            ImportateurFluxCredit importateur =
                    new ImportateurFluxCredit();

            Stream<String> flux = Stream.of(
                    "CL-778;1500000;12;TITRE_FONCIER,CONTRAT_TRAVAIL",
                    "CL-900;abc;12;CNI,MAISON", // montant invalide
                    "CL-100;300000;-5;CNI,MAISON", // durée invalide
                    "", // ligne vide
                    "CL-200;500000;6;CNI", // 1 seule garantie
                    "CL-300;700000;10;MAISON,VOITURE"
            );

            List<DemandeCredit> demandesValides =
                    importateur.traiterFlux(flux);

            System.out.println("\n========== TEST IMPORT ==========");

            System.out.println("Demandes valides : "
                    + demandesValides.size());
            demandesValides.forEach(d ->
                    System.out.println(
                            d.id()
                                    + " | "
                                    + d.montant().amount()
                                    + " | "
                                    + d.statut()
                    )
            );

          // TEST 7 : IMMUTABILITE GARANTIES

            try {
                DemandeCredit demande = new DemandeCredit(
                        new Money(BigDecimal.valueOf(900_000)),
                        new DureeCredit(24),
                        List.of(
                                new DocumentGarantie("TERRAIN"),
                                new DocumentGarantie("SALAIRE")
                        )
                );

                System.out.println("\n========== TEST ENCAPSULATION ==========");
                demande.garanties().add(
                        new DocumentGarantie("FAUX_DOC")
                );

            } catch (Exception e) {

                System.out.println("Modification interdite : "
                        + e.getClass().getSimpleName());
            }
        }
    }

