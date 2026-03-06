package farn.adventure_update_bow.mixin.bow.client.bow_renderer_fix;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
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
        if(heldStack.itemId == Item.BOW.id) {
            GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
            GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
        }
    }
}
