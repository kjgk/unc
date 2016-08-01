package com.unicorn.core;

import com.unicorn.utils.Encodes;
import com.unicorn.utils.Exceptions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;


@Service(value = "passwordEncoder")
public class PasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        if (encodedPassword.indexOf("|-|") > 0) {
            String password = encodedPassword.split("[|][-][|]")[0];
            String salt = encodedPassword.split("[|][-][|]")[1];
            byte[] hashPassword = PasswordEncoder.digest(rawPassword.toString().getBytes(), Encodes.decodeHex(salt), 1024);
            return StringUtils.equals(Encodes.encodeHex(hashPassword), password);
        }
        return super.matches(rawPassword, encodedPassword);
    }


    private static byte[] digest(byte[] input, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw Exceptions.unchecked(e);
        }
    }
}
