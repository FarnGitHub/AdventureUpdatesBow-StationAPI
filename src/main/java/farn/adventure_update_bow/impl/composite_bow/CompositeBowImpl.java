package farn.adventure_update_bow.impl.composite_bow;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.impl.vanila_bow.ArrowEntityCustomSpeed;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kozibrodka.wolves.entity.BroadheadArrowEntity;
import net.kozibrodka.wolves.events.ItemListener;
import net.kozibrodka.wolves.items.CompositeBowItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.util.math.MathHelper;

import java.util.Random;

public class CompositeBowImpl {

    public static void setDurability(Item item) {
         item.setMaxDamage(768);
     }

    public static void stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration, Random random) {
        if(player.inventory.indexOf(ItemListener.broadHeadArrow.id) > -1) {
            float speedMulti = (ItemListener.compositeBow.farnutil_getMaxDuration(stack) - duration) / 20.0F;
            speedMulti = (speedMulti * speedMulti + speedMulti * 2.0F) / 3.0F;
            speedMulti = MathHelper.clamp(speedMulti, 0.1F, 1.0F);
            BroadheadArrowEntity entityArrow7 = new BroadheadArrowEntity(world);
            BroadheadArrowEntityCustomSpeed customInvoke = ((BroadheadArrowEntityCustomSpeed)entityArrow7);
            customInvoke.b18bow_setCrit(speedMulti >= 1.0F);
            customInvoke.b18Bow_setVelo(entityArrow7, player, speedMulti * 2.0F);
            stack.damage(1, player);
            world.playSound(player, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + speedMulti * 0.5F);
            player.inventory.remove(Item.ARROW.id);
            if (!world.isRemote) {
                world.spawnEntity(entityArrow7);
            }
        } else if(player.inventory.indexOf(Item.ARROW.id) > -1) {
            float speedMulti = (ItemListener.compositeBow.farnutil_getMaxDuration(stack) - duration) / 20.0F;
            speedMulti = (speedMulti * speedMulti + speedMulti * 2.0F) / 3.0F;
            speedMulti = MathHelper.clamp(speedMulti, 0.1F, 1.0F);
            ArrowEntity entityArrow7 = new ArrowEntity(world);
            ArrowEntityCustomSpeed customInvoke = ((ArrowEntityCustomSpeed)entityArrow7);
            customInvoke.b18bow_setCrit(speedMulti >= 1.0F);
            customInvoke.b18Bow_setVelo(entityArrow7, player, speedMulti * 3.0F);
            stack.damage(1, player);
            world.playSound(player, "random.bow", 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + speedMulti * 0.5F);
            player.inventory.remove(Item.ARROW.id);
            if (!world.isRemote) {
                world.spawnEntity(entityArrow7);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static int getPullingIcon(ItemStack stack, PlayerEntity player) {
        if(player.farnutil_isUsingItem() && stack.getItem() instanceof CompositeBowItem) {
            StationAPI.LOGGER.info("TEST");
            int durationEs = ItemListener.compositeBow.farnutil_getMaxDuration(stack) - player.farnutil_getUsingDuration();
            return durationEs >= 18 ? AdventureUpdateBow.bowCompositePulling[2] :
                    (durationEs > 13 ? AdventureUpdateBow.bowCompositePulling[1] : AdventureUpdateBow.bowCompositePulling[0]);
        }
        return -1;
    }

    public static ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        if(AdventureUpdateBow.isEnabled()) {
            if (user.inventory.indexOf(Item.ARROW.id) > -1 || user.inventory.indexOf(ItemListener.broadHeadArrow.id) > -1) {
                user.farnutil_setUsingItemMaxDuration(stack, ItemListener.compositeBow.farnutil_getMaxDuration(stack));
            }
            return stack;
        } else {
            return original.call(stack, world, user);
        }
    }
}
