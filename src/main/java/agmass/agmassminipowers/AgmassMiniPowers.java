package agmass.agmassminipowers;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class AgmassMiniPowers extends JavaPlugin {

    public static List<String> pps = new ArrayList<String>();
    public static List<Material> beds = new ArrayList<Material>();
    public static boolean hasPP(String ppe, Player p) {
        NamespacedKey key1 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        boolean b = p.getPersistentDataContainer().get(key1, PersistentDataType.STRING).equals(ppe);
        return b;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        beds.add(Material.WHITE_BED);
        beds.add(Material.BLACK_BED);
        beds.add(Material.BLUE_BED);
        beds.add(Material.RED_BED);
        beds.add(Material.BROWN_BED);
        beds.add(Material.CYAN_BED);
        beds.add(Material.GREEN_BED);
        beds.add(Material.LIGHT_BLUE_BED);
        beds.add(Material.LIGHT_GRAY_BED);
        beds.add(Material.LIME_BED);
        beds.add(Material.GRAY_BED);
        beds.add(Material.MAGENTA_BED);
        beds.add(Material.ORANGE_BED);
        beds.add(Material.PINK_BED);
        beds.add(Material.PURPLE_BED);
        beds.add(Material.YELLOW_BED);
        pps.add("warden");
        pps.add("soulling");
        pps.add("frog");
        pps.add("elytrian");
        pps.add("merling");
        pps.add("human");
        this.getCommand("resetPP").setExecutor(new CommandKit());
        this.getLogger().info("You're currently running AMP version " + this.getDescription().getVersion() + " (Made for MC" + this.getDescription().getAPIVersion() + ")");
        MyTask t1 = new MyTask();
        t1.runTaskTimer(getPlugin(this.getClass()), 0, 1);
        MyTaske t2 = new MyTaske();
        t2.runTaskTimer(getPlugin(this.getClass()), 0, 20);
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        String worldJSON =
        "{\n" +
                "   \"layers\":[\n" +
                "      {\n" +
                "         \"block\":\"dirt\",\n" +
                "         \"height\":1\n" +
                "      }]\n" +
                "}";
        String wJs =
                "{\n" +
                        "   \"layers\":[\n" +
                        "      {\n" +
                        "         \"block\":\"air\",\n" +
                        "         \"height\":1\n" +
                        "      }]\n" +
                        "}";
        if (Bukkit.getWorld("pickingWorld") == null) {
            WorldCreator wc = new WorldCreator("pickingWorld");

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.FLAT);
            wc.generatorSettings(worldJSON);

            wc.createWorld();
        }
        if (Bukkit.getWorld("soulWorld") == null) {
            WorldCreator wce = new WorldCreator("soulWorld");
            wce.environment(World.Environment.NORMAL);
            wce.generatorSettings(wJs);
            wce.generateStructures(false);
            wce.type(WorldType.FLAT);
            wce.createWorld();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

class CommandKit implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command  command, String label, String[] args) {
        NamespacedKey key1 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getPersistentDataContainer().set(key1, PersistentDataType.STRING, "NaN");
        }
        return true;
    }
}

class MyListener implements Listener {
    public static void wardenTimerReset(Player p, Double t) {
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
        p.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, t);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (AgmassMiniPowers.hasPP("warden", all)) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), p);
            }
        }
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (!event.getPlayer().isSneaking()) {
            NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
            event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (AgmassMiniPowers.hasPP("warden", all)) {
                    all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), event.getPlayer());
                }
            }
            wardenTimerReset(event.getPlayer(), 30.0);
        }
    }

    @EventHandler
    public void noise2(BlockPlaceEvent event) {
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
        event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (AgmassMiniPowers.hasPP("warden", all)) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), event.getPlayer());
            }
        }
    }

    @EventHandler
    public void noise5(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType().equals(EntityType.PLAYER)) {
            NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
            event.getDamager().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (AgmassMiniPowers.hasPP("warden", all)) {
                    all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), Bukkit.getServer().getPlayer(event.getDamager().getName()));
                }
            }
        }
    }

    @EventHandler
    public void rst(PlayerJoinEvent event) {
        NamespacedKey key1 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        NamespacedKey key13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
        event.getPlayer().getPersistentDataContainer().remove(key13123);
        event.getPlayer().getPersistentDataContainer().set(key13123, PersistentDataType.INTEGER, 0);
        if (event.getPlayer().getPersistentDataContainer().get(key1, PersistentDataType.STRING) == null) {
            event.getPlayer().getPersistentDataContainer().set(key1, PersistentDataType.STRING, "NaN");
        }
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
        event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
        Bukkit.broadcastMessage(event.getPlayer().getPersistentDataContainer().get(key, PersistentDataType.DOUBLE).toString());
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (AgmassMiniPowers.hasPP("warden", all)) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), event.getPlayer());
            }
        }
    }

    @EventHandler
    public void useAbility(PlayerSwapHandItemsEvent e) {
        if (AgmassMiniPowers.hasPP("elytrian", e.getPlayer())) {
            if (e.getPlayer().isOnGround()) {
                e.getPlayer().getWorld().spawnParticle(Particle.CLOUD, e.getPlayer().getLocation(), 10);
                e.getPlayer().setVelocity(new Vector(0, 20, 0));
            }
        }
        if (AgmassMiniPowers.hasPP("frog", e.getPlayer())) {
            e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(), EntityType.TADPOLE);
            e.getPlayer().getWorld().spawnParticle(Particle.FLASH, e.getPlayer().getLocation(), 10);
        }
        if (AgmassMiniPowers.hasPP("soulling", e.getPlayer())) {
            if (e.getPlayer().getWorld().getName() != "soulWorld") {
                new Location(Bukkit.getWorld("soulWorld"), 0, -1, 0).getBlock().setType(Material.BEDROCK);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("soulWorld"), 0, 0, 0));
            } else {
                e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
                e.getPlayer().sendMessage("You were sent back to the normal world.");
            }
        }
        if (AgmassMiniPowers.hasPP("warden", e.getPlayer())) {
            e.getPlayer().addPotionEffect(PotionEffectType.DARKNESS.createEffect(90, 2));
            for (Entity ps : e.getPlayer().getNearbyEntities(e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ())) {
                if (ps instanceof Player){
                    Player pps = ((Player) ps).getPlayer();
                    pps.addPotionEffect(PotionEffectType.DARKNESS.createEffect(180, 2));
                }
            }
        }
    }
    @EventHandler
    public void noise3(BlockBreakEvent event) {
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
        event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (AgmassMiniPowers.hasPP("warden", all)) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), event.getPlayer());
            }
        }
    }

    @EventHandler
    public void noise6(BlockReceiveGameEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER)) {
            if (AgmassMiniPowers.hasPP("warden", Bukkit.getPlayer(event.getEntity().getName())) || AgmassMiniPowers.hasPP("frog", Bukkit.getPlayer(event.getEntity().getName()))) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        NamespacedKey key13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        NamespacedKey skey13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
        if (e.getItemDrop().getItemStack().getType() == Material.WRITTEN_BOOK) {
            if (e.getPlayer().getPersistentDataContainer().get(key13123, PersistentDataType.STRING).equals("NaN")) {
                e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
                e.getPlayer().getInventory().clear();
                e.getPlayer().addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(30, 255));
                e.getPlayer().getPersistentDataContainer().set(key13123, PersistentDataType.STRING, AgmassMiniPowers.pps.get(e.getPlayer().getPersistentDataContainer().get(skey13123, PersistentDataType.INTEGER)));
                if (e.getPlayer().getPersistentDataContainer().get(key13123, PersistentDataType.STRING).equals("warden")) {
                    e.getPlayer().sendMessage("1.19 Spigot does not include ancient cities to be locatable. Ask an op if you want to be tped to an ancient city.");
                }
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if ((event.getTarget() instanceof Player) && (AgmassMiniPowers.hasPP("soulling", Bukkit.getPlayer(event.getTarget().getName())))) {
            EntityType et = event.getEntity().getType();
            if (et.equals(EntityType.STRIDER) || et.equals(EntityType.ZOMBIFIED_PIGLIN) || et.equals(EntityType.GHAST) || et.equals(EntityType.PIGLIN) || et.equals(EntityType.HOGLIN) || et.equals(EntityType.MAGMA_CUBE) || et.equals(EntityType.BLAZE) || et.equals(EntityType.WITHER_SKELETON) || et.equals(EntityType.PIGLIN_BRUTE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void noFoodRegen(FoodLevelChangeEvent e) {
        if (AgmassMiniPowers.hasPP("frog", Bukkit.getPlayer(e.getEntity().getName()))) e.setCancelled(true);
    }
    @EventHandler
    public void soulBond(EntityDamageEvent event) {
        EntityType et = event.getEntity().getType();
        if (et == EntityType.PLAYER) {
            if (AgmassMiniPowers.hasPP("elytrian", (Player) event.getEntity())) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                    ((Player) event.getEntity()).damage(event.getDamage());
                }
            }
        }
        if (et.equals(EntityType.STRIDER) || et.equals(EntityType.ZOMBIFIED_PIGLIN) || et.equals(EntityType.GHAST) || et.equals(EntityType.PIGLIN) || et.equals(EntityType.HOGLIN) || et.equals(EntityType.MAGMA_CUBE) || et.equals(EntityType.BLAZE) || et.equals(EntityType.WITHER_SKELETON) || et.equals(EntityType.PIGLIN_BRUTE))
            Bukkit.getOnlinePlayers().stream()
                    .filter(p -> AgmassMiniPowers.hasPP("soulling", p))
                    .forEach(p -> p.damage(event.getDamage() / 4));
    }

    @EventHandler
    public void frogdis(PlayerItemConsumeEvent e) {
        if (AgmassMiniPowers.hasPP("frog", e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void frogeat(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            if (AgmassMiniPowers.hasPP("frog", Bukkit.getPlayer(e.getDamager().getName()))) {
                List<Material> fl = new ArrayList<Material>();
                fl.add(Material.OCHRE_FROGLIGHT);
                fl.add(Material.VERDANT_FROGLIGHT);
                fl.add(Material.VERDANT_FROGLIGHT);
                Bukkit.getPlayer(e.getDamager().getName()).setFoodLevel(Bukkit.getPlayer(e.getDamager().getName()).getFoodLevel() + 3);
                if (e.getEntityType() == EntityType.MAGMA_CUBE) {
                    ItemStack is = new ItemStack(fl.get(new Random().nextInt(3)), 1);
                    Bukkit.getPlayer(e.getDamager().getName()).getInventory().addItem();
                }
            }
        }
    }

    @EventHandler
    public void replaceWarden(EntitySpawnEvent e) {
        if (e.getEntity().getType() == EntityType.WARDEN && Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("warden", p);
        }).count() > 0L) {
            e.setCancelled(true);
            Bukkit.getOnlinePlayers().stream().filter((p) -> {
                return AgmassMiniPowers.hasPP("warden", p);
            }).forEach((p) -> {
                p.teleport(e.getLocation());
            });
        }

    }

    @EventHandler
    public void death(PlayerDeathEvent e) {
        e.getDrops().clear();
    }
    @EventHandler
    public void rclick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (AgmassMiniPowers.hasPP("soulling", e.getPlayer())) {
                if (AgmassMiniPowers.beds.contains(e.getClickedBlock().getType())) {
                    e.getPlayer().sendMessage("You can't sleep now, You never know what could happen.");
                    e.setCancelled(true);
                }
            }
        }
        if  (e.getPlayer().getInventory().getItemInMainHand().getItemMeta() != null) {
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "LEFT")) {
                NamespacedKey key13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
                if (e.getPlayer().getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) > 0) {
                    e.getPlayer().getPersistentDataContainer().set(key13123, PersistentDataType.INTEGER, e.getPlayer().getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) - 1);
                }
            }
            if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "RIGHT")) {
                NamespacedKey key13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
                if (e.getPlayer().getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) != AgmassMiniPowers.pps.size() - 1) {
                    e.getPlayer().getPersistentDataContainer().set(key13123, PersistentDataType.INTEGER, e.getPlayer().getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) + 1);
                }
            }
        }
    }
}



class MyTask extends BukkitRunnable {
    @Override
    public void run(){
        Location location = new Location(Bukkit.getWorld("pickingWorld"), 0, -45, 0, -180, 90);
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("NaN", p);
        }).forEach((p) -> {
            p.teleport(location);
            p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(2, 255));
            p.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(2, 255));
            PlayerInventory inv = p.getInventory();
            inv.clear();
            ItemStack l = new ItemStack(Material.SPECTRAL_ARROW, 1);
            ItemMeta lm = l.getItemMeta();
            lm.setDisplayName(ChatColor.LIGHT_PURPLE + "LEFT");
            lm.addEnchant(Enchantment.SWEEPING_EDGE, 8,true);
            l.setItemMeta(lm);
            ItemStack r = new ItemStack(Material.SPECTRAL_ARROW, 1);
            ItemMeta rm = r.getItemMeta();
            rm.setDisplayName(ChatColor.LIGHT_PURPLE + "RIGHT");
            rm.addEnchant(Enchantment.SWEEPING_EDGE, 8,true);
            r.setItemMeta(rm);
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta bm = (BookMeta) book.getItemMeta();
            NamespacedKey key13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == null) {
                    p.getPersistentDataContainer().set(key13123, PersistentDataType.INTEGER, 0);
            }
            bm.setTitle(AgmassMiniPowers.pps.get(p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER)).toUpperCase());
            bm.setAuthor(ChatColor.BLUE + "AMP Plugin");
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 0) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [WARDEN]\n" + ChatColor.GRAY + "A blind creature born in the depths of caverns from a strange sculk substance with feared powers." + ChatColor.RED + "\n\nTerrible Vision" + ChatColor.GRAY + "\nYour blind eyes make you only be able to see players making sound.", ChatColor.RED + "Sculk-Infused" + ChatColor.GRAY + "\nYou have constant 'sculk' around your screen, making you mine, walk and jump slower." + ChatColor.GOLD + "\n\nHeartbeat" + ChatColor.GRAY + "\nA heartbeat sound constantly plays around you. can be disabled by setting 'voices/speech' to 0 in sound settings.", ChatColor.GREEN + "Dark Spread" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can give anyone around you the darkness effect for 9 seconds, and inflicts you with it for ~4s." + ChatColor.GREEN + "\n\nSculky Friends" + ChatColor.GRAY + "\nYou will not trigger sculk sensors.", ChatColor.GREEN + "Shrieked" + ChatColor.GRAY + "\nWhen a player activates a shrieker, Instead of a warden spawning, you will be summoned for an easy catch. Warden will not spawn as long as you are online.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 1) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [SOULLING]\n" + ChatColor.GRAY + "Trapped inside the nether's soul sand valleys, souls are trying their best not to go back to their painful origins." + ChatColor.RED + "\nWeak" + ChatColor.GRAY + "\nYou have weakness and mining fatigue" + ChatColor.RED + "\nTrail" + ChatColor.GRAY + "\nYou leave a trail of lava", ChatColor.GOLD + "\n\nGhostly Pockets" + ChatColor.GRAY + "\nYou do not drop your items.", ChatColor.GREEN + "Soul Sand" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can teleport into a new air world. Only you and other soullings can access this." + ChatColor.RED + "\n\nSoul-bound" + ChatColor.GRAY + "\nWhen a nether creature takes damage, you take 1/4 of it's damage.", ChatColor.GREEN + "Neighbours" + ChatColor.GRAY + "\nNether mobs will not target you.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 2) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [FROG]\n" + ChatColor.GRAY + "Ribbit" + ChatColor.RED + "\n\nHeavy Eater" + ChatColor.GRAY + "\nWhen damaging a mob or player, you get food back. You cannot regain by natural hunger" + ChatColor.RED + "\nJumpy Jumpy" + ChatColor.GRAY + "\nYou have slowness unless you jump.", ChatColor.GREEN + "Up, Up And Up" + ChatColor.GRAY + "\nYou have higher jump boost." + ChatColor.GREEN + "\n\nDefensive Tadpoles" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can use 1/2 of your hunger bar to cause a big particle flash and summon a few very distracting tadpoles. ", ChatColor.GREEN + "Waterborne" + ChatColor.GRAY + "\nYou do amazingly in water and can stay in it for very long periods" + ChatColor.GREEN + "\n\nSticky Feet" + ChatColor.GRAY + "\nYour feet are silent and cannot be heard by sculk sensors");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 3) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [ELYTRIAN]\n" + ChatColor.GRAY + "Peacful Builders from the sky that have developed natural wings" + ChatColor.RED + "\n\nNeed For Mobility" + ChatColor.GRAY + "\nYou cannot wear chestplates.", ChatColor.RED + "Claustrophobic" + ChatColor.GRAY + "\nYou get slowness and weakness if a block is above you" + ChatColor.RED + "\nFallFlying" + ChatColor.GRAY + "\nYou take 2x fall damage." + ChatColor.GREEN + "\nLaunch" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can launch up into the skies " + ChatColor.GREEN + "\nElytrian" + ChatColor.GRAY + "\nYou have a permanent elytra");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 4) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [Merling]" + ChatColor.GRAY + "" + ChatColor.RED + "\n\nWater Being" + ChatColor.GRAY + "\nYou cannot wear chestplates.", ChatColor.RED + "Claustrophobic" + ChatColor.GRAY + "\nYou get slowness and weakness if a block is above you" + ChatColor.RED + "\nFallFlying" + ChatColor.GRAY + "\nYou take 2x fall damage." + ChatColor.GREEN + "\nLaunch" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can launch up into the skies " + ChatColor.GREEN + "\nElytrian" + ChatColor.GRAY + "\nYou have a permanent elytra");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == AgmassMiniPowers.pps.size() - 1) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [HUMAN]\n" + ChatColor.GRAY + "Dude. It's normal minecraft.");
            }
            book.setItemMeta(bm);
            inv.setItem(4, book);
            inv.setItem(5, r);
            inv.setItem(3, l);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("soulling", p);
        }).forEach((p) -> {
            p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(2, 0));
            p.addPotionEffect(PotionEffectType.SLOW_DIGGING.createEffect(2, 0));
            p.getWorld().spawnParticle(Particle.DRIP_LAVA, p.getLocation(), 1);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("elytrian", p);
        }).forEach((p) -> {
            ItemStack ely = new ItemStack(Material.ELYTRA, 69);
            ely.addEnchantment(Enchantment.BINDING_CURSE, 1);
            ely.addEnchantment(Enchantment.VANISHING_CURSE, 1);
            p.getInventory().setChestplate(ely);
            if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 2, p.getLocation().getZ()).getBlock().getType().equals(Material.AIR)) {
                p.addPotionEffect(PotionEffectType.SLOW.createEffect(2, 3));
                p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(2, 3));
            }
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("frog", p);
        }).forEach((p) -> {
            p.setWalkSpeed(0);
            p.setFallDistance(0);
            p.addPotionEffect(PotionEffectType.CONDUIT_POWER.createEffect(2, 0));
            p.addPotionEffect(PotionEffectType.DOLPHINS_GRACE.createEffect(2, 0));
            p.addPotionEffect(PotionEffectType.JUMP.createEffect(2, 1));
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("merling", p);
        }).forEach((p) -> {
            p.addPotionEffect(PotionEffectType.CONDUIT_POWER.createEffect(2, 3));
            p.addPotionEffect(PotionEffectType.DOLPHINS_GRACE.createEffect(2, 3 ));
            if (p.isInWater()) {
                p.setRemainingAir(300);
            } else {
                p.setRemainingAir(p.getRemainingAir() - 1);
                if (p.getRemainingAir() <= 0) {
                    p.damage(4);
                }
            }
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return !AgmassMiniPowers.hasPP("frog", p);
        }).forEach((p) -> {
            p.setWalkSpeed(0.2f);
        });
        for(Player all : Bukkit.getOnlinePlayers()) {
            NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
            all.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, all.getPlayer().getPersistentDataContainer().get(key, PersistentDataType.DOUBLE) - 1);
            if (all.getPlayer().getPersistentDataContainer().get(key, PersistentDataType.DOUBLE) < 1 && !AgmassMiniPowers.hasPP("warden", all)) {
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> AgmassMiniPowers.hasPP("warden", p))
                        .forEach(p -> p.hidePlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), all));
            }
        }

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> AgmassMiniPowers.hasPP("warden", p))
                .forEach(p -> p.setFreezeTicks(40));
    }
}

class MyTaske extends BukkitRunnable {
    @Override
    public void run(){
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> AgmassMiniPowers.hasPP("warden", p))
                .forEach(p -> Bukkit.getWorld(p.getWorld().getName()).playSound(p.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, SoundCategory.VOICE, 1, 0));
    }
}

