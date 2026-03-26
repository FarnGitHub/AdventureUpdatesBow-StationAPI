package farn.adventure_update_bow.mixin.common;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import farn.adventure_update_bow.AdventureUpdateBow;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SkeletonEntity.class)
public class SkeletonEntityMixin {

    @Definition(id="yPos", field="Lnet/minecraft/entity/projectile/ArrowEntity;y:D")
    @Expression("?.yPos = ?.yPos + 1.399999976158142")
    @WrapWithCondition(method="attack", at = @At("MIXINEXTRAS:EXPRESSION"))
    public boolean cancelYIncrement(ArrowEntity instance, double value) {
        return AdventureUpdateBow.isPreciseSkeleton();
    }

    @ModifyConstant(method="attack", constant = @Constant(floatValue = 0.2F, ordinal = 0))
    public float increaseEyeHeight(float constant) {
        return constant + (AdventureUpdateBow.isPreciseSkeleton() ? 0.5F : 0.0F);
    }

    @ModifyArg(method="attack",
            at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/ArrowEntity;setVelocity(DDDFF)V"),
            index = 3
    )
    public float increaseSpeed(float speed) {
        return speed + (AdventureUpdateBow.isPreciseSkeleton() ? 1.0F : 0.0F);
    }

    @Definition(id="attackCooldown", field="Lnet/minecraft/entity/mob/SkeletonEntity;attackCooldown:I")
    @Expression("this.attackCooldown = @(30)")
    @ModifyExpressionValue(method="attack", at = @At("MIXINEXTRAS:EXPRESSION"))
    public int getCooldown(int cooldown) {
        return AdventureUpdateBow.isPreciseSkeleton() ? cooldown * 2 : cooldown;
    }
}
