package farn.adventure_update_bow.compat.elemental_arrow;

import farn.adventure_update_bow.api.ArrowProperties;
import farn.adventure_update_bow.impl.ChargingArrow;
import farn.adventure_update_bow.impl.item.ItemBowImpl;
import net.danygames2014.elementalarrows.entity.ElementalArrowEntity;
import net.danygames2014.elementalarrows.item.ElementalArrowItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ElementalArrowProperties implements ArrowProperties {

    @Override
    public boolean canUse(ItemStack heldStack, PlayerEntity user) {
        return getElementalArrowItem(user) != null;
    }

    @Override
    public boolean onUse(ItemStack heldStack, PlayerEntity user, int duration, Random random) {
        ElementalArrowItem arrow = getElementalArrowItem(user);
        if(arrow != null) {
            float speed = (ItemBowImpl.getMaxDuration() - duration) / 20.0F;
            speed = (speed * speed + speed * 2.0F) / 3.0F;
            speed = Math.max(Math.min(speed, 1.0F), 0.1F);
            heldStack.damage(1, user);
            user.world.playSound(user, "adventure_update_bow:bow.shoot",
                    1.0F,
                    1.0F / (random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
            user.inventory.remove(arrow.id);
            if (!user.world.isRemote) {
                user.world.spawnEntity(createArrow(
                        arrow, user,
                        speed * 2,
                        speed >= 1.0F));
            }
            return true;
        }
        return false;
    }

    private ElementalArrowItem getElementalArrowItem(PlayerEntity user) {
        for(ItemStack invStack : user.inventory.main)
            if(invStack != null && invStack.getItem() instanceof ElementalArrowItem arrow)
                return arrow;
        return null;
    }

    public static ElementalArrowEntity createArrow(ElementalArrowItem item, LivingEntity shooter, float speed, boolean crit) {
        ElementalArrowEntity arrow = (ElementalArrowEntity) item.getArrowEntity(shooter.world);
        if(arrow != null) {
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
            ((ChargingArrow)arrow).aub_setCrit(crit);
        }
        return arrow;
    }
}
