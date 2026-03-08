package farn.adventure_update_bow.mixin.common.elemental_arrow;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import farn.adventure_update_bow.impl.vanilla.ArrowEntityAUB;
import net.danygames2014.elementalarrows.entity.ElementalArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Pseudo
@Mixin(ElementalArrowEntity.class)
public abstract class ElementalArrowEntityMixin extends Entity implements ArrowEntityAUB {
    private boolean crit = false;

    public ElementalArrowEntityMixin(World world) {
        super(world);
    }

    public void aub_setCrit(boolean value) {
        crit = value;
    }

    public boolean aub_isCrit() {
        return crit;
    }

    @ModifyConstant(method="tick", constant = @Constant(floatValue = 0.03F))
    public float higherGravity(float constant) {
        return 0.05F;
    }

    @Definition(id="shake", field = "Lnet/danygames2014/elementalarrows/entity/ElementalArrowEntity;shake:I")
    @Expression("this.shake = 7")
    @WrapOperation(method="tick", at = @At("MIXINEXTRAS:EXPRESSION"))
    public void setNotCritical(ElementalArrowEntity instance, int value, Operation<Void> original) {
        original.call(instance, value);
        crit = false;
    }
}
