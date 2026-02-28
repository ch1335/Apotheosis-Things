package com.chen1335.apotheosisThings.data;

import com.chen1335.apotheosisThings.ApotheosisThings;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = ApotheosisThings.MODID)
public class DataMain {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        DatapackBuiltinEntriesProvider builtinEntriesProvider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                generator.getPackOutput(),
                event.getLookupProvider(),
                new RegistrySetBuilder()
                ,
                Map.of(),
                Set.of(ApotheosisThings.MODID)
        ));
        ATBlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new ATBlockTagsProvider(generator.getPackOutput(), builtinEntriesProvider.getRegistryProvider(), event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(), new ATItemTagsProvider(generator.getPackOutput(), builtinEntriesProvider.getRegistryProvider(), blockTagsProvider.contentsGetter()));

        generator.addProvider(event.includeServer(), new ATItemModelProvider(generator.getPackOutput(), event.getExistingFileHelper()));

        generator.addProvider(event.includeServer(), new ATRecipeProvider(generator.getPackOutput(), builtinEntriesProvider.getRegistryProvider()));

    }

}
