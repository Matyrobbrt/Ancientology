package io.github.darealturtywurty.ancientology.core.util.registry;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import org.apache.commons.compress.utils.Lists;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unchecked")
public class BlockBuilder<B extends Block> implements Builder<B> {

    protected final Factory<BlockBehaviour.Properties, B> factory;
    protected final BlockDeferredRegister register;
    protected final String name;
    private BlockItemBuilder<BlockItem> blockItemBuilder;
    private Material material = Material.HEAVY_METAL;
    private HarvestLevel harvestLevel;
    private HarvestTool harvestTool;

    BlockBuilder(final String name, Factory<Properties, B> factory, BlockDeferredRegister register) {
        this.factory = factory;
        this.register = register;
        this.name = name;

        this.blockItemBuilder = new BlockItemBuilder<>(BlockItem::new);
    }

    public BlockBuilder<B> material(@Nonnull Material material) {
        this.material = material;
        return this;
    }

    public BlockBuilder<B> harvestLevel(@Nonnull HarvestLevel harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    public BlockBuilder<B> harvestTool(@Nonnull HarvestTool harvestTool) {
        this.harvestTool = harvestTool;
        return this;
    }

    public BlockBuilder<B> blockItem(@Nonnull final Consumer<BlockItemBuilder<BlockItem>> consumer) {
        consumer.accept(blockItemBuilder);
        return this;
    }

    public <I extends BlockItem> BlockBuilder<B> blockItem(
            final BiFunction<B, net.minecraft.world.item.Item.Properties, I> factory,
            @Nonnull final Consumer<BlockItemBuilder<I>> consumer) {
        final var builder = new BlockItemBuilder<>(factory);
        consumer.accept(builder);
        this.blockItemBuilder = (BlockItemBuilder<BlockItem>) builder;
        return this;
    }

    @Override
    public RegistryObject<B> build() {
        final var object = register.getRegister().register(name, () -> factory.build(createProperties()));
        if (harvestLevel != null) {
            register.harvestLevels.computeIfAbsent(harvestLevel, k -> Lists.newArrayList())
                    .add(object::get);
        }
        if (harvestTool != null) {
            register.harvestTools.computeIfAbsent(harvestTool, k -> Lists.newArrayList())
                    .add(object::get);
        }
        if (blockItemBuilder != null) {
            blockItemBuilder.build(object);
        }
        return object;
    }

    protected Properties createProperties() {
        return Properties.of(material);
    }

    public class BlockItemBuilder<I extends BlockItem> extends ItemBuilder<I> {

        private final BiFunction<B, net.minecraft.world.item.Item.Properties, I> blockItemFactory;

        BlockItemBuilder(BiFunction<B, net.minecraft.world.item.Item.Properties, I> factory) {
            super(null, BlockBuilder.this.register.itemRegister, BlockBuilder.this.name);
            this.blockItemFactory = factory;
        }

        private RegistryObject<I> build(final RegistryObject<B> block) {
            return register.getRegister().register(name,
                    () -> blockItemFactory.apply(block.get(), super.createProperties()));
        }

        @Override
        public RegistryObject<I> build() {
            throw new UnsupportedOperationException("Cannot build a BlockItem without a block!");
        }

    }

    public enum HarvestLevel {

        WOOD(() -> Tags.Blocks.NEEDS_WOOD_TOOL), STONE(() -> BlockTags.NEEDS_STONE_TOOL),
        GOLD(() -> Tags.Blocks.NEEDS_GOLD_TOOL), IRON(() -> BlockTags.NEEDS_IRON_TOOL),
        DIAMOND(() -> BlockTags.NEEDS_DIAMOND_TOOL), NETHERITE(() -> Tags.Blocks.NEEDS_NETHERITE_TOOL);

        private final Supplier<Named<Block>> tagSupplier;

        private HarvestLevel(Supplier<Named<Block>> tagSupplier) {
            this.tagSupplier = tagSupplier;
        }

        public Named<Block> getTag() {
            return tagSupplier.get();
        }
    }

    public enum HarvestTool {

        PICKAXE(() -> BlockTags.MINEABLE_WITH_PICKAXE), AXE(() -> BlockTags.MINEABLE_WITH_AXE),
        SHOVEL(() -> BlockTags.MINEABLE_WITH_SHOVEL), HOE(() -> BlockTags.MINEABLE_WITH_HOE);

        private final Supplier<Named<Block>> tagSupplier;

        private HarvestTool(Supplier<Named<Block>> tagSupplier) {
            this.tagSupplier = tagSupplier;
        }

        public Named<Block> getTag() {
            return tagSupplier.get();
        }
    }

}
