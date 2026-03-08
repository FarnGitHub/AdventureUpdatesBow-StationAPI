package farn.adventure_update_bow.impl.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class AUBGlassConfig {

    @ConfigRoot(value = "adventure_update_bow", visibleName = "Adventure Update's Bow Config")
    public static final InstanceConfig instance = new InstanceConfig();

    public static class InstanceConfig {

        @ConfigEntry(name = "Durability", description = "Add durability to the bow", multiplayerSynced = true, requiresRestart = true)
        public Boolean durability = true;

        @ConfigEntry(name = "Crit Damage", description = "Allowed for higher randomized damage when fully charged", multiplayerSynced = true)
        public Boolean crit = false;

    }
}
