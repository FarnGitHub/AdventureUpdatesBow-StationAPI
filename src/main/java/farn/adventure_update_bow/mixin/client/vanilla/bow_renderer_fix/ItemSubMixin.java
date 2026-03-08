package farn.adventure_update_bow.mixin.client.vanilla.bow_renderer_fix;

import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemSubMixin {

    @Inject(method="isHandheld", at = @At("HEAD"))
    public void overridden_handheld(CallbackInfoReturnable<Boolean> cir) {
    }
}
