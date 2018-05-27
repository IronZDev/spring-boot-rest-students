package pl.iwa.mstokfisz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.iwa.mstokfisz.AlreadyExistsException;
import pl.iwa.mstokfisz.NotFoundException;
import pl.iwa.mstokfisz.model.User;
import pl.iwa.mstokfisz.repository.UserRepository;
import pl.iwa.mstokfisz.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		for(User user : findAll()) {
			if(user.getUsername().equals(userId)) {
				return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
			}
		}
		throw new UsernameNotFoundException("Invalid username or password.");
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}

	@Override
	public User findOne(String username) {
		for(User usr : findAll()) {
			if(usr.getUsername().equals(username)) {
				return usr;
			}
		}
		throw new NotFoundException("User not found!");
	}

	@Override
	public User findById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
    public User save(User newUser) {
		for(User usr : findAll()) {
			if(usr.getUsername().equals(newUser.getUsername())) {
				System.out.println("Already exists!");
				throw new AlreadyExistsException("Such user already exists!");
			}
		}
		User user = new User();
		user.setUsername(newUser.getUsername());
		user.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		return userRepository.save(user);
    }
}
