package farn.adventure_update_bow.mixin.common;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import farn.adventure_update_bow.AdventureUpdateBow;
import net.minecraft.entity.mob.SkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(SkeletonEntity.class)
public class SkeletonEntityMixin {

    @Definition(id="yPos", field="Lnet/minecraft/entity/projectile/ArrowEntity;y:D")
    @Expression("?.yPos = ?.yPos + @(1.399999976158142)")
    @ModifyExpressionValue(method="attack", at = @At("MIXINEXTRAS:EXPRESSION"))
    public double aub_cancelYIncrement(double original) {
        return AdventureUpdateBow.isPreciseSkeleton() ? 0.0D : original;
    }

    @ModifyConstant(method="attack", constant = @Constant(doubleValue = 0.20000000298023224))
    public double aub_reduceSpread(double constant) {
        return constant + (AdventureUpdateBow.isPreciseSkeleton() ? 0.5D : 0.0D);
    }

    @ModifyArg(method="attack",
            at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/ArrowEntity;setVelocity(DDDFF)V"),
            index = 3
    )
    public float aub_increaseSpeed(float speed) {
        return speed + (AdventureUpdateBow.isPreciseSkeleton() ? 1.0F : 0.0F);
    }

    @Definition(id="attackCooldown", field="Lnet/minecraft/entity/mob/SkeletonEntity;attackCooldown:I")
    @Expression("this.attackCooldown = @(30)")
    @ModifyExpressionValue(method="attack", at = @At("MIXINEXTRAS:EXPRESSION"))
    public int aub_increaseCoolDown(int cooldown) {
        return AdventureUpdateBow.isPreciseSkeleton() ? cooldown * 2 : cooldown;
    }

    @ModifyConstant(method="attack", constant = @Constant(stringValue = "random.bow"))
    public String aub_attackSound(String constant) {
        return AdventureUpdateBow.isPreciseSkeleton() ? "adventure_update_bow:bow.shoot" : constant;
    }
}
