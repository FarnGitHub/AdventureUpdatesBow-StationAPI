package farn.adventure_update_bow.mixin.bow.client.bow_renderer_fix;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BowItem.class, priority = 1200)
public abstract class ItemBowHandHeldMixin extends Item {

    public ItemBowHandHeldMixin(int id) {
        super(id);
    }

    public boolean isHandheld() {
        return true;
    }
}
