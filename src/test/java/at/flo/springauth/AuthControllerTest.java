package at.flo.springauth;

import at.flo.springauth.domain.User;
import at.flo.springauth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser() {
        userRepository.save(new User("test", "test@mail.com", "password"));
        var all = userRepository.findAll();
        assertEquals(1, all.size());
    }

}
