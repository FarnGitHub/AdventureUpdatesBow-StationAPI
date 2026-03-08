package farn.adventure_update_bow.mixin.common;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.impl.ChargingArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends Entity implements ChargingArrow {

    @Unique
    private boolean crit = false;

    public ArrowEntityMixin(World world) {
        super(world);
    }

    @ModifyArg(method="tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"), index = 1)
    public int modifyDamage(int amount) {
        float distance = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
        int newDamage = (int)Math.ceil(distance * 2.0D);
        if(AdventureUpdateBow.isCritEnabled() && aub_isCrit()) {
            newDamage += this.random.nextInt(newDamage / 2 + 2);
        }
        return newDamage;
    }

    @ModifyConstant(method="tick", constant = @Constant(floatValue = 0.03F))
    public float higherGravity(float constant) {
        return 0.05F;
    }

    public void aub_setCrit(boolean value) {
        crit = value;
    }

    @Definition(id="shake", field = "Lnet/minecraft/entity/projectile/ArrowEntity;shake:I")
    @Expression("this.shake = 7")
    @WrapOperation(method="tick", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void setNotCritical(ArrowEntity instance, int value, Operation<Void> original) {
        original.call(instance, value);
        crit = false;
    }

    public boolean aub_isCrit() {
        return crit;
    }


}
