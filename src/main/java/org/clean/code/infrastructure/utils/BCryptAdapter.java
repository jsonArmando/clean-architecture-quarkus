package org.clean.code.infrastructure.utils;

import jodd.crypt.BCrypt;
import org.clean.code.data.interfaces.Encrypter;

public class BCryptAdapter implements Encrypter {

    @Override
    public String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }
}