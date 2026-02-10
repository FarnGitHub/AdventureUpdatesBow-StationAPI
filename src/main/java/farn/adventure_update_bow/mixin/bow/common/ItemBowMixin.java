package farn.adventure_update_bow.mixin.bow.common;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.impl.ItemBowImpl;
import farn.farn_util.api.item_usage.ActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BowItem.class, priority = 900)
public abstract class ItemBowMixin extends Item{

    public ItemBowMixin(int id) {
        super(id);
    }

    @Inject(method="<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci){
        ItemBowImpl.setDurability(this);
    }

    @WrapMethod(method="use")
    public ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        return ItemBowImpl.use(stack, world, user, original);
    }

    @Override
    public void farnutil_stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration) {
        super.farnutil_stopUsingItem(stack, world, player, duration);
        ItemBowImpl.stopUsingItem(stack, world, player, duration, random);
    }

    @Override
    public ActionType farnutil_getActionType() {
        return ItemBowImpl.getActionType();
    }

    @Override
    public int farnutil_getMaxDuration(ItemStack itemStack1) {
        return ItemBowImpl.getMaxDuration(itemStack1);
    }

    @Override
    public float farnutil_getFovMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return ItemBowImpl.getFovMultiplier(entity, stack, duration);
    }

    public float farnutil_getSpeedMultiplier(PlayerEntity entity, ItemStack stack, int duration) {
        return ItemBowImpl.getSpeedMultiplier(entity, stack, duration);
    }
}
