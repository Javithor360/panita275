package com.panita.panita275.core.config;

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
}
