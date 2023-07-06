package hr.java.zavrsni.entities.UserRelated;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HashPass <P, T>{
    public String returnTheHash(P password, T type) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(String.valueOf(password).getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();

        for (byte h : hash){
            sb.append(String.format("%02X",h));
        }
        return sb.toString();
    }
}
