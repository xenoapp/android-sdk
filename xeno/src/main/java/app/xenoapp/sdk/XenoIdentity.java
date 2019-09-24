package app.xenoapp.sdk;

import org.json.JSONObject;

import java.util.HashMap;

public class XenoIdentity {
    private HashMap<String, String> attributes;

    public XenoIdentity() {
        this.attributes = new HashMap<>();
        setName("Unknown");
        setCustomAttribute("kind", "lead");
    }

    public XenoIdentity setId(String id) {
        return setCustomAttribute("id", id);
    }

    public XenoIdentity setIdentityHash(String hash) { return setCustomAttribute("user_hash", hash); }

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
}