package com.panita.panita275.core.config;

import java.util.List;

public class ConfigDefaults {
    // --- General ---
    public static final String PREFIX = "<b><gradient:#B87333:#D55555:#B87333>PANITACRAFT</gradient></b> <#393341>»</#393341><reset> ";

    // --- Optimization Module ---
    public static final boolean OPTIMIZATION_ENABLED = true;
    public static final boolean OPTIMIZATION_DEBUG = false;
    public static final boolean OPTIMIZATION_MOB_SPAWNING_REDUCEWOLFS = true;

    // --- Quality of Life Module ---
    public static final boolean QOL_ENABLED = true;
    public static final boolean QOL_TOTEMS_ALERT = true;
    public static final String QOL_TOTEMS_MESSAGE = "&b%player_name% &7ha consumido un tótem en &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)";
    public static final boolean QOL_TOTEMS_PLAYSOUND = true;
    public static final String QOL_TOTEMS_SOUNDNAME = "minecraft:entity.vindicator.hurt";
    public static final boolean QOL_SHARECOORDINATES_ENABLED = true;
    public static final String QOL_SHARECOORDINATES_PUBLICMESSAGE = "&b%player_name% &7se encuentra en &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)";
    public static final String QOL_SHARECOORDINATES_PRIVATEMESSAGE = "&b%player_name% &7te ha compartido su ubicación, &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)";
    public static final String QOL_SHARECOORDINATES_SELFMESSAGE = "&7Tu ubicación es &bX: &7%player_x% &bY: &7%player_y% &bZ: &7%player_z% (&b%player_world_type%&7)";
    public static final String QOL_SHARECOORDINATES_SENTPRIVATECONFIRMATION = "&7Has compartido tu ubicación con &b%player_name%&7.";
    public static final boolean QOL_SHARECOORDINATES_PLAYSOUND = true;
    public static final String QOL_SHARECOORDINATES_SOUNDPUBLIC = "minecraft:entity.player.levelup";
    public static final String QOL_SHARECOORDINATES_SOUNDPRIVATE = "minecraft:entity.player.levelup";

    // --- Troll Module ---
    public static final boolean TROLL_ENABLED = true;
    public static final List<String> TROLL_AFFECTED_PLAYERS = List.of("Griisaia");
    public static final boolean TROLL_ENDER_UPGRADES_ENABLED = true;
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_4_MESSAGE_TEXT = "&aTu Enderchest ha incrementado su espacio hasta &e36&a slots.";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_4_MESSAGE_SOUND = "minecraft:entity.player.levelup";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_4_PERMISSION = "";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_5_MESSAGE_TEXT = "&aTu Enderchest ha incrementado su espacio hasta &e45&a slots.";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_5_MESSAGE_SOUND = "minecraft:entity.player.levelup";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_5_PERMISSION = "enderchest.size.4";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_6_MESSAGE_TEXT = "&aTu Enderchest ha incrementado su espacio hasta &e54&a slots.";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_6_MESSAGE_SOUND = "minecraft:entity.player.levelup";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_6_PERMISSION = "enderchest.size.5";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_COMMAND_MESSAGE_TEXT = "&aTu Enderchest se ha conectado de manera inalámbrica, ahora puedes abrirlo con &e/ec";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_COMMAND_MESSAGE_SOUND = "minecraft:entity.player.levelup";
    public static final String TROLL_ENDER_UPGRADES_ITEMS_SCROLL_UPGRADE_ENDERCHEST_COMMAND_PERMISSION = "";

    // --- End Module ---
    public static final boolean END_ENABLED = true;
    public static final double END_ENDER_PEARL_DROP_BUFF = 3.0f;

    public static final boolean END_OTHER_MOBS_DROP_PEARLS_ENABLED = true;
    public static final double END_OTHER_MOBS_DROP_PEARLS_CHANCE = 0.05f;
    public static final int END_OTHER_MOBS_DROP_PEARLS_MAX_AMOUNT = 1;
    public static final double END_ENDERMAN_HOSTILE_CHANCE = 0.05f;
    public static final double END_SKELETON_LEVITATION_CHANCE = 0.20f;

    public static final boolean END_BLAZE_FIREBALL_SPAWN_ESCORT = true;
    public static final double END_BLAZE_FIREBALL_ESCORT_CHANCE = 0.10f;

    public static final boolean END_PRE_EVENT_INCREASE_ENDERMAN_SPAWN = true;
    public static final double END_PRE_EVENT_ENDERMAN_SPAWN_AMOUNT = 0.20f;
    public static final boolean END_PRE_EVENT_ENDER_MOB_SPAWN = true;
    public static final boolean END_PRE_EVENT_POWERED_ENDER_MOBS = false;
    public static final double END_PRE_EVENT_ENDER_MOB_SPAWN_AMOUNT = 0.50f;
    public static final double END_PRE_EVENT_ENDERMAN_KILL_SPAWN_PHANTOM_CHANCE = 0.5f;

    public static final boolean END_PHANTOM_DROPS_ENABLED = true;
    public static final int END_PHANTOM_DROPS_EXP_DROPPED = 20;
    public static final double END_PHANTOM_DROPS_GHAST_TEAR_CHANCE = 0.20f;
    public static final int END_PHANTOM_DROPS_MAX_GHAST_TEAR_AMOUNT = 1;
    public static final int END_PHANTOM_DROPS_MAX_COAL_AMOUNT = 1;

    public static final boolean END_EVENT_ENDER_MOBS_ENABLED = true;
    public static final double END_EVENT_ENDER_MOB_SPAWN_AMOUNT = 0.2f;
    public static final double END_EVENT_ENDER_MOB_DROPPED_XP = 35.0f;
    public static final int END_EVENT_ENDER_MOB_MAX_PEARL_DROP_AMOUNT = 2;
    public static final boolean END_EVENT_SHULKERS_COLORED = true;
    public static final boolean END_EVENT_SHULKERS_VARIATIONS = true;
    public static final boolean END_EVENT_ARMOR_DOUBLE_JUMP = true;
    public static final boolean END_EVENT_ARMOR_NIGHT_VISION = true;
    public static final boolean END_EVENT_ARMOR_VOID_SAFETY = true;
    public static final boolean END_EVENT_ARMOR_DRAGON_DROP_ELYTRA = true;
    public static final double END_EVENT_ARMOR_ELYTRA_DROP_CHANCE = 0.4;
}
