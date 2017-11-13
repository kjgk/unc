package com.unicorn.core;

import com.unicorn.utils.RSAUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RSAKeyProducer {

    private String privateKey;

    private String publicKey;

    public void generateRSAKeyPair() throws Exception {

        Map<String, String> keyPair = RSAUtils.generateRSAKeyPair(1024);
        publicKey = keyPair.get("RSAPublicKey");
        privateKey = keyPair.get("RSAPrivateKey");
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
