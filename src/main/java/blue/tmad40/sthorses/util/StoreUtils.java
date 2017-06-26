package blue.tmad40.sthorses.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreUtils {
    public static List<String> createAbstractHorseLore(AbstractHorse abstractHorse) {
        List<String> lore = new ArrayList<>();

        String name = "Name: ";
        String owner = "Owner: ";
        String ownerUuid = "Owner UUID: ";

        if (abstractHorse.getCustomName() != null) {
            name = name.concat(abstractHorse.getCustomName());
        } else {
            name = name.concat("None");
        }

        if (abstractHorse.getOwner() != null) {
            owner = owner.concat(String.valueOf(abstractHorse.getOwner().getName()));
            ownerUuid = ownerUuid.concat(String.valueOf(abstractHorse.getOwner().getUniqueId()));
        } else {
            owner = owner.concat("None");
            ownerUuid = ownerUuid.concat("None");
        }

        double jumpValue = abstractHorse.getAttribute(Attribute.HORSE_JUMP_STRENGTH).getValue();

        // Formula from http://minecraft.gamepedia.com/Horse#Statistics
        double jumpValueInBlocks = -0.1817584952 * Math.pow(jumpValue, 3) + 3.689713992 * Math.pow(jumpValue, 2) + 2.128599134 * jumpValue - 0.343930367;
        jumpValueInBlocks = (double) Math.round(jumpValueInBlocks * 1d) / 1d;

        double speedValue = abstractHorse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue();
        double speedValueInBlocks = 43.178 * speedValue - 0.0214;
        speedValueInBlocks = (double) Math.round(speedValueInBlocks * 1d) / 1d;

        String variant = "Variant: " + abstractHorse.getClass().getSimpleName().replace("Craft", "");
        String jump = "Jump: ~"  + String.valueOf(jumpValueInBlocks) + " blocks";
        String speed = "Speed: ~" + String.valueOf(speedValueInBlocks) + " blocks";
        String health = "Health: " + String.valueOf(abstractHorse.getHealth()) + "/" + String.valueOf(abstractHorse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        String domestication = "Domestication: " + String.valueOf(abstractHorse.getDomestication() + "/" + String.valueOf(abstractHorse.getMaxDomestication()));
        String age = "Age: " + String.valueOf(abstractHorse.getAge());
        String realJump = "Real Jump: " + String.valueOf(jumpValue);
        String realSpeed = "Real Speed: " + String.valueOf(speedValue);

        lore.addAll(Arrays.asList(
                name, owner, variant, jump, speed, health, domestication, age, ownerUuid, realJump, realSpeed
        ));

        return lore;
    }

    public static List<String> createHorseLore(Horse horse) {
        List<String> lore = new ArrayList<>();
        lore.add("Color: " + horse.getColor().toString());
        lore.add("Style: " + horse.getStyle().toString());
        return lore;
    }

    public static List<String> createLlamaLore(Llama llama) {
        List<String> lore = new ArrayList<>();
        lore.add("Color: " + llama.getColor().toString());
        return lore;
    }
}
