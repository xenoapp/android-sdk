package app.xenoapp.sdk;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class XenoIdentity {
    private HashMap<String, String> attributes;

    public XenoIdentity() {
        this.attributes = new HashMap<>();
        setName("Unknown");
        setCustomAttribute("kind", "lead");
    }

    public XenoIdentity setId(String id) {
        try {
            setCustomAttribute("user_hash", generateHashWithHmac256(id, Xeno.getInstance().getSecretKey()));
            setCustomAttribute("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public XenoIdentity setId(String id, String secretKey) {
        try {
            setCustomAttribute("user_hash", generateHashWithHmac256(id, secretKey));
            setCustomAttribute("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public XenoIdentity setName(String name) {
        return setCustomAttribute("name", name);
    }

    public XenoIdentity setEmail(String email) {
        return setCustomAttribute("email", email);
    }

    public XenoIdentity setAvatar(String avatar) {
        return setCustomAttribute("avatar", avatar);
    }

    public XenoIdentity setRegisteredAt(String registeredAt) {
        return setCustomAttribute("registered_at", registeredAt);
    }

    public XenoIdentity setCustomAttribute(String key, String value) {
        this.attributes.put(key, value);
        return this;
    }

    public String build() {
        return new JSONObject(attributes).toString();
    }

    private String generateHashWithHmac256(String message, String key) {
        try {
            final String hashingAlgorithm = "HmacSHA256"; //or "HmacSHA1", "HmacSHA512"
            byte[] bytes = hmac(hashingAlgorithm, key.getBytes(), message.getBytes());
            return bytesToHex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static byte[] hmac(String algorithm, byte[] key, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(message);
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

// new XenoIdentity().withId("123").name('Yolo').Attribute('lol', 'paslol')