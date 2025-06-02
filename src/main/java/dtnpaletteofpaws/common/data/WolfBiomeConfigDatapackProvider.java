package dtnpaletteofpaws.common.data;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.JsonOps;

import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfig;
import dtnpaletteofpaws.common.variant.biome_config.WolfBiomeConfigs;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.GsonHelper;

public abstract class WolfBiomeConfigDatapackProvider implements DataProvider {

    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> prov;
    private final String packName;
    private final Component description;

    public WolfBiomeConfigDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> prov, String packName, Component desc) {
        this.output = output; 
        this.prov = prov;
        this.packName = packName;
        this.description = desc;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        return this.prov.thenCompose(prov_resolved -> run(output, prov_resolved));
    }

    protected CompletableFuture<?> run(final CachedOutput output, final HolderLookup.Provider prov) {
        var configs_map = new HashMap<ResourceKey<WolfBiomeConfig>, WolfBiomeConfig>();
        var config_output = new WolfBiomeConfig.Output() {

            @Override
            public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<S>> regKey) {
                return prov.lookupOrThrow(regKey);
            }

            @Override
            public void accept(ResourceKey<WolfBiomeConfig> id, WolfBiomeConfig val) {
                if (configs_map.get(id) != null)
                    throw new IllegalStateException(
                        "Duplicate biome config for pack [ " + packName + " ] : " + id );
                configs_map.put(id, val);
            }
            
        };
        addWolfBiomeConfigsToPack(config_output);
        var entries = createDataPackEntries(prov, description, configs_map);
        var output_folder = this.output.getOutputFolder();
        var future = CompletableFuture.runAsync(() -> {
            try {
                writeJsonZipFile(output, packName, output_folder, entries);
            } catch (Exception e) {
                
            }
        }, net.minecraft.Util.backgroundExecutor());
        
        return future;
    }
    
    protected abstract void addWolfBiomeConfigsToPack(WolfBiomeConfig.Output output);

    private static List<Pair<Path, JsonElement>> createDataPackEntries(HolderLookup.Provider prov, 
        Component description, Map<ResourceKey<WolfBiomeConfig>, WolfBiomeConfig> configs) {
        
        var pack_output = new PackOutput(Path.of(""));
        var path_prov = createWolfBiomeConfigPathProvider(pack_output);

        var codec = WolfBiomeConfig.CODEC;
        var ops = prov.createSerializationContext(JsonOps.INSTANCE);

        var ret = new ArrayList<Pair<Path, JsonElement>>();
        ret.add(createPackMeta(pack_output.getOutputFolder(), description));
        
        for (var entry : configs.entrySet()) {
            var id = entry.getKey();
            var config = entry.getValue();
            var path = path_prov.json(id.location());
            var config_json = codec.encodeStart(ops, config).getOrThrow();
            ret.add(Pair.of(path, config_json));
        }

        return ret;
    }

    private static PackOutput.PathProvider createWolfBiomeConfigPathProvider(PackOutput output) {
        var reg_id = WolfBiomeConfigs.regKey().location();
        var reg_dir_path = reg_id.getNamespace() + "/" + reg_id.getPath();
		return output.createPathProvider(PackOutput.Target.DATA_PACK, reg_dir_path);
	}

    private static Pair<Path, JsonElement> createPackMeta(Path parent_folder, Component description) {
        var json = new JsonObject();
        var metadata_section_type = PackMetadataSection.TYPE;
        var metadata_section = new PackMetadataSection(description, 
            DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA), 
            Optional.empty());
        json.add(
            metadata_section_type.getMetadataSectionName(), 
            metadata_section_type.toJson(metadata_section)
        );
        var metadata_path = parent_folder.resolve("pack.mcmeta");
        return Pair.of(metadata_path, json);
    }

    private static void writeJsonZipFile(CachedOutput output, String filename, 
        Path path, List<Pair<Path, JsonElement>> entries) throws IOException {
        
        var zip_file_buffer = new ByteArrayOutputStream();
        var hashed_zip_file_buffer = new HashingOutputStream(Hashing.sha1(), zip_file_buffer);
        var json_buffer = new ByteArrayOutputStream();

        try (var zip_output = new ZipOutputStream(hashed_zip_file_buffer)) {
            for (var entry : entries) {
                var path_str = entry.getLeft().toString();
                var json = entry.getRight();
                
                zip_output.putNextEntry(new ZipEntry(path_str));

                try (var json_writer = new JsonWriter(
                    new OutputStreamWriter(json_buffer, StandardCharsets.UTF_8))) {
                        
                    json_writer.setSerializeNulls(false);
                    json_writer.setIndent("  ");
                    GsonHelper.writeValue(json_writer, json, KEY_COMPARATOR);
                }
                zip_output.write(json_buffer.toByteArray());
                json_buffer.reset();
                
                zip_output.closeEntry();
            }
        }

        output.writeIfNeeded(path.resolve(filename + ".zip"), 
            zip_file_buffer.toByteArray(), hashed_zip_file_buffer.hash());
    }

    @Override
    public String getName() {
        return "DTNP Wolf Biome Config Pack Generator.";
    }
    
}
