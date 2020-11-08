package fr.voirplustard.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


/**
 * 
 * @author Frederic Thebault
 * Date: 5 novembre 2020
 * Classe servant à hacher les mots de passe
 *
 */
public class PasswordHachingGenerator {

	public String hashing(String stringToBeHashed, String stringUsedForSalt) {
		byte[] salt = new byte[16];
		
		// One add the UTF-8 number for each letter of stringUsedForSalt
		int size = 0;
		for (int i = 0; i < stringUsedForSalt.length(); i++) {
			size += stringUsedForSalt.charAt(i);
		}
		
		for (int i = 0; i < 16; i++) {
			// The computation of each salt[i] is done in the following way.
			// The size of the user's pseudo is multiplied by the increment i.
			// It gives an integer that can potentially be outside the byte domain.
			// One take its modulo 258 and then subtract 128. This way, it will always be
			// between -128 and 127, which is within the range of the byte.
			// Finally, one converts from integer to Integer and then to byte.
			salt[i] = new Integer(((i * size) % 256) - 128).byteValue();
		}

		KeySpec spec = new PBEKeySpec(stringToBeHashed.toCharArray(), salt, 65536, 128);
		String finalResult = "";
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] hash = factory.generateSecret(spec).getEncoded();
			
			// The bytes inside hash[] are concatenated to form one resulting string
			for (int i = 0; i < hash.length; i++) {
				finalResult += hash[i];
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return finalResult;
	}
}