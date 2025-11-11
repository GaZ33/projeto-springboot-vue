package util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestHash {
    public static void main(String[] args) {
        String rawPassword = "batata";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode(rawPassword);
        System.out.println("Novo hash válido: " + encoded);
    }
}
