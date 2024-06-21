package io.coaton.clothing;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import io.coaton.clothing.ClothingTags.*;

public class ClothingHelper {
    public static Boolean coversTorso(Clothing item) {
        return item.getTags().getBodySection() == ClothingTags.BodySection.TORSO_AND_LEGS
                || item.getTags().getBodySection() == ClothingTags.BodySection.TORSO_INNER
                || item.getTags().getBodySection() == ClothingTags.BodySection.TORSO_OUTER;
    }

    public static List<Clothing> getTorsoClothing(List<Clothing> clothes) {
        return clothes.stream().filter(item -> coversTorso(item)).collect(Collectors.toList());
    }

    public static List<Clothing> getLegsClothing(List<Clothing> clothes) {
        return clothes.stream().filter(item -> item.getTags().getBodySection() == ClothingTags.BodySection.LEGS)
                .collect(Collectors.toList());
    }

    public static List<Clothing> getHeadClothing(List<Clothing> clothes) {
        return clothes.stream().filter(item -> item.getTags().getBodySection() == ClothingTags.BodySection.HEAD)
                .collect(Collectors.toList());
    }

    public static List<Clothing> getFeetClothing(List<Clothing> clothes) {
        return clothes.stream().filter(item -> item.getTags().getBodySection() == ClothingTags.BodySection.FEET)
                .collect(Collectors.toList());
    }

    public static List<Clothing> getHandsClothing(List<Clothing> clothes) {
        return clothes.stream().filter(item -> item.getTags().getBodySection() == ClothingTags.BodySection.HANDS)
                .collect(Collectors.toList());
    }

    private static ClothingTags mapToClothingTags(JSONObject rawTags) {
        ClothingTags tags = new ClothingTags();

        for (String key : rawTags.keySet()) {
            try {
                switch (key) {
                    case "temperature" -> tags.setTemperature(Temperature.valueOf(rawTags.getString(key)));
                    case "precipitationProtection" ->
                        tags.setPrecipitationProtection(PrecipitationProtection.valueOf(rawTags.getString(key)));
                    case "waterResistance" -> tags.setWaterResistance(WaterResistance.valueOf(rawTags.getString(key)));
                    case "windResistance" -> tags.setWindResistance(WindResistance.valueOf(rawTags.getString(key)));
                    case "bodySection" -> tags.setBodySection(BodySection.valueOf(rawTags.getString(key)));
                    default -> throw new IllegalArgumentException("Tag not recognized");
                }
            } catch (IllegalArgumentException ignored) {
                // Tag not recognized
            }
        }

        return tags;
    }

    public static Clothing mapDtoToClothing(JSONObject clothingDto) {
        ClothingTags tags = mapToClothingTags(clothingDto.getJSONObject("tags"));
        return Clothing.builder().id(clothingDto.getBigInteger("id")).tags(tags).name(clothingDto.getString("name")).build();
    }

}
