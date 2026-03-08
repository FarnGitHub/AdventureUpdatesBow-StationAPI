package farn.adventure_update_bow.impl.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.api.ArrowRegistry;
import farn.farn_util.api.item_usage.ActionHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class ItemBowImpl {

    public static ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        if (ArrowRegistry.hasArrow(stack, user)) {
            user.farnutil_setUsingItemMaxDuration(stack, getMaxDuration());
        }
        return stack;
    }

    public static void stopUsingItem(ItemStack stack, World world, PlayerEntity user, int duration, Random random) {
        ArrowRegistry.onUse(stack, user, duration, random);
    }

    public static ActionHandler getActionType() {
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

    public static void init(Item item) {
        if(AdventureUpdateBow.isDurabilityEnabled())
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

    public static ArrowEntity createArrow(World world, LivingEntity shooter, float speed, boolean crit) {
        ArrowEntity arrow = new ArrowEntity(world);
        arrow.owner = shooter;
        arrow.pickupAllowed = shooter instanceof PlayerEntity;
        arrow.setPositionAndAnglesKeepPrevAngles(shooter.x, shooter.y + (double)shooter.getEyeHeight(), shooter.z, shooter.yaw, shooter.pitch);
        arrow.x -= MathHelper.cos(arrow.yaw / 180.0F * 3.1415927F) * 0.16F;
        arrow.y -= 0.10000000149011612;
        arrow.z -= MathHelper.sin(arrow.yaw / 180.0F * 3.1415927F) * 0.16F;
        arrow.setPosition(arrow.x, arrow.y, arrow.z);
        arrow.standingEyeHeight = 0.0F;
        arrow.velocityX = -MathHelper.sin(arrow.yaw / 180.0F * 3.1415927F) * MathHelper.cos(arrow.pitch / 180.0F * 3.1415927F);
        arrow.velocityZ = MathHelper.cos(arrow.yaw / 180.0F * 3.1415927F) * MathHelper.cos(arrow.pitch / 180.0F * 3.1415927F);
        arrow.velocityY = -MathHelper.sin(arrow.pitch / 180.0F * 3.1415927F);
        arrow.setVelocity(arrow.velocityX, arrow.velocityY, arrow.velocityZ, 1.5F * speed, 1.0F);
        arrow.aub_setCrit(crit);
        return arrow;
    }
}
