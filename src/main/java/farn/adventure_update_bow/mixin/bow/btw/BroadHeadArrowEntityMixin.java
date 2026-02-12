package farn.adventure_update_bow.mixin.bow.btw;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.impl.composite_bow.BroadheadArrowEntityCustomSpeed;
import farn.adventure_update_bow.impl.vanila_bow.ArrowEntityCustomSpeed;
import net.kozibrodka.wolves.entity.BroadheadArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BroadheadArrowEntity.class)
public abstract class BroadHeadArrowEntityMixin extends Entity implements BroadheadArrowEntityCustomSpeed {

    @Shadow
    public LivingEntity owner;

    @Shadow
    public boolean spawnedByPlayer = false;

    @Unique
    private boolean crit = false;

    public BroadHeadArrowEntityMixin(World world) {
        super(world);
    }

    @Shadow
    public abstract void method_1291(double x, double y, double z, float speed, float divergence);

    @Override
    public void b18Bow_setVelo(BroadheadArrowEntity arrowEntity, LivingEntity entity, float speed) {
        this.owner = entity;
        this.spawnedByPlayer = owner instanceof PlayerEntity;
        this.setBoundingBoxSpacing(0.5F, 0.5F);
        this.setPositionAndAnglesKeepPrevAngles(owner.x, owner.y + (double)owner.getEyeHeight(), owner.z, owner.yaw, owner.pitch);
        this.x -= MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * 0.16F;
        this.y -= 0.10000000149011612;
        this.z -= MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * 0.16F;
        this.setPosition(this.x, this.y, this.z);
        this.standingEyeHeight = 0.0F;
        this.velocityX = -MathHelper.sin(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F);
        this.velocityZ = MathHelper.cos(this.yaw / 180.0F * 3.1415927F) * MathHelper.cos(this.pitch / 180.0F * 3.1415927F);
        this.velocityY = -MathHelper.sin(this.pitch / 180.0F * 3.1415927F);
        this.method_1291(this.velocityX, this.velocityY, this.velocityZ, speed * 1.5F, 1.0F);
    }

    @ModifyArg(method="tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"), index = 1)
    public int modifyDamage(int amount) {
        if(AdventureUpdateBow.isEnabled()) {
            float distance = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
            int newDamage = (int)Math.ceil(distance * 4.0D);
            if(AdventureUpdateBow.isCritEnabled() && crit) {
                newDamage += this.random.nextInt(newDamage / 2 + 2);
            }
            return newDamage;
        }
        return amount;
    }

    @ModifyConstant(method="tick", constant = @Constant(floatValue = 0.03F))
    public float higherGravity(float constant) {
        return AdventureUpdateBow.isEnabled() ? 0.05F : constant;
    }

    public void b18bow_setCrit(boolean flag) {
        crit = flag;
    }

    @Definition(id="shake", field = "Lnet/kozibrodka/wolves/entity/BroadheadArrowEntity;shake:I")
    @Expression("this.shake = 7")
    @WrapOperation(method="tick", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void setNotCritical(BroadheadArrowEntity instance, int value, Operation<Void> original) {
        original.call(instance, value);
        crit = false;
    }


}
