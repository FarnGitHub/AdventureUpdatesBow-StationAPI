package farn.adventure_update_bow.api;

import farn.adventure_update_bow.impl.vanilla.GenericConsumableArrow;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.StationAPI;

import java.util.Random;

public class ConsumableArrows {
    public static final ObjectArrayList<ConsumableArrow> consumables = new ObjectArrayList<>();

    public static boolean hasArrow(ItemStack stack, PlayerEntity user) {
        for(ConsumableArrow arrow : consumables)
            if(arrow.canConsume(stack, user)) return true;
        return false;
    }

    public static void onUse(ItemStack stack, PlayerEntity user, int duration, Random random) {
        for(ConsumableArrow arrow : consumables)
            if(arrow.onConsume(stack, user, duration, random)) return;
    }

    public static void init(){
        StationAPI.EVENT_BUS.post(new ConsumableArrowEvent());
        //consumables.add(new GenericConsumableArrow());
    }
}
