package accident;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author ArvikV
 * @version 1.0
 * @since 09.02.2022
 */
public class Main {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode("123456");
        System.out.println(pwd);
    }
}
