package farn.adventure_update_bow.mixin.client.bow_renderer_fix;

import com.llamalad7.mixinextras.sugar.Local;
import farn.adventure_update_bow.AdventureUpdateBow;
import farn.adventure_update_bow.compat.unitweaks.UniTweakCompat;
import farn.adventure_update_bow.impl.action.BowAction;
import net.danygames2014.unitweaks.UniTweaks;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerRendererBowMixin {

    @Inject(method="renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V"
    , at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isHandheldRod()Z", shift = At.Shift.BEFORE))
    public void offsetBow(PlayerEntity plr, float tick, CallbackInfo ci, @Local(type = ItemStack.class, ordinal = 1) ItemStack heldStack) {
        if(plr.farnutil_isUsingItem() && plr.farnutil_getActionType(heldStack) instanceof BowAction) {
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.5F, 1.0F, 0.0F);
        }
    }

    @Inject(
            method = {"renderMore(Lnet/minecraft/entity/player/PlayerEntity;F)V"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.BEFORE,
                    ordinal = 1
            )}
    )
    public void changeBowRendering(PlayerEntity entity, float f, CallbackInfo ci, @Local(type = ItemStack.class, ordinal = 1) ItemStack heldStack) {
        if (!AdventureUpdateBow.uniTweak || !UniTweakCompat.isUniTweakBowFix()) {
            if (entity.farnutil_getActionType(heldStack) instanceof BowAction && !entity.farnutil_isUsingItem()) {
                 GL11.glTranslatef(0.0F, -0.5F, 0.0F);
            }
        } else {
            if (entity.farnutil_getActionType(heldStack) instanceof BowAction && entity.farnutil_isUsingItem()) {
                GL11.glTranslatef(0.0F, 0.5F, 0.0F);
            }
        }

    }
}
