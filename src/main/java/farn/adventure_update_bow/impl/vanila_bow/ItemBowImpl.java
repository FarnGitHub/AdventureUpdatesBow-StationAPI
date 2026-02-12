package farn.adventure_update_bow.impl.vanila_bow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.farn_util.api.item_usage.ActionType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.MathHelper;

import java.util.Random;

public class ItemBowImpl {

    public static ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        if(AdventureUpdateBow.isEnabled()) {
            if (user.inventory.indexOf(Item.ARROW.id) > -1) {
                user.farnutil_setUsingItemMaxDuration(stack, getMaxDuration());
            }
            return stack;
        } else {
            return original.call(stack, world, user);
        }
    }

    public static void stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration, Random random) {
        if(player.inventory.indexOf(Item.ARROW.id) > -1) {
            float speedMulti = (getMaxDuration() - duration) / 20.0F;
            speedMulti = (speedMulti * speedMulti + speedMulti * 2.0F) / 3.0F;
            speedMulti = MathHelper.clamp(speedMulti, 0.1F, 1.0F);
            ArrowEntity entityArrow7 = new ArrowEntity(world);
            ArrowEntityCustomSpeed customInvoke = ((ArrowEntityCustomSpeed)entityArrow7);
            customInvoke.b18bow_setCrit(speedMulti >= 1.0F);
            customInvoke.b18Bow_setVelo(entityArrow7, player, speedMulti * 2.0F);
            stack.damage(1, player);
            world.playSound(player, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + speedMulti * 0.5F);
            player.inventory.remove(Item.ARROW.id);
            if (!world.isRemote) {
                world.spawnEntity(entityArrow7);
            }
        }
    }

    public static ActionType getActionType() {
        return AdventureUpdateBow.bowAction;
    }

    public static int getMaxDuration() {
        return 72000;
    }

    public static float getFovMultiplier(int duration) {
        float baseMt = 1.0F;
        float duationX = (float)(getMaxDuration() - duration) / 20.0F;
        if(duationX > 1.0F) {
            duationX = 1.0F;
        } else {
            duationX *= duationX;
        }
        baseMt *= 1.0F - duationX * 0.15F;
        return baseMt;
    }

    public static float getSpeedMultiplier() {
        return 0.2F;
    }

    public static void setDurability(Item item) {
        item.setMaxDamage(384);
    }

    @Environment(EnvType.CLIENT)
    public static int getPullingIcon(ItemStack itemStack, PlayerEntity player) {
        if(player.farnutil_isUsingItem() && itemStack.itemId == Item.BOW.id) {
            int durationEs = Item.BOW.farnutil_getMaxDuration(itemStack) - player.farnutil_getUsingDuration();
            return durationEs >= 18 ? AdventureUpdateBow.bowPulling[2] :
                    (durationEs > 13 ? AdventureUpdateBow.bowPulling[1] : AdventureUpdateBow.bowPulling[0]);
        }
        return -1;
    }
}
