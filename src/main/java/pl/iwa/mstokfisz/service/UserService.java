package pl.iwa.mstokfisz.service;

import pl.iwa.mstokfisz.model.User;

import java.util.List;

public interface UserService {

    User save(User user);
    List<User> findAll();
    void delete(User user);
    User findOne(String username);

    User findById(Long id);
}
