package net.jrdemiurge.difficultychaosintegration;

import com.google.gson.*;
import net.minecraftforge.fml.ModList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class MajruszCompatibility {
    private static final String CONFIG_PATH = "config/majruszsdifficulty.json";

    public static void applyCompatibility() {
/*        if (!ModList.get().isLoaded("majruszsdifficulty")) {
            return; // Majrusz's Progressive Difficulty не установлен
        }*/

        try {
            // Читаем текущую конфигурацию
            JsonObject config = loadConfig();

            // Добавляем или обновляем секцию mob_groups
            JsonObject features = config.getAsJsonObject("features");
            if (features == null) {
                features = new JsonObject();
                config.add("features", features);
            }

            JsonObject mobGroups = new JsonObject();
            features.add("mob_groups", mobGroups);

            // Default
            mobGroups.add("piglins", createMobGroup(
                    "expert", 0.25, true,
                    new String[]{"minecraft:piglin"}, new String[]{"majruszsdifficulty:mob_groups/piglin_leader"},
                    new String[]{"minecraft:piglin"}, new String[]{"majruszsdifficulty:mob_groups/piglin_sidekick"},
                    1, 3
            ));

            mobGroups.add("skeletons", createMobGroup(
                    "normal", 0.1, true,
                    new String[]{"minecraft:skeleton"}, new String[]{"majruszsdifficulty:mob_groups/skeleton_leader"},
                    new String[]{"minecraft:skeleton"}, new String[]{"majruszsdifficulty:mob_groups/skeleton_sidekick"},
                    1, 3
            ));

            mobGroups.add("undead", createMobGroup(
                    "normal", 0.1, true,
                    new String[]{"minecraft:skeleton", "minecraft:stray", "minecraft:zombie", "minecraft:husk"},
                    new String[]{
                            "majruszsdifficulty:undead_army/wave_3_skeleton",
                            "majruszsdifficulty:undead_army/wave_3_skeleton",
                            "majruszsdifficulty:undead_army/wave_3_mob",
                            "majruszsdifficulty:undead_army/wave_3_mob"
                    },
                    new String[]{
                            "minecraft:skeleton",
                            "minecraft:stray",
                            "minecraft:zombie",
                            "minecraft:husk"
                    },
                    new String[]{
                            "majruszsdifficulty:undead_army/wave_2_mob",
                            "majruszsdifficulty:undead_army/wave_2_mob",
                            "majruszsdifficulty:undead_army/wave_2_mob",
                            "majruszsdifficulty:undead_army/wave_2_mob"
                    },
                    2, 4
            ));

            mobGroups.add("zombie_miners", createMobGroup(
                    "expert", 0.25, true,
                    new String[]{"minecraft:zombie"}, new String[]{"majruszsdifficulty:mob_groups/zombie_leader"},
                    new String[]{"minecraft:zombie"}, new String[]{"majruszsdifficulty:mob_groups/zombie_sidekick"},
                    1, 3
            ));


            // Custom
            mobGroups.add("spiritof_chaos", createMobGroup(
                    "master", 0.15, true,
                    new String[]{"born_in_chaos_v1:fallen_chaos_knight", "born_in_chaos_v1:mother_spider", "born_in_chaos_v1:dire_hound_leader",
                            "born_in_chaos_v1:glutton_fish", "born_in_chaos_v1:nightmare_stalker", "born_in_chaos_v1:missioner",
                            "born_in_chaos_v1:krampus", "born_in_chaos_v1:sir_pumpkinhead", "born_in_chaos_v1:supreme_bonescaller",
                            "born_in_chaos_v1:lifestealer_true_form"},
                    new String[]{"born_in_chaos_v1:spiritof_chaos"}, 1, 1
            ));

            mobGroups.add("skeleton_demoman", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:skeleton_demoman"},
                    new String[]{"born_in_chaos_v1:skeleton_demoman"}, 1, 3
            ));

            mobGroups.add("supreme_bonescaller", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:supreme_bonescaller"},
                    new String[]{"born_in_chaos_v1:fallen_chaos_knight", "born_in_chaos_v1:skeleton_thrasher"}, 1, 3
            ));

            mobGroups.add("bonescaller", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:bonescaller"},
                    new String[]{"born_in_chaos_v1:door_knight"}, 1, 3
            ));

            mobGroups.add("zombie_bruiser", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:zombie_bruiser"},
                    new String[]{"born_in_chaos_v1:zombie_clown", "born_in_chaos_v1:zombie_lumberjack"}, 1, 3
            ));

            mobGroups.add("spirit_guide", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:spirit_guide"},
                    new String[]{"born_in_chaos_v1:siamese_skeletons"}, 2, 3
            ));

            mobGroups.add("swarmer", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:swarmer"},
                    new String[]{"born_in_chaos_v1:bloody_gadfly"}, 1, 3
            ));

            mobGroups.add("fallen_chaos_knight", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:fallen_chaos_knight"},
                    new String[]{"born_in_chaos_v1:dark_vortex", "born_in_chaos_v1:fallen_chaos_knight"}, 1, 3
            ));

            mobGroups.add("seared_spirit", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:seared_spirit"},
                    new String[]{"born_in_chaos_v1:firelight", "born_in_chaos_v1:bone_imp"}, 2, 4
            ));

            mobGroups.add("barrel_zombie", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:barrel_zombie"},
                    new String[]{"born_in_chaos_v1:zombie_fisherman"}, 1, 3
            ));

            mobGroups.add("mother_spider", createMobGroup(
                    "expert", 0.15, true,
                    new String[]{"born_in_chaos_v1:mother_spider"},
                    new String[]{"born_in_chaos_v1:swarmer"}, 2, 2
            ));

            // Waves
            JsonObject undeadArmy = config.getAsJsonObject("undead_army");
            if (undeadArmy == null) {
                undeadArmy = new JsonObject();
                config.add("undead_army", undeadArmy);
            }
            JsonArray waves = new JsonArray();
            undeadArmy.add("waves", waves);

            // Добавляем волны
            waves.add(createWave(
                    new String[][] {{"born_in_chaos_v1:siamese_skeletons", "3"}, {"born_in_chaos_v1:spirit_guide", "3"}},
                    "normal", 8, null
            ));
            waves.add(createWave(
                    new String[][] {{"born_in_chaos_v1:door_knight", "3"}, {"born_in_chaos_v1:bonescaller", "3"}},
                    "normal", 16, null
            ));
            waves.add(createWave(
                    new String[][] {{"born_in_chaos_v1:skeleton_thrasher", "2"}, {"born_in_chaos_v1:dark_vortex", "2"},
                            {"born_in_chaos_v1:fallen_chaos_knight", "1"}, {"born_in_chaos_v1:supreme_bonescaller", "1"}},
                    "normal", 24, new String[]{"born_in_chaos_v1:nightmare_stalker", "1"}
            ));
            waves.add(createWave(
                    new String[][] {{"born_in_chaos_v1:swarmer", "6"}, {"born_in_chaos_v1:mother_spider", "2"}},
                    "expert", 32, null
            ));
            waves.add(createWave(
                    new String[][] {{"born_in_chaos_v1:skeleton_thrasher", "2"}, {"born_in_chaos_v1:dark_vortex", "2"},
                            {"born_in_chaos_v1:nightmare_stalker", "1"}, {"born_in_chaos_v1:fallen_chaos_knight", "2"},
                            {"born_in_chaos_v1:supreme_bonescaller", "2"}},
                    "expert", 40, new String[]{"born_in_chaos_v1:lifestealer_true_form", "1"}
            ));
            waves.add(createWave(
                    new String[][] {{"born_in_chaos_v1:zombie_clown", "6"}, {"born_in_chaos_v1:nightmare_stalker", "1"},
                            {"born_in_chaos_v1:lifestealer_true_form", "1"}},
                    "master", 48, new String[]{"majruszsdifficulty:giant", "1", "majruszsdifficulty:undead_army/wave_6_mob"}
            ));

            // Сохраняем изменённую конфигурацию
            saveConfig(config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JsonObject loadConfig() throws IOException {
        Path path = Path.of(CONFIG_PATH);
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                return JsonParser.parseReader(reader).getAsJsonObject();
            }
        }
        return new JsonObject(); // Возвращаем пустую конфигурацию, если файл не найден
    }

    private static void saveConfig(JsonObject config) throws IOException {
        Path path = Path.of(CONFIG_PATH);

        // Создаём Gson с форматированием
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Сохраняем JSON с отступами
        try (Writer writer = Files.newBufferedWriter(path)) {
            gson.toJson(config, writer);
        }
    }

    private static JsonObject createMobGroup(String gameStage, double chance, boolean isScaledByCRD,
                                             String[] leaderTypes, String[] leaderEquipments,
                                             String[] sidekickTypes, String[] sidekickEquipments,
                                             int minSidekicks, int maxSidekicks) {
        JsonObject group = new JsonObject();
        group.add("required_game_stage", new JsonPrimitive(gameStage));
        group.add("chance", new JsonPrimitive(chance));
        group.add("is_scaled_by_crd", new JsonPrimitive(isScaledByCRD));

        JsonArray leaders = new JsonArray();
        for (int i = 0; i < leaderTypes.length; i++) {
            JsonObject leaderObj = new JsonObject();
            leaderObj.add("type", new JsonPrimitive(leaderTypes[i]));
            leaderObj.add("equipment", new JsonPrimitive(leaderEquipments[i])); // Добавляем путь к оборудованию
            leaders.add(leaderObj);
        }
        group.add("leader_types", leaders);

        JsonObject sidekicksCount = new JsonObject();
        sidekicksCount.add("min", new JsonPrimitive(minSidekicks));
        sidekicksCount.add("max", new JsonPrimitive(maxSidekicks));
        group.add("sidekicks_count", sidekicksCount);

        JsonArray sidekicks = new JsonArray();
        for (int i = 0; i < sidekickTypes.length; i++) {
            JsonObject sidekickObj = new JsonObject();
            sidekickObj.add("type", new JsonPrimitive(sidekickTypes[i]));
            sidekickObj.add("equipment", new JsonPrimitive(sidekickEquipments[i])); // Добавляем путь к оборудованию
            sidekicks.add(sidekickObj);
        }
        group.add("sidekick_types", sidekicks);

        return group;
    }


    private static JsonObject createMobGroup(String gameStage, double chance, boolean isScaledByCRD,
                                             String[] leaderTypes, String[] sidekickTypes, int minSidekicks, int maxSidekicks) {
        JsonObject group = new JsonObject();
        group.add("required_game_stage", new JsonPrimitive(gameStage));
        group.add("chance", new JsonPrimitive(chance));
        group.add("is_scaled_by_crd", new JsonPrimitive(isScaledByCRD));

        JsonArray leaders = new JsonArray();
        for (String leader : leaderTypes) {
            JsonObject leaderObj = new JsonObject();
            leaderObj.add("type", new JsonPrimitive(leader));
            leaderObj.add("equipment", new JsonPrimitive(""));
            leaders.add(leaderObj);
        }
        group.add("leader_types", leaders);

        JsonObject sidekicksCount = new JsonObject();
        sidekicksCount.add("min", new JsonPrimitive(minSidekicks));
        sidekicksCount.add("max", new JsonPrimitive(maxSidekicks));
        group.add("sidekicks_count", sidekicksCount);

        JsonArray sidekicks = new JsonArray();
        for (String sidekick : sidekickTypes) {
            JsonObject sidekickObj = new JsonObject();
            sidekickObj.add("type", new JsonPrimitive(sidekick));
            sidekickObj.add("equipment", new JsonPrimitive(""));
            sidekicks.add(sidekickObj);
        }
        group.add("sidekick_types", sidekicks);

        return group;
    }

    private static JsonObject createWave(String[][] mobs, String gameStage, int exp, String[] boss) {
        JsonObject wave = new JsonObject();
        JsonArray mobsArray = new JsonArray();

        for (String[] mob : mobs) {
            JsonObject mobObject = new JsonObject();
            mobObject.addProperty("type", mob[0]);
            mobObject.addProperty("count", Integer.parseInt(mob[1]));
            mobsArray.add(mobObject);
        }

        wave.add("mobs", mobsArray);

        if (boss != null) {
            JsonObject bossObject = new JsonObject();
            bossObject.addProperty("type", boss[0]);
            bossObject.addProperty("count", Integer.parseInt(boss[1]));
            if (boss.length > 2) {
                bossObject.addProperty("equipment", boss[2]);
            }
            wave.add("boss", bossObject);
        }

        wave.addProperty("game_stage", gameStage);
        wave.addProperty("exp", exp);

        return wave;
    }
}
