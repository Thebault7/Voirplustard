package fr.voirplustard.service;


import java.util.Random;

/**
 * Classe en charge de crypter et décrypter une chaîne de caractères
 * par la méthode de Vigenère.
 * On ajoute une lettre aléatoire devant le message à coder afin
 * de changer la taille du message une fois codé.
 * Chaque lettre du message à coder est ensuite convertie en un entier
 * qui dépend de la position du caractère dans LISTE_CARACTERES.
 * On y ajoute alors une lettre de la clé, elle-même préalablement convertie
 * en entier.
 * Le résultat de cette somme est enfin reconvertie grâce à LISTE_CARACTERES
 * en un caractère pour constituer le message codé.
 * 
 * Le processus de décodage est exactement l'inverse du processus de codage.
 * 
 * @version Encodage - v1.0
 * @author Frédéric Thébault
 * @date 5 nov. 2020
 *
 */
public class Encodage implements InterfaceEncodage {
	
	private static final String LISTE_CARACTERES = "S£/3~*674gU=q'ù[;knR9J%YMN>Gl02X)jyçC]Qb1K(IWPTµ^,&}F<x+_.5pEwLHsc:#?|fèt -Be@aOA§Zm$¤iDé!dvozVh{r8àu`";
	private static final String CLE = "123456789";
	
	/**
	 * Constructeur par défaut
	 */
	public Encodage() {
	}
	
	/**
	 * Méthode qui transforme une chaîne de caractère en une autre chaîne de caractères.
	 * Le codage se fait par addition de la chaîne avec une clé
	 * et par l'utilisation d'une liste de caractères.
	 * {@inheritDoc}
	 * @see fr.InterfaceEncodage.cryptage.InterfaceCryptage#crypter(java.lang.String)
	 */
	@Override
	public String crypter(String motACrypter) {
		Random random = new Random();
		int positionLettreAleatoire = random.nextInt(LISTE_CARACTERES.length());
		String motCrypte = "" + LISTE_CARACTERES.charAt(positionLettreAleatoire);
		StringBuffer sb = new StringBuffer();
		int index = 0;
		do {
			int numCle = associerNumeroLettre(CLE.charAt(index % CLE.length()));
			int numMot = associerNumeroLettre(motACrypter.charAt(index));
			char lettreCryptee = LISTE_CARACTERES.charAt((numMot + numCle) % LISTE_CARACTERES.length());
			sb.append(lettreCryptee);
			index++;
		} while (index < motACrypter.length());
		motCrypte = motCrypte + sb.toString();
		return motCrypte;
	}
	
	/**
	 * Méthode qui restore une chaîne de caractères cryptée en la chaîne originale.
	 * Le codage se fait par soustraction à la chaîne d'une clé
	 * et par l'utilisation d'une liste de caractères.
	 * {@inheritDoc}
	 * @see fr.InterfaceEncodage.cryptage.InterfaceCryptage#decrypter(java.lang.String)
	 */
	@Override
	public String decrypter(String motADecrypter) {
		motADecrypter = motADecrypter.substring(1, motADecrypter.length());
		String motDecrypte = "";
		StringBuffer sb = new StringBuffer();
		int index = 0;
		do {
			int numCle = associerNumeroLettre(CLE.charAt(index % CLE.length()));
			int numCode = associerNumeroLettre(motADecrypter.charAt(index));
			char lettreDecryptee = LISTE_CARACTERES.charAt(((numCode - numCle) + LISTE_CARACTERES.length()) % LISTE_CARACTERES.length());
			sb.append(lettreDecryptee);
			index++;
		} while (index < motADecrypter.length());
		motDecrypte = sb.toString();
		return motDecrypte;
	}
	
	/**
	 * 
	 * Méthode en charge d'associer à un caractère son numéro correspondant
	 * dans la liste de caractères fournie
	 * @param lettre
	 * @return
	 */
	public int associerNumeroLettre(char lettre) {
		int numeroLettre = 0;
		for (int i = 0; i < LISTE_CARACTERES.length(); i++) {
			if (lettre == LISTE_CARACTERES.charAt(i)) {
				numeroLettre = i;
				break;
			}
		}
		return numeroLettre;
	}
}
