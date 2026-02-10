package farn.adventure_update_bow.impl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.ArrowEntityCustomSpeed;
import farn.adventure_update_bow.ModernBow;
import farn.farn_util.api.item_usage.ActionType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

public class ItemBowImpl {

    public static ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        if (user.inventory.indexOf(Item.ARROW.id) > -1) {
            user.farnutil_setUsingItemMaxDuration(stack, getMaxDuration(stack));
        }
        return stack;
    }

    public static void stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration, Random random) {
        if(player.inventory.indexOf(Item.ARROW.id) > -1) {
            int durationI = getMaxDuration(stack) - duration;
            float speedMulti = (float) durationI / 20.0F;
            speedMulti = (speedMulti * speedMulti + speedMulti * 2.0F) / 3.0F;

            if (speedMulti > 1.5F) {
                speedMulti = 1.5F;
            } else if(speedMulti < 0.2D) {
                speedMulti = 0.2F;
            }

            ArrowEntity entityArrow7 = new ArrowEntity(world);
            ((ArrowEntityCustomSpeed)entityArrow7).b18Bow_setVelo(entityArrow7, player, speedMulti);
            stack.damage(1, player);
            world.playSound(player, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + speedMulti * 0.5F);
            player.inventory.remove(Item.ARROW.id);
            if (!world.isRemote) {
                world.spawnEntity(entityArrow7);
            }
        }
    }

    public static ActionType getActionType() {
        return ModernBow.bowAction;
    }

    public static int getMaxDuration(ItemStack itemStack1) {
        return 72000;
    }

    public static float getFovMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        float baseMt = 1.0F;
        float duationX = (float)(getMaxDuration(stack) - duration) / 20.0F;
        if(duationX > 1.0F) {
            duationX = 1.0F;
        } else {
            duationX *= duationX;
        }
        baseMt *= 1.0F - duationX * 0.15F;
        return baseMt;
    }

    public static float getSpeedMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return 0.2F;
    }

    public static void setDurability(Item item) {
        item.setMaxDamage(384);
    }

    @Environment(EnvType.CLIENT)
    public static int getPullingIcon(ItemStack itemStack, PlayerEntity player) {
        if(player.farnutil_isUsingItem() && itemStack.getItem() instanceof BowItem itemBow) {
            int durationEs = itemBow.farnutil_getMaxDuration(itemStack) - player.farnutil_getUsingDuration();
            return durationEs >= 18 ? ModernBow.bowPulling[2] :
                    (durationEs > 13 ? ModernBow.bowPulling[1] : ModernBow.bowPulling[0]);
        }
        return -1;
    }
}
