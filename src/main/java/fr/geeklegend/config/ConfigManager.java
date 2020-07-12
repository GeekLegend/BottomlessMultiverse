package fr.geeklegend.config;

import fr.geeklegend.Main;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

public class ConfigManager
{

    private Main plugin;

    private FileConfiguration defaultConfig, kitConfig;

    public ConfigManager(Main plugin)
    {
        this.plugin = plugin;
        this.defaultConfig = load("config");
        this.kitConfig = load("kits");

        create(new String[]{"config", "kits"});

        addDefaults();
    }

    private void addDefaults()
    {
        defaultConfig.options().copyDefaults(true);
        defaultConfig.addDefault("messages.prefix", "&6BM &8»");
        defaultConfig.addDefault("messages.join", "%prefix% &6%playername% &eà rejoint la partie. &6(%online%/%maxonline%)");
        defaultConfig.addDefault("messages.quit", "%prefix% &6%playername% &eà quitté la partie. &6(%online%/%maxonline%)");
        defaultConfig.addDefault("messages.death.killer", "%prefix% &a%victimname% &ea été tué par &c%killername%");
        defaultConfig.addDefault("messages.death.void", "%prefix% &a%victimname% &ea été tué par le vide.");
        defaultConfig.addDefault("messages.cantedit", "%prefix% &cUn opérateur edit déjà ceci.");
        defaultConfig.addDefault("messages.spectator", "&7&o[Spectateur] %playername% : %message%");
        defaultConfig.addDefault("messages.chat.text", "%teamnamecolor%%teamname% %playername% &f: %message%");
        defaultConfig.addDefault("messages.chat.global.prefix", "!");
        defaultConfig.addDefault("messages.actionbar", "&6Tués : &e%kills%");
        defaultConfig.addDefault("messages.kit.selected", "%prefix% &eKit %kitnamecolor%%kitname% &esélectionner.");
        defaultConfig.addDefault("messages.kit.already", "%prefix% &cKit %kitnamecolor%%kitname% &cdéjà sélectionner.");
        defaultConfig.addDefault("messages.kit.have", "%prefix% &cVous ne possédez pas le kit %kitnamecolor%%kitname%.");
        defaultConfig.addDefault("messages.editkit.help", "%prefix% &ePour sauvegarder ce kit, faites &6/savekit.");
        defaultConfig.addDefault("messages.editkit.saved", "%prefix% &eKit %kitnamecolor%%kitname% &esauvegarder.");
        defaultConfig.addDefault("messages.edititems.help", "%prefix% &ePour sauvegarder les items, faites &6/saveitems.");
        defaultConfig.addDefault("messages.edititems.saved", "%prefix% &eItems sauvegarder.");
        defaultConfig.addDefault("messages.teams.joined", "%prefix% &eVous avez rejoint l'équipe %teamnamecolor%%teamname%.");
        defaultConfig.addDefault("messages.teams.already", "%prefix% &cVous êtes déjà dans l'équipe %teamnamecolor%%teamname%.");
        defaultConfig.addDefault("messages.teams.maxplayers", "%prefix% &cL'équipe %teamnamecolor%%teamname% &cest complète.");
        defaultConfig.addDefault("messages.starting.title.firstline", "&6&lBM");
        defaultConfig.addDefault("messages.starting.title.secondline", "&eLa partie commence dans %timer%s.");
        defaultConfig.addDefault("messages.starting.chat", "%prefix% &eLa partie commence dans &6%timer% &eseconde(s).");
        defaultConfig.addDefault("messages.starting.cancelled", "%prefix% &cIl manque %online% joueurs pour commencer la partie.");
        defaultConfig.addDefault("messages.cage", "%prefix% &eDestruction des cages dans &6%timer% &eseconde(s).");
        defaultConfig.addDefault("messages.teleport.title.firstline", "&6&lTéléportation");
        defaultConfig.addDefault("messages.teleport.title.secondline", "&eTéléportation dans %timer%s.");
        defaultConfig.addDefault("messages.teleport.chat", "%prefix% &eUne faille a était ouverte téléportation dans &6%timer% seconde(s).");
        defaultConfig.addDefault("messages.border.title.firstline", "&6&lBordure");
        defaultConfig.addDefault("messages.border.title.secondline", "&eBordure dans %timer%s.");
        defaultConfig.addDefault("messages.border.chat", "%prefix% &eLa bordure se rétracte dans &6%timer% &eseconde(s).");
        defaultConfig.addDefault("messages.deathmatch.title.firstline", "&6&lMatch à mort");
        defaultConfig.addDefault("messages.deathmatch.title.secondline", "&eTéléportation dans %timer%s.");
        defaultConfig.addDefault("messages.deathmatch.chat", "%prefix% &eLe match à mort commence dans &6%timer% seconde(s).");
        defaultConfig.addDefault("messages.spectator", "%prefix% &eLa partie à déja commencer, vous êtes donc en spectateur.");

        defaultConfig.addDefault("join.spawn", "1.5,107.0,-3.5,180,0");
        defaultConfig.addDefault("join.gamemode", "adventure");
        defaultConfig.addDefault("join.void", 70);
        defaultConfig.addDefault("join.items.kits.slot", 0);
        defaultConfig.addDefault("join.items.kits.material", "name tag");
        defaultConfig.addDefault("join.items.kits.name", "&6Sélecteur de kit");
        defaultConfig.addDefault("join.items.kits.description", Arrays.asList("&eTu va avoir besoin de ça", "&epour combattre."));
        defaultConfig.addDefault("join.items.teams.slot", 1);
        defaultConfig.addDefault("join.items.teams.material", "white banner");
        defaultConfig.addDefault("join.items.teams.name", "&6Sélectionner une équipe");
        defaultConfig.addDefault("join.items.teams.description", Arrays.asList("&eUne équipe c'est important."));
        defaultConfig.addDefault("join.items.editkit.slot", 4);
        defaultConfig.addDefault("join.items.editkit.material", "anvil");
        defaultConfig.addDefault("join.items.editkit.name", "&6Edit Kits");
        defaultConfig.addDefault("join.items.editkit.description", Arrays.asList("&eC'est bon de sentir qu'on", "&ea le pouvoir de modifier.", "&7&oRéserver aux opérateurs."));
        defaultConfig.addDefault("join.items.edititems.slot", 5);
        defaultConfig.addDefault("join.items.edititems.material", "chest");
        defaultConfig.addDefault("join.items.edititems.name", "&6Edit Items");
        defaultConfig.addDefault("join.items.edititems.description", Arrays.asList("&eItems du coffre.", "&7&oRéserver aux opérateurs."));
        defaultConfig.addDefault("join.items.leave.slot", 8);
        defaultConfig.addDefault("join.items.leave.material", "red bed");
        defaultConfig.addDefault("join.items.leave.name", "&cQuitter");
        defaultConfig.addDefault("join.items.leave.description", Arrays.asList("&eQuitter la partie."));
        defaultConfig.addDefault("join.items.leave.redirect", "hub");
        defaultConfig.addDefault("teams.type", "duo");
        defaultConfig.addDefault("teams.list.team", Arrays.asList(
                "Rouge,red,red banner",
                "Bleue,blue,blue banner",
                "Rose,light purple,pink banner",
                "Orange,gold,orange banner",
                "Violet,dark purple,purple banner",
                "Gris,gray,gray banner",
                "Vert,green,green banner",
                "Jaune,yellow,yellow banner"));
        defaultConfig.addDefault("teams.list.players", "&e- %teamnamecolor%%playername%");
        defaultConfig.addDefault("inventory.teams.size", 9);
        defaultConfig.addDefault("inventory.teams.name", "Sélectionner une équipe");
        defaultConfig.addDefault("inventory.edititems.size", 3 * 9);
        defaultConfig.addDefault("inventory.edititems.name", "Sélectionner une phase");
        defaultConfig.addDefault("inventory.edititems.enchants.size", 3 * 9);
        defaultConfig.addDefault("inventory.edititems.enchants.name", "Sélectionner un enchantement");
        defaultConfig.addDefault("inventory.edititems.items.stage1.slot", 11);
        defaultConfig.addDefault("inventory.edititems.items.stage1.material", "green wool");
        defaultConfig.addDefault("inventory.edititems.items.stage1.name", "&aPhase 1");
        defaultConfig.addDefault("inventory.edititems.items.stage1.description", Arrays.asList("&eClique-gauche: Editer."));
        defaultConfig.addDefault("inventory.edititems.items.stage2.slot", 13);
        defaultConfig.addDefault("inventory.edititems.items.stage2.material", "orange wool");
        defaultConfig.addDefault("inventory.edititems.items.stage2.name", "&6Phase 2");
        defaultConfig.addDefault("inventory.edititems.items.stage2.description", Arrays.asList("&eClique-gauche: Editer."));
        defaultConfig.addDefault("inventory.edititems.items.stage3.slot", 15);
        defaultConfig.addDefault("inventory.edititems.items.stage3.material", "red wool");
        defaultConfig.addDefault("inventory.edititems.items.stage3.name", "&cPhase 3");
        defaultConfig.addDefault("inventory.edititems.items.stage3.description", Arrays.asList("&eClique-gauche: Editer."));
        defaultConfig.addDefault("schedulers.start.timer", 30);
        defaultConfig.addDefault("schedulers.start.minplayers", 1);
        defaultConfig.addDefault("schedulers.cage.timer", 5);
        defaultConfig.addDefault("schedulers.border.timer", 30);
        defaultConfig.addDefault("schedulers.teleport.timer", 45);
        defaultConfig.addDefault("game.world.stage1.border.center", "1.5,107.0,-3.5,0,0");
        defaultConfig.addDefault("game.world.stage1.border.size", 150);
        defaultConfig.addDefault("game.world.stage1.border.maxsize", 50);
        defaultConfig.addDefault("game.world.stage2.border.center", "1.5,107.0,-3.5,0,0");
        defaultConfig.addDefault("game.world.stage2.border.size", 150);
        defaultConfig.addDefault("game.world.stage2.border.maxsize", 50);
        defaultConfig.addDefault("game.world.stage3.border.center", "1.5,107.0,-3.5,0,0");
        defaultConfig.addDefault("game.world.stage3.border.size", 150);
        defaultConfig.addDefault("game.world.stage3.border.maxsize", 50);
        defaultConfig.addDefault("game.cages.stage1.material", "green stained glass");
        defaultConfig.addDefault("game.cages.stage2.material", "orange stained glass");
        defaultConfig.addDefault("game.cages.stage3.material", "red stained glass");
        defaultConfig.addDefault("game.cages.stage1.list", Arrays.asList("-49.5,95.0,95.5,-90,0",
                "-42.5,95.0,-33.5,-90,0",
                "-42.5,95.0,26.5,-90,0",
                "-28.5,95.0,-47.5,0,0",
                "1.5,95.0,-54.5,0,0",
                "31.5,95.0,-47.5,0,0",
                "45.5,95.0,-33.5,90,0",
                "52.5,94.0,-3.5,90,0",
                "45.5,95.0,26.5,90,0",
                "31.5,95.0,40.5,180,0",
                "1.5,95.0,47.5, 180,0",
                "-28.5,95.0,40.5,180,0"));
        defaultConfig.addDefault("game.cages.stage2.list", Arrays.asList("-49.5,95.0,95.5,-90,0",
                "-42.5,95.0,-33.5,-90,0",
                "-42.5,95.0,26.5,-90,0",
                "-28.5,95.0,-47.5,0,0",
                "1.5,95.0,-54.5,0,0",
                "31.5,95.0,-47.5,0,0",
                "45.5,95.0,-33.5,90,0",
                "52.5,94.0,-3.5,90,0",
                "45.5,95.0,26.5,90,0",
                "31.5,95.0,40.5,180,0",
                "1.5,95.0,47.5, 180,0",
                "-28.5,95.0,40.5,180,0"));
        defaultConfig.addDefault("game.cages.stage3.list", Arrays.asList("-49.5,95.0,95.5,-90,0",
                "-42.5,95.0,-33.5,-90,0",
                "-42.5,95.0,26.5,-90,0",
                "-28.5,95.0,-47.5,0,0",
                "1.5,95.0,-54.5,0,0",
                "31.5,95.0,-47.5,0,0",
                "45.5,95.0,-33.5,90,0",
                "52.5,94.0,-3.5,90,0",
                "45.5,95.0,26.5,90,0",
                "31.5,95.0,40.5,180,0",
                "1.5,95.0,47.5, 180,0",
                "-28.5,95.0,40.5,180,0"));
        defaultConfig.addDefault("game.chests.items", 5);
        defaultConfig.addDefault("game.chests.stage1.list", Arrays.asList());
        defaultConfig.addDefault("game.chests.stage2.list", Arrays.asList());
        defaultConfig.addDefault("game.chests.stage3.list", Arrays.asList());
        defaultConfig.addDefault("scoreboards.displayname", "&6&lBM");
        defaultConfig.addDefault("scoreboards.ip.text", "ipduserveur.fr");
        defaultConfig.addDefault("scoreboards.ip.colors.primary", "gold");
        defaultConfig.addDefault("scoreboards.ip.colors.secondary", "yellow");
        defaultConfig.addDefault("scoreboards.waiting.lines.1", "yellow");
        defaultConfig.addDefault("inventory.spectator.size", 3 * 9);
        defaultConfig.addDefault("inventory.spectator.name", "Menu spectateur");
        defaultConfig.addDefault("inventory.spectator.items.players.name", "&6%playername%");
        defaultConfig.addDefault("inventory.spectator.items.players.description", Arrays.asList("&eClique-gauche: Téléportation"));
        defaultConfig.addDefault("game.spectators.items.menu.slot", 4);
        defaultConfig.addDefault("game.spectators.items.menu.material", "compass");
        defaultConfig.addDefault("game.spectators.items.menu.name", "&6Menu spectateur");
        defaultConfig.addDefault("game.spectators.items.menu.description", Arrays.asList("&eVoir la liste des", "&ejoueurs encore en vie."));

        kitConfig.options().copyDefaults(true);
        kitConfig.addDefault("inventory.kits.size", 3 * 9);
        kitConfig.addDefault("inventory.kits.name", "Sélecteur de kit");
        kitConfig.addDefault("inventory.editkit.size", 3 * 9);
        kitConfig.addDefault("inventory.editkit.name", "Editkit");

        kitConfig.addDefault("kits.classique.name", "Classique");
        kitConfig.addDefault("kits.classique.namecolor", "gold");
        kitConfig.addDefault("kits.classique.icon", "stone sword");
        kitConfig.addDefault("kits.classique.permission", "permission.kit.classique");
        kitConfig.addDefault("kits.classique.description", Arrays.asList("&eKit de base pour jouer."));
        kitConfig.addDefault("kits.classique.armorcontents", Arrays.asList());
        kitConfig.addDefault("kits.classique.contents", Arrays.asList());

        save(defaultConfig, "config");
        save(kitConfig, "kits");
    }

    @SneakyThrows
    public void create(String fileName)
    {
        checkDataFolder();

        File file = new File(plugin.getDataFolder(), fileName + ".yml");

        if (!file.exists())
        {
            file.createNewFile();
        }
    }

    @SneakyThrows
    public void create(String[] filesName)
    {
        checkDataFolder();

        File file = null;

        for (String fileName : filesName)
        {
            file = new File(plugin.getDataFolder(), fileName + ".yml");
        }

        if (!file.exists())
        {
            file.createNewFile();
        }
    }

    private void checkDataFolder()
    {
        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists())
        {
            dataFolder.mkdir();
        }
    }

    public FileConfiguration load(String fileName)
    {
        return YamlConfiguration.loadConfiguration(getFile(fileName));
    }

    @SneakyThrows
    public void save(FileConfiguration config, String fileName)
    {
        config.save(getFile(fileName));
    }

    public File getFile(String fileName)
    {
        return new File(plugin.getDataFolder(), fileName + ".yml");
    }

    public FileConfiguration getDefaultConfig()
    {
        return defaultConfig;
    }

    public FileConfiguration getKitConfig()
    {
        return kitConfig;
    }
}
