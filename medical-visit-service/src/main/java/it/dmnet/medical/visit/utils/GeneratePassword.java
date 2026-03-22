package it.dmnet.medical.visit.utils;

import io.quarkus.elytron.security.common.BcryptUtil;

public class GeneratePassword {



        public static void main(String[] args) {
            String password = "Password123!";
            String hash = BcryptUtil.bcryptHash(password);
            System.out.println(hash);
        }

}
