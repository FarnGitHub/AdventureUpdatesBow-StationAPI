package farn.adventure_update_bow.mixin.bow.client;

import farn.adventure_update_bow.impl.ItemBowImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerArrowIconMixin {

    @Inject(method="getItemStackTextureId", at = @At("HEAD"), cancellable = true)
    public void bowWhatEverTexture(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int icon = ItemBowImpl.getPullingIcon(stack, (PlayerEntity)(Object)this);
        if(icon > 0) {
            cir.setReturnValue(icon);
        }
    }
}
