package farn.adventure_update_bow.mixin.common.elemental_arrow;

import farn.adventure_update_bow.AdventureUpdateBow;
import net.danygames2014.elementalarrows.entity.IceArrowEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(IceArrowEntity.class)
public abstract class IceArrowEntityMixin extends ElementalArrowEntityMixin {
    public IceArrowEntityMixin(World world) {
        super(world);
    }

    @ModifyArg(method="hitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/Entity;I)Z"), index = 1)
    public int modifyDamage(int amount) {
        float distance = MathHelper.sqrt(this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ);
        int newDamage = (int)Math.ceil(distance * 1.5D);
        if(AdventureUpdateBow.isCritEnabled() && aub_isCrit()) {
            newDamage += this.random.nextInt(newDamage / 3 + 2);
        }
        return newDamage;
    }
}
