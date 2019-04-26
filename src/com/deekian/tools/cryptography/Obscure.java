package com.deekian.tools.cryptography;

import com.deekian.tools.io.Fichier;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 混淆工具
 */
public class Obscure {
    private static final Map<String, Obscure> instanceSet = new HashMap<>();

    public static Obscure getInstance(String dictPath) throws IOException {
        if (!instanceSet.containsKey(dictPath)) {
            byte[] keyDict = Fichier.readToStream(dictPath);
            instanceSet.put(dictPath, new Obscure(keyDict));
        }
        return instanceSet.get(dictPath);
    }

    private byte[] keyDict;
    private int k;
    private int pow3;
    private Random random = new Random();
    private Base64.Decoder decoder = Base64.getDecoder();
    private Base64.Encoder encoder = Base64.getEncoder();

    private Obscure(byte[] keyDict) {
        this.keyDict = keyDict;
        k = keyDict.length;
        pow3 = (int) Math.cbrt(k);
        pow3 = pow3 > 35 ? 35 : pow3;
    }

    public String encoding(String plaintext) {
        StringBuilder plainStr = new StringBuilder(plaintext);
        StringBuilder bcode = new StringBuilder();
        int position = 1;
        for (int i = 0; i < 3; i++) {
            int p = random.nextInt(pow3) + 1;
            position *= p;
            bcode.append(Integer.toUnsignedString(p, 36));
        }
        plainStr.insert(5, bcode);
        int pl = plainStr.length();
        plaintext = plainStr.toString();
        byte[] bytes = plaintext.getBytes();
        byte[] cipherArr = new byte[pl];

        for (int i = 0; i < pl; i++) {
            if (i > 4 && i < 8) {
                cipherArr[i] = bytes[i];
                continue;
            }
            int idx = (i + position) % k;
            cipherArr[i] = (byte) (bytes[i] ^ keyDict[idx]);
        }
        return encoder.encodeToString(cipherArr);
    }

    public String decoding(String cipherText) {
        byte[] cipherArrs = decoder.decode(cipherText);
        int pl = cipherArrs.length;
        Integer k1 = Integer.valueOf(String.valueOf((char) cipherArrs[5]), 36);
        Integer k2 = Integer.valueOf(String.valueOf((char) cipherArrs[6]), 36);
        Integer k3 = Integer.valueOf(String.valueOf((char) cipherArrs[7]), 36);
        int position = k1 * k2 * k3;
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < pl; i++) {
            if (i > 4 && i < 8) {
                continue;
            }
            int idx = (i + position) % k;
            byte ca = cipherArrs[i];
            byte kd = keyDict[idx];
            plainText.append((char) (ca ^ kd));
        }
        return plainText.toString();
    }
}
