########################### 1 ########################
# Le fait que DemandeCredit hérite de CompteClient est une erreur de modélisation parce qu' une demande de crédit n’est pas un compte client.
# La relation “is-a” est donc fausse conceptuellement.
# Une DemandeCredit représente un processus métier de financement, tandis qu’un CompteClient représente un moyen de gestion bancaire d’un client.
# Cette conception viole le principe de substitution de Liskov (LSP) :
# Toute sous-classe doit pouvoir remplacer sa classe mère sans altérer le comportement attendu.
# DemandeCredit possède éventuellement une référence vers un client ou un compte.
# Donc relation de composition/association, pas d’héritage.**_~~`

########################### 2 ########################
#la methode getDocumentsGarantie() retourne directement la collection cela détruit totalement l’encapsulation car un développeur externe peut modifier 
#l’état interne sans passer par les règles métier.
# Exemple de concret  
#DemandeCredit demande = new DemandeCredit();
#demande.getDocumentsGarantie().clear();
#demande.getDocumentsGarantie().add("FAUX_DOCUMENT");

########################### 3 ########################
# a) Pourquoi double est une faute professionnelle
# Dans un système bancaire, double introduit des erreurs de précision binaires.
# on a par exemple  0.5 + 0.2 != 0.7
# Cela peut amener des erreurs d’arrondi , pertes financières etc

# b) Pourquoi String statut est dangereux
#Le type String permet de mettre des valeurs invalides comme :
#"VALID", "ok"
#Il ne protège pas les transitions métier.
# Approche DDD  :
# utiliser un enum StatutDemande
# encapsuler les transitions via des méthodes métier :
# passerEnAnalyse()
# valider()
# rejeter()

########################### 4 ########################
# Le service AnalyseCreditService contient toute la logique métier, ce qui crée un modèle anémique.
# Dans un modèle riche :
# A déplacer dans DemandeCredit :
#validation des transitions d’état,
#vérification du nombre minimal de garanties,
#décision métier valider/rejeter,
#contrôle des invariants.
# À déplacer dans Money :
#calcul des frais,
#additions monétaires,
#validations de montants.
# À déplacer dans DureeCredit :
#validation des bornes métier (3 à 60 mois).