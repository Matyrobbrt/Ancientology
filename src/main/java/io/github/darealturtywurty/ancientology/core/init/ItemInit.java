package io.github.darealturtywurty.ancientology.core.init;

import io.github.darealturtywurty.ancientology.Ancientology;
import io.github.darealturtywurty.ancientology.common.items.excalibur.ExcaliburItem;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemDeferredRegister;
import io.github.darealturtywurty.ancientology.core.util.registry.ItemRegistryObject;

public final class ItemInit {

    public static final ItemDeferredRegister ITEMS = ItemDeferredRegister.create(Ancientology.MODID)
            .setDefaultItemTab(Ancientology.ANCIENTOLOGY_ITEM_TAB);

    public static final ItemRegistryObject<ExcaliburItem> EXCALIBUR = ITEMS
            .register("excalibur", p -> new ExcaliburItem(0, 0, p)).defaultDurability(2048).fireResistant()
            .setNoRepair().build();

    private ItemInit() {
        throw new IllegalAccessError("Illegal access to hidden initialization class!");
    }
}
