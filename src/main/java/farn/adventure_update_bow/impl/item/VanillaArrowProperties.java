package farn.adventure_update_bow.impl.item;

import farn.adventure_update_bow.api.ArrowProperties;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class VanillaArrowProperties implements ArrowProperties {

    @Override
    public boolean canUse(ItemStack heldStack, PlayerEntity user) {
        return user.inventory.indexOf(Item.ARROW.id) >= 0;
    }

    @Override
    public boolean onUse(ItemStack heldStack, PlayerEntity user, int duration, Random random) {
        if(user.inventory.indexOf(Item.ARROW.id) >= 0){
            float speed = (ItemBowImpl.getMaxDuration() - duration) / 20.0F;
            speed = (speed * speed + speed * 2.0F) / 3.0F;
            speed = Math.max(Math.min(speed, 1.0F), 0.1F);
            heldStack.damage(1, user);
            user.world.playSound(user, "adventure_update_bow:bow.shoot",
                    1.0F,
                    1.0F / (random.nextFloat() * 0.4F + 1.2F) + speed * 0.5F);
            user.inventory.remove(Item.ARROW.id);
            if (!user.world.isRemote) {
                user.world.spawnEntity(ItemBowImpl.createArrow(
                        user.world, user,
                        speed * 2,
                        speed >= 1.0F));
            }
            return true;
        }
        return false;
    }
}
