package at.flo.springauth;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

@SpringBootApplication
public class SpringauthApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(SpringauthApplication.class, args);
    }

}
