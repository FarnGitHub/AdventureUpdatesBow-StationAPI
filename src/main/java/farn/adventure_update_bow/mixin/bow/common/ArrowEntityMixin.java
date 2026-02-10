package farn.adventure_update_bow.mixin.bow.common;

import farn.adventure_update_bow.impl.ArrowEntityCustomSpeed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends Entity implements ArrowEntityCustomSpeed {

    @Shadow
    public LivingEntity owner;

    @Shadow
    public boolean pickupAllowed;

    public ArrowEntityMixin(World world) {
        super(world);
    }

    @Shadow
    public abstract void setVelocity(double x, double y, double z, float speed, float divergence);

    public void b18Bow_setVelo(ArrowEntity arrowEntity, LivingEntity entity, float speed) {
        this.owner = entity;
        this.pickupAllowed = owner instanceof PlayerEntity;
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.setPositionAndAnglesKeepPrevAngles(owner.x, owner.y + (double)owner.getEyeHeight(), owner.z, owner.yaw, owner.pitch);
        this.x -= (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F);
        this.y -= 0.10000000149011612;
        this.z -= (double)(MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F);
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0F;
        this.velocityX = (double)(-MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
        this.velocityZ = (double)(MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F));
        this.velocityY = (double)(-MathHelper.sin(this.pitch / 180.0F * 3.1415927F));
        this.setVelocity(this.velocityX, this.velocityY, this.velocityZ, speed * 1.5F, 1.0F);
    }

    @ModifyArg(method="tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"), index = 1)
    public int modifyDamage(int amount) {
        double distance = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
        return (int)Math.ceil(distance * 2.0D);
    }

}
