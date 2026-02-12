package farn.adventure_update_bow.impl.composite_bow;

import net.kozibrodka.wolves.entity.BroadheadArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;

public interface BroadheadArrowEntityCustomSpeed {

    void b18Bow_setVelo(BroadheadArrowEntity arrowEntity, LivingEntity entity, float speed);

    void b18bow_setCrit(boolean flag);
}
