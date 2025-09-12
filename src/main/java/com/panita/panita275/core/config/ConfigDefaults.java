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

    // --- End Module ---
    public static final boolean END_ENABLED = true;
    public static final double END_ENDER_PEARL_DROP_BUFF = 3.0f;
    public static final boolean END_PRE_EVENT_INCREASE_ENDERMAN_SPAWN = true;
    public static final boolean END_OTHER_MOBS_DROP_PEARLS_ENABLED = true;
    public static final double END_OTHER_MOBS_DROP_PEARLS_CHANCE = 0.05f;
    public static final int END_OTHER_MOBS_DROP_PEARLS_MAX_AMOUNT = 1;
    public static final double END_PRE_EVENT_ENDERMAN_SPAWN_AMOUNT = 0.20f;
    public static final boolean END_PRE_EVENT_ENDER_MOB_SPAWN = true;
    public static final boolean END_PRE_EVENT_POWERED_ENDER_MOBS = false;
    public static final double END_PRE_EVENT_ENDER_MOB_SPAWN_AMOUNT = 0.50f;
}
