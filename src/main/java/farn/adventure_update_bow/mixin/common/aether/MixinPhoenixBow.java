package farn.adventure_update_bow.mixin.common.aether;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.matthewperiut.aether.item.tool.ItemPhoenixBow;
import farn.adventure_update_bow.impl.aether.PhoenixBowImpl;
import farn.adventure_update_bow.impl.vanilla.ItemBowImpl;
import farn.farn_util.api.item_usage.ActionHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(ItemPhoenixBow.class)
public abstract class MixinPhoenixBow  extends TemplateItem {

    public MixinPhoenixBow(Identifier identifier) {
        super(identifier);
    }

    @Inject(method="<init>", at = @At("TAIL"))
    private void init(CallbackInfo ci){
        ItemBowImpl.init(this);
    }

    @WrapMethod(method="use")
    public ItemStack use(ItemStack stack, World world, PlayerEntity user, Operation<ItemStack> original) {
        return PhoenixBowImpl.use(stack, world, user, original);
    }

    @Override
    public void farnutil_stopUsingItem(ItemStack stack, World world, PlayerEntity player, int duration) {
        super.farnutil_stopUsingItem(stack, world, player, duration);
        PhoenixBowImpl.stopUsingItem(stack, world, player, duration, random);
    }

    @Override
    public ActionHandler farnutil_getActionType() {
        return ItemBowImpl.getActionType();
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
