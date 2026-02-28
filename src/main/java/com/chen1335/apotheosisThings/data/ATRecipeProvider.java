package com.chen1335.apotheosisThings.data;

import com.chen1335.apotheosisThings.ApotheosisThings;
import com.chen1335.apotheosisThings.object.ATItems;
import dev.shadowsoffire.apotheosis.Apoth;
import dev.shadowsoffire.apotheosis.affix.salvaging.SalvagingRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ATRecipeProvider extends RecipeProvider {
    public ATRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.@NotNull Provider holderLookup) {
        recipeOutput.accept(ApotheosisThings.id("salvaging/other/bow"), new SalvagingRecipe(Ingredient.of(Items.BOW), List.of(new SalvagingRecipe.OutputData(Items.STRING, 0, 3))), null);
        recipeOutput.accept(ApotheosisThings.id("salvaging/other/shield"), new SalvagingRecipe(Ingredient.of(Items.SHIELD), List.of(new SalvagingRecipe.OutputData(Items.IRON_INGOT, 0, 1))), null);
        recipeOutput.accept(ApotheosisThings.id("salvaging/other/crossbow"), new SalvagingRecipe(Ingredient.of(Items.CROSSBOW), List.of(new SalvagingRecipe.OutputData(Items.IRON_INGOT, 0, 1), new SalvagingRecipe.OutputData(Items.STRING, 0, 2))), null);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ATItems.SALVAGING_CHARM)
                .group("hanging_sign")
                .define('A', Apoth.Items.SALVAGING_TABLE.value())
                .define('B', Items.STRING)
                .define('C', Apoth.Items.COMMON_MATERIAL.value())
                .pattern(" CB")
                .pattern("CAC")
                .pattern(" C ")
                .unlockedBy("has_common_material", has(Apoth.Items.COMMON_MATERIAL.value()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ATItems.TRANSFER_CHARM)
                .group("hanging_sign")
                .define('A', Items.ENDER_PEARL)
                .define('B', Items.STRING)
                .define('C', Apoth.Items.COMMON_MATERIAL.value())
                .define('D', Apoth.Items.GEM_DUST.value())

                .pattern("DCB")
                .pattern("CAC")
                .pattern("DCD")
                .unlockedBy("has_gem", has(Apoth.Items.GEM.value()))
                .save(recipeOutput);
    }
}
