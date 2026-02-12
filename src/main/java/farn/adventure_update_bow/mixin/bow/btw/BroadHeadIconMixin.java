package farn.adventure_update_bow.mixin.bow.btw;

import farn.adventure_update_bow.impl.composite_bow.CompositeBowImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PlayerEntity.class, priority = 1100)
public class BroadHeadIconMixin {

    @Inject(method="getItemStackTextureId", at = @At("HEAD"), cancellable = true)
    public void bowWhatEverTextureComposite(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        int icon = CompositeBowImpl.getPullingIcon(stack, (PlayerEntity)(Object)this);
        if(icon > 0) {
            cir.setReturnValue(icon);
        }
    }
}
