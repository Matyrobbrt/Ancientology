package io.github.darealturtywurty.ancientology.common.items.excalibur;

import java.util.function.Supplier;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import io.github.darealturtywurty.ancientology.Ancientology;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class ExcaliburPower extends ForgeRegistryEntry<ExcaliburPower> {

    public static final DeferredRegister<ExcaliburPower> POWERS = DeferredRegister.create(ExcaliburPower.class,
            Ancientology.MODID);

    public static final Supplier<IForgeRegistry<ExcaliburPower>> REGISTRY = POWERS.makeRegistry("excalibur_power",
            RegistryBuilder::new);

    public AABB getSweepHitBox(ItemStack stack, Player player, Entity target) {
        return null;
    }

}
