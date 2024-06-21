package io.coaton.clothing.service;

import java.math.BigInteger;
import java.util.*;

import io.coaton.clothing.configuration.KafkaTopicConfiguration;
import io.coaton.clothing.dto.ClothingDto;
import io.coaton.clothing.dto.UserDto;
import io.coaton.clothing.model.Clothing;
import io.coaton.clothing.model.ClothingStatus;
import io.coaton.clothing.model.ClothingTags;
import io.coaton.clothing.repository.ClothingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClothingService {

    @Autowired
    private ClothingRepository clothingRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, ClothingDto> kafkaTemplate;

    // TODO: Uncomment and finalize implementation based on user service implementation
    /*
    @KafkaListener(topics = KafkaTopicConfiguration.Topics.USER)
    public void userTopicListener(UserDto userDto) {

        switch (userDto.getStatus()) {
            case DELETED:
                List<ClothingDto> userClothingDto = clothingRepository.findByUserId(userDto.getId()).stream().map(this::mapToClothingDto).toList();

                for (ClothingDto clothingDto : userClothingDto) {
                    clothingDto.setStatus(ClothingStatus.DELETED);
                    kafkaTemplate.send(KafkaTopicConfiguration.Topics.CLOTHING, clothingDto);
                }

                clothingRepository.deleteClothingByUserId(userDto.getId());
                break;
            case MODIFIED:
                ..?
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unrecognized status of userDto: " + userDto.getStatus());
        }
    }
    */

    public List<ClothingDto> getAllClothingFromUser(BigInteger userId) {
        List<Clothing> clothings = new ArrayList<>(clothingRepository.findByUserId(userId));
        return clothings.stream().map(this::mapToClothingDto).toList();
    }

    public Optional<ClothingDto> getClothing(BigInteger userId, BigInteger clothingId) {
        return clothingRepository.findByUserIdAndId(userId, clothingId).map(this::mapToClothingDto);
    }

    /**
     * Retrieves all clothing items tied to {@code userID} with provided tag values.
     * Clothing matches are checked for with <b>INCLUSIVE OR</b>.
     * @param userId ID of user
     * @param tags Set containing all the tags
     * @return List of clothing items which match the tags <b>OR</b> all clothing items if no tags are provided
     */
    public List<ClothingDto> getAllClothingFromUserWithTags(BigInteger userId,
                                                         Map<String, String> tags) {
        if (tags == null || tags.isEmpty())
            return getAllClothingFromUser(userId);

        ClothingTags clothingTags = mapToClothingTags(tags);

        Set<Clothing> clothing = new HashSet<>(clothingRepository.findClothingByUserIdAndTags(
                userId,
                clothingTags.getTemperature(),
                clothingTags.getPrecipitationProtection(),
                clothingTags.getWaterResistance(),
                clothingTags.getWindResistance(),
                clothingTags.getBodySection()
        ));

        return clothing.stream().map(this::mapToClothingDto).toList();
    }

    public void addClothing(ClothingDto clothingDto) {
        if ( clothingDto.getId() != null && clothingRepository.findById(clothingDto.getId()).isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Clothing with matching ID already exists");
        clothingDto.setStatus(ClothingStatus.CREATED);
        clothingRepository.save(mapToClothing(clothingDto));
        kafkaTemplate.send(KafkaTopicConfiguration.Topics.CLOTHING, clothingDto);
    }

    public void updateClothing(ClothingDto clothingDto) {
        if ( clothingDto.getId() != null && clothingRepository.findById(clothingDto.getId()).isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothing with matching ID not found");
        clothingDto.setStatus(ClothingStatus.MODIFIED);
        clothingRepository.save(mapToClothing(clothingDto));
        kafkaTemplate.send(KafkaTopicConfiguration.Topics.CLOTHING, clothingDto);
    }

    public void deleteClothing(BigInteger id) {
        Optional<Clothing> clothing = clothingRepository.findById(id);
        if ( clothing.isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothing with matching ID not found");
        clothing.get().setStatus(ClothingStatus.DELETED);
        clothingRepository.deleteById(id);
        kafkaTemplate.send(KafkaTopicConfiguration.Topics.CLOTHING, mapToClothingDto(clothing.get()));
    }

    private ClothingDto mapToClothingDto(Clothing clothing) {
        return ClothingDto.builder()
                .id( clothing.getId() )
                .name( clothing.getName() )
                .type( clothing.getType() )
                .userId( clothing.getUserId() )
                .picture( clothing.getPicture() )
                .usable( clothing.getUsable() )
                .tags( clothing.getTags() )
                .status( clothing.getStatus() )
                .build();
    }

    private Clothing mapToClothing(ClothingDto clothingDto) {
        return Clothing.builder()
                .id( clothingDto.getId() )
                .name( clothingDto.getName() )
                .type( clothingDto.getType() )
                .userId( clothingDto.getUserId() )
                .picture( clothingDto.getPicture() )
                .usable( clothingDto.getUsable() )
                .tags( clothingDto.getTags() )
                .status( clothingDto.getStatus() )
                .build();
    }

    private ClothingTags mapToClothingTags(Map<String, String> rawTags) {
        ClothingTags tags = new ClothingTags();

        for (String key : rawTags.keySet()) {
            try {
                switch (key) {
                    case "temperature" -> tags.setTemperature(ClothingTags.Temperature.valueOf(rawTags.get(key)));
                    case "precipitationProtection" -> tags.setPrecipitationProtection(ClothingTags.PrecipitationProtection.valueOf(rawTags.get(key)));
                    case "waterResistance" -> tags.setWaterResistance(ClothingTags.WaterResistance.valueOf(rawTags.get(key)));
                    case "windResistance" -> tags.setWindResistance(ClothingTags.WindResistance.valueOf(rawTags.get(key)));
                    case "bodySection" -> tags.setBodySection(ClothingTags.BodySection.valueOf(rawTags.get(key)));
                    default -> throw new IllegalArgumentException("Tag not recognized");
                }
            } catch (IllegalArgumentException ignored) {
                // Tag not recognized
            }
        }

        return tags;
    }

    /**
     * Deprecated as this should be done earlier. Kept the code for reference.
     * @param userId ID of the user to be checked
     */
    @Deprecated
    private void verifyUserExistence(BigInteger userId) {
        webClientBuilder // Verify that user exists
                .build()
                .get()
                .uri("http://localhost:8085/user/{userId}", userId) // NOTE: Hard coded URI
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> switch(response.statusCode().value()){
                    case 404 -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
                    default -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"));
                })
                .bodyToMono(String.class)
                .block();
    }
}

;