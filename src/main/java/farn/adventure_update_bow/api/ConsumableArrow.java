package farn.adventure_update_bow.api;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class ConsumableArrow {
    public static final IntArrayList consumables = new IntArrayList();

    public static void add(int id) {
        consumables.add(id);
    }
}
