package fr.voirplustard.service;


import java.util.Random;

/**
 * Classe en charge de crypter et d�crypter une cha�ne de caract�res
 * par la m�thode de Vigen�re.
 * On ajoute une lettre al�atoire devant le message � coder afin
 * de changer la taille du message une fois cod�.
 * Chaque lettre du message � coder est ensuite convertie en un entier
 * qui d�pend de la position du caract�re dans LISTE_CARACTERES.
 * On y ajoute alors une lettre de la cl�, elle-m�me pr�alablement convertie
 * en entier.
 * Le r�sultat de cette somme est enfin reconvertie gr�ce � LISTE_CARACTERES
 * en un caract�re pour constituer le message cod�.
 * 
 * Le processus de d�codage est exactement l'inverse du processus de codage.
 * 
 * @version Encodage - v1.0
 * @author Fr�d�ric Th�bault
 * @date 5 nov. 2020
 *
 */
public class Encodage implements InterfaceEncodage {
	
	private static final String LISTE_CARACTERES = "S�/3~*674gU=q'�[;knR9J%YMN>Gl02X)jy�C]Qb1K(IWPT�^,&}F<x+_.5pEwLHsc:#?|f�t -Be@aOA�Zm$�iD�!dvozVh{r8�u`";
	private static final String CLE = "123456789";
	
	/**
	 * Constructeur par d�faut
	 */
	public Encodage() {
	}
	
	/**
	 * M�thode qui transforme une cha�ne de caract�re en une autre cha�ne de caract�res.
	 * Le codage se fait par addition de la cha�ne avec une cl�
	 * et par l'utilisation d'une liste de caract�res.
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
	 * M�thode qui restore une cha�ne de caract�res crypt�e en la cha�ne originale.
	 * Le codage se fait par soustraction � la cha�ne d'une cl�
	 * et par l'utilisation d'une liste de caract�res.
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
	 * M�thode en charge d'associer � un caract�re son num�ro correspondant
	 * dans la liste de caract�res fournie
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
