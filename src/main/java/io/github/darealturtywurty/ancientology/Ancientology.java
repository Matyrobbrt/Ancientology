package io.github.darealturtywurty.ancientology;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import io.github.darealturtywurty.ancientology.core.init.BlockEntityInit;
import io.github.darealturtywurty.ancientology.core.init.BlockInit;
import io.github.darealturtywurty.ancientology.core.init.EntityInit;
import io.github.darealturtywurty.ancientology.core.init.ItemInit;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Ancientology.MODID)
public class Ancientology {

    public static final String MODID = "ancientology";

    public Ancientology() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockInit.BLOCKS.register(bus);
        ItemInit.ITEMS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        EntityInit.ENTITIES.register(bus);
    }

    public static final CreativeModeTab ANCIENTOLOGY_ITEM_TAB = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(),
            MODID) {

        @Override
        public ItemStack makeIcon() {
            // TODO add an icon
            return ItemStack.EMPTY;
        }
    };
}
