package yar.web_lab4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yar.web_lab4.DTO.EntryDTO;
import yar.web_lab4.entities.Entry;
import yar.web_lab4.entities.User;
import yar.web_lab4.repositories.EntryRepository;
import yar.web_lab4.security.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/entries")
public class EntriesController {
    @Autowired
    private UserService userService;

    @Autowired
    private EntryRepository entryRepository;

    @GetMapping
    ResponseEntity<?> getUserEntries(Principal principal){
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.findByUser(user));
    }

    @PostMapping
    ResponseEntity<?> addEntry(@Validated @RequestBody EntryDTO entryData, Principal principal){
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.save(new Entry(
                entryData.getX(),
                entryData.getY(),
                entryData.getR(),
                user)));
    }

    @DeleteMapping
    ResponseEntity<?> deleteUserEntries(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(entryRepository.deleteByUser(user));
    }
}
