package fr.voirplustard.service;

/**
 * Classe en charge d'encoder les donn�es personnelles des utilisateurs telles
 * que le nom et l'email
 * 
 * @version cryptage - v1.0
 * @author Fr�d�ric Th�bault
 * @date 5 nov. 2020
 *
 */
public interface InterfaceEncodage {

	public String crypter(String motACrypter);

	public String decrypter(String motADecrypter);
}
