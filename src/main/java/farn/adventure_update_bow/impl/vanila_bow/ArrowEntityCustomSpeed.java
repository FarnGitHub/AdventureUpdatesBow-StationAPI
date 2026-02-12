package farn.adventure_update_bow.impl.vanila_bow;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;

public interface ArrowEntityCustomSpeed {

    void b18Bow_setVelo(ArrowEntity arrowEntity, LivingEntity entity, float speed);

    void b18bow_setCrit(boolean flag);
}
