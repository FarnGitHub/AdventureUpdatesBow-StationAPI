package farn.adventure_update_bow.mixin.bow.client.vanilla.bow_renderer_fix;

import net.minecraft.item.BowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BowItem.class)
public class ItemBowHandHeldMixin extends ItemSubMixin {

    public void overridden_handheld(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

}
