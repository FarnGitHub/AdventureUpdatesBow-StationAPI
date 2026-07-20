package farn.adventure_update_bow.api;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ArrowRegistry {
    private static final ObjectArrayList<ArrowProperties> arrows = new ObjectArrayList<>();

    public static boolean hasArrow(ItemStack stack, PlayerEntity user) {
        for(ArrowProperties arrow : arrows) {
            if(arrow.canUse(stack, user)) return true;
        }
        return false;
    }

    public static void onUse(ItemStack stack, PlayerEntity user, int duration, Random random) {
        for(ArrowProperties arrow : arrows)
            if(arrow.onUse(stack, user, duration, random)) return;
    }

    public static void register(ArrowProperties arrow) {
        arrows.add(arrow);
    }
}
