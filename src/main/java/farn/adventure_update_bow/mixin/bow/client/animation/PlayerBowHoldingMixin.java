package farn.adventure_update_bow.mixin.bow.client.animation;

import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.action.BowAction;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerBowHoldingMixin {

    @Shadow
    private BipedEntityModel bipedModel;

    @Shadow
    private BipedEntityModel armor1;

    @Shadow
    private BipedEntityModel armor2;

    @Inject(method="render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;render(Lnet/minecraft/entity/LivingEntity;DDDFF)V", shift = At.Shift.BEFORE))
    public void renderBowModel(PlayerEntity player, double e, double f, double g, float h, float par6, CallbackInfo ci) {
        boolean bool =
                player.farnutil_isUsingItem() &&
                player.farnutil_getActionType(player.farnutil_getUsingItem())
                        instanceof BowAction;
        this.armor1.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
        this.armor2.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
        this.bipedModel.modelflag_setModelFlag(AdventureUpdateBow.bow_rotate, bool);
    }
}
