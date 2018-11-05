/**
 * 
 */
package com.cirb.archiver;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import com.cirb.archiver.batch.utils.JavaPGP;

/**
 * @author Zkaoukab
 *
 */
public class EncryptionDemo {

	public static void main(String[] args) throws GeneralSecurityException, UnsupportedEncodingException {
		String message = "Zach";
		JavaPGP javaPGP = JavaPGP.getInstance();
		System.out.println(javaPGP.encrypt(message));
		System.out.println(javaPGP.decrypt(javaPGP.encrypt(message), javaPGP.getAESKey()));

	}

}
