package com.example.demo.user;

// import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

// import java.util.UUID;
// import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;

    }

    public List<ObjectNode> GetUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {
            ObjectNode node = objectMapper.convertValue(user, ObjectNode.class);
            node.remove("password");
            node.remove("credentialsNonExpired");
            node.remove("accountNonExpired");
            node.remove("accountNonLocked");
            node.remove("usernameDB");
            node.remove("passwordDB");
            return node;
        }).collect(Collectors.toList());
    }
}
