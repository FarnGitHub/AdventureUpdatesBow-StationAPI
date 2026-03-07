package farn.adventure_update_bow.impl.aether;

import com.matthewperiut.aether.entity.projectile.EntityFlamingArrow;
import com.matthewperiut.aether.item.AetherItems;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.impl.vanila_bow.ArrowEntityAUB;
import farn.adventure_update_bow.impl.vanila_bow.ItemBowImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class PhoenixBowImpl {

    public static void stopUsingItem(ItemStack stack, World world, PlayerEntity user, int duration, Random random) {
        if(ItemBowImpl.hasArrow(user)) {
            float speed = (ItemBowImpl.getMaxDuration() - duration) / 20.0F;
            speed = (speed * speed + speed * 2.0F) / 3.0F;
            speed = Math.max(Math.min(speed, 1.0F), 0.1F);
            stack.damage(1, user);
            world.playSound(user, "mob.ghast.fireball",
                    1.0F,
                    1.0F / (random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
            ItemBowImpl.removeArrow(user);
            if (!world.isRemote) {
                world.spawnEntity(createArrow(
                        world, user,
                        speed * 2,
                        speed >= 1.0F));
            }
        }
    }

    public static EntityFlamingArrow createArrow(World world, LivingEntity shooter, float speed, boolean crit) {
        EntityFlamingArrow arrow = new EntityFlamingArrow(world);
        arrow.owner = shooter;
        arrow.doesArrowBelongToPlayer = shooter instanceof PlayerEntity;
        arrow.setPositionAndAnglesKeepPrevAngles(shooter.x, shooter.y + (double)shooter.getEyeHeight(), shooter.z, shooter.yaw, shooter.pitch);
        arrow.x -= MathHelper.cos(arrow.yaw / 180.0F * 3.1415927F) * 0.16F;
        arrow.y -= 0.10000000149011612;
        arrow.z -= MathHelper.sin(arrow.yaw / 180.0F * 3.1415927F) * 0.16F;
        arrow.setPosition(arrow.x, arrow.y, arrow.z);
        arrow.standingEyeHeight = 0.0F;
        arrow.velocityX = -MathHelper.sin(arrow.yaw / 180.0F * 3.1415927F) * MathHelper.cos(arrow.pitch / 180.0F * 3.1415927F);
        arrow.velocityZ = MathHelper.cos(arrow.yaw / 180.0F * 3.1415927F) * MathHelper.cos(arrow.pitch / 180.0F * 3.1415927F);
        arrow.velocityY = -MathHelper.sin(arrow.pitch / 180.0F * 3.1415927F);
        arrow.setArrowHeading(arrow.velocityX, arrow.velocityY, arrow.velocityZ, 1.5F * speed, 1.0F);
        ((ArrowEntityAUB)arrow).aub_setCrit(crit);
        return arrow;
    }

    @Environment(EnvType.CLIENT)
    public static int getPullingIcon(ItemStack itemStack, PlayerEntity player) {
        if(player.farnutil_isUsingItem() && itemStack.itemId == AetherItems.PhoenixBow.id) {
            int durationEs = AetherItems.PhoenixBow.farnutil_getMaxDuration(itemStack) - player.farnutil_getUsingDuration();
            return durationEs >= 18 ? AdventureUpdateBow.phoenixBowPulling[2] :
                    (durationEs > 13 ? AdventureUpdateBow.phoenixBowPulling[1] : AdventureUpdateBow.phoenixBowPulling[0]);
        }
        return -1;
    }
}
