package farn.adventure_update_bow.mixin.common;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import farn.adventure_update_bow.impl.item.ItemBowImpl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BowItem.class, priority = 2000)
public abstract class ItemBowMixin extends Item{

    public ItemBowMixin(int id) {
        super(id);
    }

    @Inject(method="<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci){
         ItemBowImpl.init(this);
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
    public String farnutil_getActionId() {
        return ItemBowImpl.getActionId();
    }

    @Override
    public int farnutil_getMaxDuration(ItemStack stack) {
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
