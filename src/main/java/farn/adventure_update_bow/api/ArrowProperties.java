package farn.adventure_update_bow.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public interface ArrowProperties {

    boolean canUse(ItemStack heldStack, PlayerEntity user);

    boolean onUse(ItemStack heldStack, PlayerEntity user, int duration, Random random);

}
