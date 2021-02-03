package am.devbooks.service;

import am.devbooks.entity.User;
import am.devbooks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getUserById(int id) {
        return userRepository.getOne(id);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public void updateResetPassword(String token, String email) throws Exception {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new Exception("Could not find any customer with the email" + email);
        }
    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }

    public User getEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
