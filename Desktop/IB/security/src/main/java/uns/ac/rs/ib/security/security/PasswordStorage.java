package uns.ac.rs.ib.security.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import uns.ac.rs.ib.security.util.Base64;

public class PasswordStorage {

			public static byte[] generateSalt() throws NoSuchAlgorithmException {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[8];
			random.nextBytes(salt);
			return salt;
		}
		
		public static String base64Encode(byte[] data) {
			String result = null;
			result = String.valueOf(Base64.encode(data));
			return result; 
		}
		
		public static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
			String algorithm = "PBKDF2WithHmacSHA1";
			int derivedKeyLength = 160; 
			int iterations = 10000; 
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
			SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
			SecretKey sk = f.generateSecret(spec);
			
			return sk.getEncoded();
		}
		
}
