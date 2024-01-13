package com.scdbackend.service;

import com.scdbackend.data.model.Result;
import com.scdbackend.data.model.User;
import com.scdbackend.data.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ResultService {
    private final ResultRepository repository;
    private final UserService userService;

    @Autowired
    public ResultService(ResultRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Result> findAll() {
        return repository.findAll();
    }

    public void save(Result result) {
        repository.save(result);
    }

    public void deleteById(Long id) {
        if( repository.existsById(id)){
            repository.deleteById(id);
        }
    }

    public List<Result> findByUserId(Long userId){
        User user = userService.findById(userId);
        return repository.findByUserOrderByDateTimeDesc(user);
    }

    public long count() {
        return repository.count();
    }

    public Result updateScreen(Result updatedResult) {
        // Obțineți rezultatul existent din baza de date folosind ID-ul
        Result existingResult = repository.findById(updatedResult.getId())
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + updatedResult.getId()));

        // Actualizați atributele rezultatului existent cu noile valori
        existingResult.setMalign(updatedResult.getMalign());
        existingResult.setBenign(updatedResult.getBenign());
        existingResult.setDateTime(updatedResult.getDateTime());
        existingResult.setImagePath(updatedResult.getImagePath());

        // Salvați rezultatul actualizat în baza de date
        return repository.save(existingResult);
    }

}
