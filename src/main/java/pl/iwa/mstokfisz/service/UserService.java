package pl.iwa.mstokfisz.service;

import pl.iwa.mstokfisz.model.Usr;
import pl.iwa.mstokfisz.model.request.LoginUserRequest;

import java.util.List;

public interface UserService {

    Usr save(LoginUserRequest newUser);
    List<Usr> findAll();
    void delete(Usr user);
    Usr findOne(String username);

    Usr findById(Long id);
}
