package com.scdbackend.controller;

import com.scdbackend.data.model.Result;
import com.scdbackend.data.model.User;
import com.scdbackend.service.ResultService;
import com.scdbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ResultController {

    private final ResultService service;
    private final UserService userService;

    @Autowired
    public ResultController(ResultService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/results")
    public List<Result> findAll() {
        return service.findAll();
    }

    @PostMapping("/results")
    public Result saveResult(@RequestBody Result result) {
        // Validare pentru câmpurile obligatorii
        // service.validateResult(result);

        // Salvare entitate
        return service.save(result);
    }

    @GetMapping("/results/latest")
    public ResponseEntity<Result> findLatestResult() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findByEmail(username);
            Result latestResult = service.findLatestResultByUserId(user.getId());

            if (latestResult != null) {
                return new ResponseEntity<>(latestResult, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/results/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @GetMapping(value = "/results", params = "userId")
    public List<Result> findByUserId(@RequestParam("userId") Long userId) {
        return service.findByUserId(userId);
    }

    @PostMapping("/processImage")
    public ResponseEntity<Result> processImage(@RequestBody Result result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userService.findByEmail(username);

            // Construiește URL-ul endpoint-ului FastAPI
            String fastApiUrl = "http://127.0.0.1:8000/process_images";

            // Trimite cererea către FastAPI
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);  // Setează tipul de conținut la JSON

            // Creează un obiect care să conțină doar calea imaginii pentru a fi trimis la Python
            Map<String, String> imageRequest = new HashMap<>();
            imageRequest.put("path", result.getImagePath());
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(imageRequest, headers);

            // Folosește exchange pentru a obține un răspuns specific de la Python
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    fastApiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            // Obține rezultatul din răspuns
            Map<String, Object> pythonResult = response.getBody();

            // Validează și actualizează obiectul rezultat
            if (pythonResult != null) {
                result.setMalign((int) pythonResult.get("malign"));
                result.setBenign((int) pythonResult.get("benign"));
                result.setUser(user);

                // Salvează rezultatul actualizat în baza de date
                Result savedResult = service.save(result);
                return new ResponseEntity<>(savedResult, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }



}
