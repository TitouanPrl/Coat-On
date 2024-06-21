package io.coaton.clothing.controller;

import java.math.BigInteger;
import java.util.*;

import io.coaton.clothing.dto.ClothingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.coaton.clothing.service.ClothingService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ClothingController {

    @Autowired
    private ClothingService clothingService;

    @GetMapping("/user/{userId}/clothing/{clothingId}")
    public Optional<ClothingDto> getClothing(@PathVariable BigInteger userId, @PathVariable BigInteger clothingId) {
        return clothingService.getClothing(userId, clothingId);
    }

    @RequestMapping(value="/user/{userId}/clothing", method=RequestMethod.GET)
    public List<ClothingDto> getAllClothingFromUser(@PathVariable BigInteger userId, @RequestParam(required = false) Map<String,String> tags) {
        return clothingService.getAllClothingFromUserWithTags(userId, tags);
    }

    @PostMapping("/user/{userId}/clothing")
    public void addClothing(@RequestBody ClothingDto clothing, @PathVariable BigInteger userId) {
        clothing.setUserId(userId);
        clothingService.addClothing(clothing);
    }

    @PutMapping("/user/{userId}/clothing/{clothingId}")
    public void updateClothing(@RequestBody ClothingDto clothing, @PathVariable BigInteger userId,
                               @PathVariable BigInteger clothingId) {
        clothing.setUserId(userId);
        clothing.setId(clothingId);
        clothingService.updateClothing(clothing);
    }

    @DeleteMapping("/user/{userId}/clothing/{clothingId}")
    public void deleteClothing(@PathVariable BigInteger clothingId) {
        clothingService.deleteClothing(clothingId);
    }
}
