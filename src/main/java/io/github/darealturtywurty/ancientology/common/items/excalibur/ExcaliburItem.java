package io.github.darealturtywurty.ancientology.common.items.excalibur;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.AABB;

import net.minecraftforge.common.ForgeTier;

public class ExcaliburItem extends SwordItem {

    public ExcaliburItem(int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(TIER, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public AABB getSweepHitBox(ItemStack stack, Player player, Entity target) {
        final var power = getPower(stack);
        if (power != null) {
            final var newAABB = power.getSweepHitBox(stack, player, target);
            if (newAABB != null) { return newAABB; }
        }
        return super.getSweepHitBox(stack, player, target);
    }

    public static ExcaliburPower getPower(final ItemStack stack) {
        if (!stack.getOrCreateTag().contains("ExcaliburPower")) { return null; }
        return ExcaliburPower.REGISTRY.get()
                .getValue(new ResourceLocation(stack.getOrCreateTag().getString("ExcaliburPower")));
    }

    private static final Tier TIER = new ForgeTier(0, 0, 0, 0, 0, null, null);
}
