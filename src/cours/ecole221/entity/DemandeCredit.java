package cours.ecole221.entity;

import cours.ecole221.enumeration.StatutDemande;
import cours.ecole221.records.DocumentGarantie;
import cours.ecole221.records.DureeCredit;
import cours.ecole221.records.Money;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

public class DemandeCredit {
    private final String id;
    private final Money montant;
    private final DureeCredit duree;
    private final List<DocumentGarantie> garanties =
            new ArrayList<>();
    private StatutDemande statut;
    public DemandeCredit(
            Money montant,
            DureeCredit duree,
            List<DocumentGarantie> garanties
    ) {
        if (garanties == null || garanties.size() < 2) {
            throw new IllegalArgumentException(
                    "Minimum 2 garanties"
            );
        }
        this.id = UUID.randomUUID().toString();
        this.montant = montant;
        this.duree = duree;
        this.garanties.addAll(garanties);
        this.statut = StatutDemande.NOUVEAU;
    }

    public String id() {
        return id;
    }
    public Money montant() {
        return montant;
    }
    public DureeCredit duree() {
        return duree;
    }
    public StatutDemande statut() {
        return statut;
    }
    public List<DocumentGarantie> garanties() {
        return List.copyOf(garanties);
    }

    public void passerEnAnalyse() {
        if (statut != StatutDemande.NOUVEAU) {
            throw new IllegalStateException(
                    "Transition invalide"
            );
        }
        statut = StatutDemande.EN_ANALYSE;
    }

    public void valider() {
        verifierEtatAnalyse();
        statut = StatutDemande.VALIDE;
    }

    public void rejeter() {
        verifierEtatAnalyse();
        statut = StatutDemande.REJETE;
    }

    private void verifierEtatAnalyse() {
        if (statut != StatutDemande.EN_ANALYSE) {
            throw new IllegalStateException(
                    "La demande doit être en analyse"
            );
        }
    }

    public Money calculerFraisDossier() {
        return montant.percentage(2);
    }
    public Money montantTotal() {
        return montant.add(calculerFraisDossier());
    }
}
