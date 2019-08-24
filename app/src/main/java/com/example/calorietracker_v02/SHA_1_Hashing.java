package com.example.calorietracker_v02;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SHA_1_Hashing {

        public static void main(String[] args) throws NoSuchAlgorithmException
        {
            String passwordToHash = "test";
            byte[] salt = getSalt();

            String securePassword = get_SHA_1_SecurePassword(passwordToHash, salt);
            System.out.println(securePassword);
        }

        public static String get_SHA_1_SecurePassword(String passwordToHash, byte[] salt)
        {
            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(salt);
                byte[] bytes = md.digest(passwordToHash.getBytes());
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            return generatedPassword;
        }

        //Add salt
        public static byte[] getSalt() throws NoSuchAlgorithmException
        {
            //TODO:generates a random byte everytime, need to make it constant
            /*We are not securing random bytes here everytime so that the value stays consistent with the database*/
//            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//            byte[] salt = new byte[16];
            String dummy = "Dummy";
            byte[] salt = dummy.getBytes();
//            sr.nextBytes(salt);
            return salt;
        }
    }
