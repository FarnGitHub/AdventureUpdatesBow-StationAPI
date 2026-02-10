package farn.adventure_update_bow.mixin.bow.client.animation;

import farn.adventure_update_bow.ModernBow;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedModelBowMixin extends EntityModel {

    @Shadow
    public ModelPart rightArm;

    @Shadow
    public ModelPart leftArm;

    @Shadow
    public ModelPart head;

    @Inject(method="setAngles", at = @At("TAIL"))
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci) {
        if(modelflag_getModelFlag(ModernBow.bow_rotate, false)) {
            float f7 = 0.0F;
            float f8 = 0.0F;
            this.rightArm.roll = 0.0F;
            this.leftArm.roll = 0.0F;
            this.rightArm.yaw = -(0.1F - f7 * 0.6F) + this.head.yaw;
            this.leftArm.yaw = 0.1F - f7 * 0.6F + this.head.yaw + 0.4F;
            this.rightArm.pitch = -1.5707964F + this.head.pitch;
            this.leftArm.pitch = -1.5707964F + this.head.pitch;
            this.rightArm.pitch -= f7 * 1.2F - f8 * 0.4F;
            this.leftArm.pitch -= f7 * 1.2F - f8 * 0.4F;
            this.rightArm.roll += MathHelper.cos(animationProgress * 0.09F) * 0.05F + 0.05F;
            this.leftArm.roll -= MathHelper.cos(animationProgress * 0.09F) * 0.05F + 0.05F;
            this.rightArm.pitch += MathHelper.sin(animationProgress * 0.067F) * 0.05F;
            this.leftArm.pitch -= MathHelper.sin(animationProgress * 0.067F) * 0.05F;
        }

    }
}
