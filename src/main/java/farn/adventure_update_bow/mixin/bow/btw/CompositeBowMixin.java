package farn.adventure_update_bow.mixin.bow.btw;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.impl.composite_bow.CompositeBowImpl;
import farn.adventure_update_bow.impl.vanila_bow.ItemBowImpl;
import farn.farn_util.api.item_usage.ActionType;
import net.kozibrodka.wolves.items.CompositeBowItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CompositeBowItem.class, priority = 1100)
public abstract class CompositeBowMixin extends TemplateItem {

    public CompositeBowMixin(Identifier identifier) {
        super(identifier);
    }

    @WrapMethod(method="use")
    public ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        return ItemBowImpl.use(stack, world, user, original);
    }

    @Inject(method="<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci){
        CompositeBowImpl.setDurability(this);
    }

    @Override
    public void farnutil_stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration) {
        super.farnutil_stopUsingItem(stack, world, player, duration);
        CompositeBowImpl.stopUsingItem(stack, world, player, duration, random);
    }

    @Override
    public ActionType farnutil_getActionType() {
        return CompositeBowImpl.action;
    }

    @Override
    public int farnutil_getMaxDuration(ItemStack itemStack1) {
        return ItemBowImpl.getMaxDuration();
    }

    @Override
    public float farnutil_getFovMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return ItemBowImpl.getFovMultiplier(duration);
    }

    public float farnutil_getSpeedMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return ItemBowImpl.getSpeedMultiplier();
    }
}
