package farn.adventure_update_bow.compat.bhcreative;

import farn.adventure_update_bow.impl.item.VanillaArrowProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class CreativeArrowProperties extends VanillaArrowProperties {

    public boolean canUse(ItemStack heldStack, PlayerEntity user) {
        return user.creative_isCreative();
    }

    public void consumeItem(ItemStack heldStack, PlayerEntity user, int duration, Random random) {
    }
}
