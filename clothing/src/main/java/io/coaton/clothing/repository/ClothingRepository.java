package io.coaton.clothing.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import io.coaton.clothing.model.Clothing;
import io.coaton.clothing.model.ClothingTags;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClothingRepository extends CrudRepository<Clothing, BigInteger> {
    public List<Clothing> findByUserId(BigInteger userId);
    public Optional<Clothing> findByUserIdAndId(BigInteger userId, BigInteger clothingId);

    @Query("SELECT c FROM Clothing c WHERE " +
            "c.userId = ?1 " +
            "AND ( " +
            "c.tags.temperature = ?2 " +
            "OR c.tags.precipitationProtection = ?3 " +
            "OR c.tags.waterResistance = ?4 " +
            "OR c.tags.windResistance = ?5 " +
            "OR c.tags.bodySection = ?6 " +
            ") ")
    public List<Clothing> findClothingByUserIdAndTags(BigInteger userId,
                                                      ClothingTags.Temperature temp,
                                                      ClothingTags.PrecipitationProtection pp,
                                                      ClothingTags.WaterResistance water,
                                                      ClothingTags.WindResistance wind,
                                                      ClothingTags.BodySection section);

    public void deleteClothingByUserId(BigInteger userId);
}
