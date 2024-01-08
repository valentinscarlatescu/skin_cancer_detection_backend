package com.scdbackend.service;

import com.scdbackend.data.model.User;
import com.scdbackend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
    }

    public User save(User user) {
        return repository.save(user);
    }

    public User updateById(User user, Long id) {

        User dbObj = findById(id);
        dbObj.setFirstName(user.getFirstName());
        dbObj.setLastName(user.getLastName());
        dbObj.setImagePath(user.getImagePath());
        dbObj.setGender(user.getGender());

        return repository.save(dbObj);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NullPointerException("User not found"));
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NullPointerException("Email " + email + " not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByEmail(s);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User with email " + s + " not found");
        }
    }
}
