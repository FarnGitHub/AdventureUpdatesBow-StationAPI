package farn.adventure_update_bow.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public interface ConsumableArrow {

    boolean canConsume(ItemStack stack, PlayerEntity user);

    boolean onConsume(ItemStack stack, PlayerEntity user, int duration, Random random);

}
