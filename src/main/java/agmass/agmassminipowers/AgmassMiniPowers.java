package agmass.agmassminipowers;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
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
import org.bukkit.plugin.Plugin;
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

    private boolean getHighestBlockYAt(Location loc){

        for (int i = loc.getBlockY(); i < 256; i++){
            Block block = loc.getWorld().getBlockAt(loc.getBlockX(), i, loc.getBlockZ());

            if(block != null && !block.isLiquid()
                    && block.getType() != Material.AIR
                    && block.getType() != Material.CAVE_AIR) {
                return true;
            }
        }

        return false;
    }

    public static void useAb(Player p) {
        if (AgmassMiniPowers.hasPP("firefly", p.getPlayer())) {
            NamespacedKey key69 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
            if (p.getPersistentDataContainer().get(key69, PersistentDataType.INTEGER) == 0) {
                p.getPersistentDataContainer().set(key69, PersistentDataType.INTEGER, 1);
            } else {
                p.getPersistentDataContainer().set(key69, PersistentDataType.INTEGER, 0);
            }
        }
        if (AgmassMiniPowers.hasPP("allay", p.getPlayer())) {
            p.setVelocity(new Vector(0, 0.3, 0));
        }
        if (AgmassMiniPowers.hasPP("elytrian", p.getPlayer())) {
            if (p.isOnGround()) {
                p.getWorld().spawnParticle(Particle.CLOUD, p.getLocation(), 10);
                p.setVelocity(new Vector(0, 20, 0));
            }
        }
        if (AgmassMiniPowers.hasPP("frog", p)) {
            p.getWorld().spawnEntity(p.getLocation(), EntityType.TADPOLE);
            p.getWorld().spawnParticle(Particle.FLASH, p.getLocation(), 10);
        }
        if (AgmassMiniPowers.hasPP("soulling", p)) {
            if (!p.getWorld().getName().equals("soulworld")) {
                new Location(Bukkit.getWorld("soulworld"), 0, -1, 0).getBlock().setType(Material.BEDROCK);
                p.teleport(new Location(Bukkit.getWorld("soulworld"), 0, 0, 0));
            } else {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation());
                p.sendMessage("You were sent back to the normal world.");
            }
        }
        if (AgmassMiniPowers.hasPP("warden", p)) {
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_WARDEN_SNIFF, 1, 1);
            for (Entity ps : p.getNearbyEntities(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ())) {
                if (ps instanceof Player){
                    Player pps = ((Player) ps).getPlayer();
                    p.spawnParticle(Particle.FLASH, pps.getLocation(), 2);
                    pps.addPotionEffect(PotionEffectType.DARKNESS.createEffect(180, 2));
                }
            }
        }
    }

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
        pps.add("swordsman");
        pps.add("firefly");
        pps.add("fox");
        pps.add("enderian");
        pps.add("allay");
        pps.add("human");
        this.getCommand("resetPP").setExecutor(new CommandKit());
        this.getCommand("a").setExecutor(new CommandKit2());
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
        if (Bukkit.getWorld("pickingworld") == null) {
            WorldCreator wc = new WorldCreator("pickingworld");

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.FLAT);
            wc.generatorSettings(worldJSON);

            wc.createWorld();
        }
        if (Bukkit.getWorld("soulworld") == null) {
            WorldCreator wce = new WorldCreator("soulworld");
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
                    if (!AgmassMiniPowers.hasPP("warden", event.getPlayer()))
                        all.spawnParticle(Particle.FLASH, event.getPlayer().getLocation(), 2);
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
                if (!AgmassMiniPowers.hasPP("warden", event.getPlayer()))
                    all.spawnParticle(Particle.FLASH, event.getPlayer().getLocation(), 2);
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
                    if (!AgmassMiniPowers.hasPP("warden", Bukkit.getServer().getPlayer(event.getDamager().getName())))
                        all.spawnParticle(Particle.FLASH, Bukkit.getServer().getPlayer(event.getDamager().getName()).getLocation(), 2);
                }
            }
        }
    }

    @EventHandler
    public void rst(PlayerJoinEvent event) {
        NamespacedKey key1 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        NamespacedKey key131232 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wasInWater");
        event.getPlayer().getPersistentDataContainer().set(key131232, PersistentDataType.INTEGER, 1);
        NamespacedKey key13123 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
        event.getPlayer().getPersistentDataContainer().remove(key13123);
        event.getPlayer().getPersistentDataContainer().set(key13123, PersistentDataType.INTEGER, 0);
        if (event.getPlayer().getPersistentDataContainer().get(key1, PersistentDataType.STRING) == null) {
            event.getPlayer().getPersistentDataContainer().set(key1, PersistentDataType.STRING, "NaN");
        }
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
        event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (AgmassMiniPowers.hasPP("warden", all)) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), event.getPlayer());
                if (!AgmassMiniPowers.hasPP("warden", event.getPlayer()))
                    all.spawnParticle(Particle.FLASH, event.getPlayer().getLocation(), 2);
            }
        }
    }

    @EventHandler
    public void useAbility(PlayerSwapHandItemsEvent e) {
        AgmassMiniPowers.useAb(e.getPlayer());
    }
    @EventHandler
    public void noise3(BlockBreakEvent event) {
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
        event.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, 30.0);
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (AgmassMiniPowers.hasPP("warden", all)) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), event.getPlayer());
                if (!AgmassMiniPowers.hasPP("warden", event.getPlayer()))
                    all.spawnParticle(Particle.FLASH, event.getPlayer().getLocation(), 2);
            }
        }
    }

    @EventHandler
    public void noise6(BlockReceiveGameEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity().getType().equals(EntityType.PLAYER)) {
                if (AgmassMiniPowers.hasPP("warden", Bukkit.getPlayer(event.getEntity().getName())) || AgmassMiniPowers.hasPP("frog", Bukkit.getPlayer(event.getEntity().getName()))) {
                    event.setCancelled(true);
                }
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
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if ((event.getTarget() instanceof Player) && (AgmassMiniPowers.hasPP("enderian", Bukkit.getPlayer(event.getTarget().getName())))) {
            EntityType et = event.getEntity().getType();
            if (et.equals(EntityType.ENDERMAN) || et.equals(EntityType.SHULKER) || et.equals(EntityType.SHULKER_BULLET) || et.equals(EntityType.ENDER_DRAGON)) {
                event.setCancelled(true);
            }
        }
        if ((event.getTarget() instanceof Player) && (AgmassMiniPowers.hasPP("soulling", Bukkit.getPlayer(event.getTarget().getName())))) {
            EntityType et = event.getEntity().getType();
            if (et.equals(EntityType.STRIDER) || et.equals(EntityType.ZOMBIFIED_PIGLIN) || et.equals(EntityType.GHAST) || et.equals(EntityType.PIGLIN) || et.equals(EntityType.HOGLIN) || et.equals(EntityType.MAGMA_CUBE) || et.equals(EntityType.BLAZE) || et.equals(EntityType.WITHER_SKELETON) || et.equals(EntityType.PIGLIN_BRUTE))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void noFoodRegen(FoodLevelChangeEvent e) {
        if (AgmassMiniPowers.hasPP("frog", Bukkit.getPlayer(e.getEntity().getName())) && e.getFoodLevel() > e.getEntity().getFoodLevel()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void soulBond(EntityDamageEvent event) {
        EntityType et = event.getEntity().getType();
        if (et == EntityType.PLAYER) {
            if (AgmassMiniPowers.hasPP("merling", (Player) event.getEntity())) {
                if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                    event.setCancelled(true);
                }
            }
            if (AgmassMiniPowers.hasPP("elytrian", (Player) event.getEntity())) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                    ((Player) event.getEntity()).damage(event.getDamage());
                }
            }
            if (AgmassMiniPowers.hasPP("allay", (Player) event.getEntity())) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                    event.setCancelled(true);
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
        if (AgmassMiniPowers.hasPP("frog", e.getPlayer()) || AgmassMiniPowers.hasPP("allay", e.getPlayer())) {
            e.setCancelled(true);
        }
        if (AgmassMiniPowers.hasPP("fox", e.getPlayer())) {
            List<Material> vegan = new ArrayList<Material>();
            vegan.add(Material.GLOW_BERRIES);
            vegan.add(Material.SWEET_BERRIES);
            if (!vegan.contains(e.getItem().getType())) {
                e.setCancelled(true);
            }
        }
        if (AgmassMiniPowers.hasPP("firefly", e.getPlayer())) {
            List<Material> vegan = new ArrayList<Material>();
            vegan.add(Material.PORKCHOP);
            vegan.add(Material.COOKED_PORKCHOP);
            vegan.add(Material.COD);
            vegan.add(Material.SALMON);
            vegan.add(Material.TROPICAL_FISH);
            vegan.add(Material.PUFFERFISH);
            vegan.add(Material.COOKED_COD);
            vegan.add(Material.COOKED_SALMON);
            vegan.add(Material.BEEF);
            vegan.add(Material.COOKED_BEEF);
            vegan.add(Material.CHICKEN);
            vegan.add(Material.COOKED_CHICKEN);
            vegan.add(Material.ROTTEN_FLESH);
            vegan.add(Material.SPIDER_EYE);
            vegan.add(Material.RABBIT);
            vegan.add(Material.COOKED_RABBIT);
            vegan.add(Material.RABBIT_STEW);
            vegan.add(Material.MUTTON);
            vegan.add(Material.COOKED_MUTTON);
            if (vegan.contains(e.getItem().getType())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void frogeat(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() == EntityType.PLAYER) {
            if (AgmassMiniPowers.hasPP("firefly", Bukkit.getPlayer(e.getEntity().getName()))) {
                if(e.getDamager() instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity) e.getDamager();
                    living.addPotionEffect(PotionEffectType.POISON.createEffect(60, 1));
                }
            }
        }
        if (e.getDamager().getType() == EntityType.PLAYER) {
            if (AgmassMiniPowers.hasPP("swordsman", Bukkit.getPlayer(e.getDamager().getName()))) {
                List<Material> weaks = new ArrayList<Material>();
                weaks.add(Material.DIAMOND_AXE);
                weaks.add(Material.GOLDEN_AXE);
                weaks.add(Material.IRON_AXE);
                weaks.add(Material.STONE_AXE);
                weaks.add(Material.WOODEN_AXE);
                List<Material> strongs = new ArrayList<Material>();
                strongs.add(Material.DIAMOND_SWORD);
                strongs.add(Material.GOLDEN_SWORD);
                strongs.add(Material.IRON_SWORD);
                strongs.add(Material.STONE_SWORD);
                strongs.add(Material.WOODEN_SWORD);
                weaks.add(Material.TRIDENT);
                List<Material> unusable = new ArrayList<Material>();
                unusable.add(Material.NETHERITE_AXE);
                unusable.add(Material.NETHERITE_SWORD);
                if (weaks.contains(Bukkit.getPlayer(e.getDamager().getName()).getInventory().getItemInMainHand().getType())) {
                    e.setDamage(e.getDamage() / 3);
                }
                if (unusable.contains(Bukkit.getPlayer(e.getDamager().getName()).getInventory().getItemInMainHand().getType())) {
                    e.setDamage(0);
                }
                if (strongs.contains(Bukkit.getPlayer(e.getDamager().getName()).getInventory().getItemInMainHand().getType())) {
                    e.setDamage(e.getDamage() * 1.5);
                }
                if(e.getEntity() instanceof LivingEntity) {
                    LivingEntity living = (LivingEntity) e.getEntity();
                    living.setMaximumNoDamageTicks(0);
                }
            }
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
        if (e.getEntity().getWorld().getName() == "pickingworld") {
            e.setCancelled(true);
        }
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
        if (AgmassMiniPowers.hasPP("soulling", (Player) e.getEntity())) {
            e.getDrops().clear();
        }
        if (AgmassMiniPowers.hasPP("fox", (Player) e.getEntity())) {
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.getDrops().clear();
            e.setDroppedExp(0);
        }
    }
    @EventHandler
    public void rclick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (AgmassMiniPowers.hasPP("allay", e.getPlayer())) {
                if (e.getItem() != null) {
                    if (e.getItem().getType() == Material.AMETHYST_SHARD) {
                        if (e.getPlayer().getFoodLevel() < 20) {
                            e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 3);
                            e.getPlayer().setSaturation(e.getPlayer().getSaturation() + 2);
                            e.getItem().setAmount(e.getItem().getAmount() - 1);
                            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ALLAY_AMBIENT_WITH_ITEM, 1, 1);
                        }
                    }
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (AgmassMiniPowers.hasPP("soulling", e.getPlayer()) || AgmassMiniPowers.hasPP("fox", e.getPlayer())) {
                if (AgmassMiniPowers.beds.contains(e.getClickedBlock().getType())) {
                    e.getPlayer().sendMessage("You can't sleep.");
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
        Location location = new Location(Bukkit.getWorld("pickingworld"), 0, -45, 0, -180, 90);
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
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [WARDEN]\n" + ChatColor.GRAY + "A blind creature born in the depths of caverns from a strange sculk substance with feared powers." + ChatColor.RED + "\nTerrible Vision" + ChatColor.GRAY + "\nYour blind eyes make you only be able to see players making sound.", ChatColor.RED + "Sculk-Infused" + ChatColor.GRAY + "\nYou have constant 'sculk' around your screen, making you mine, walk and jump slower." + ChatColor.GOLD + "\n\nHeartbeat" + ChatColor.GRAY + "\nA heartbeat sound constantly plays around you. can be disabled by setting 'voices/speech' to 0 in sound settings.", ChatColor.GREEN + "Dark Spread" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can give anyone around you the darkness effect for 9 seconds, and inflicts you with it for ~4s." + ChatColor.GREEN + "\n\nSculky Friends" + ChatColor.GRAY + "\nYou will not trigger sculk sensors.", ChatColor.GREEN + "Shrieked" + ChatColor.GRAY + "\nWhen a player activates a shrieker, Instead of a warden spawning, you will be summoned for an easy catch. Warden will not spawn as long as you are online.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 1) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [SOULLING]\n" + ChatColor.GRAY + "Trapped inside the nether's soul sand valleys, souls are trying their best not to go back to their painful origins." + ChatColor.RED + "\n\nWeak" + ChatColor.GRAY + "\nYou have weakness and slow falling", ChatColor.RED + "Trail" + ChatColor.GRAY + "\nYou leave a trail of lava" + ChatColor.GOLD + "\n\nGhostly Pockets" + ChatColor.GRAY + "\nYou do not drop your items.", ChatColor.GREEN + "Soul Sand" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can teleport into a new air world. Only you and other soullings can access this." + ChatColor.RED + "\n\nSoul-bound" + ChatColor.GRAY + "\nWhen a nether creature takes damage, you take 1/4 of it's damage.", ChatColor.GREEN + "Neighbours" + ChatColor.GRAY + "\nNether mobs will not target you.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 2) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [FROG]\n" + ChatColor.GRAY + "Ribbit" + ChatColor.RED + "\n\nHeavy Eater" + ChatColor.GRAY + "\nWhen damaging a mob or player, you get food back. You cannot regain by natural hunger" + ChatColor.RED + "\nJumpy Jumpy" + ChatColor.GRAY + "\nYou have slowness unless you jump.", ChatColor.GREEN + "Up, Up And Up" + ChatColor.GRAY + "\nYou have higher jump boost." + ChatColor.GREEN + "\n\nDefensive Tadpoles" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can use 1/2 of your hunger bar to cause a big particle flash and summon a few very distracting tadpoles. ", ChatColor.GREEN + "Waterborne" + ChatColor.GRAY + "\nYou do amazingly in water and can stay in it for very long periods" + ChatColor.GREEN + "\n\nSticky Feet" + ChatColor.GRAY + "\nYour feet are silent and cannot be heard by sculk sensors");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 3) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [ELYTRIAN]\n" + ChatColor.GRAY + "Peacful Builders from the sky that have developed natural wings" + ChatColor.RED + "\n\nNeed For Mobility" + ChatColor.GRAY + "\nYou cannot wear chestplates.", ChatColor.RED + "Claustrophobic" + ChatColor.GRAY + "\nYou get slowness and weakness if a block is above you" + ChatColor.RED + "\nFallFlying" + ChatColor.GRAY + "\nYou take 2x fall damage." + ChatColor.GREEN + "\nLaunch" + ChatColor.BLUE + " [F]" + ChatColor.GRAY + "\nUsing the F key, you can launch up into the skies " + ChatColor.GREEN + "\nElytrian" + ChatColor.GRAY + "\nYou have a permanent elytra");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 4) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [Merling]" + ChatColor.GRAY + "" + ChatColor.GOLD + "\n\nWater Being" + ChatColor.GRAY + "\nYou cannot breathe on land, but have extrodinary water powers. You have perma-haste and can breath infinitely and are fast in water.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 5) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [SWORDSMAN]" + ChatColor.RED + "\n\nJust the classics" + ChatColor.GRAY + "\nUsing new combat methods/tools are weak." + ChatColor.RED + "\n\nCarpal Tunnel" + ChatColor.GRAY + "\nWhen moving using your hands- swimming, climbing, using elytra you get bad effects.", ChatColor.GOLD + "Attackable" + ChatColor.GRAY + "\nYou never have invlunerablity ticks, but pepole you hit do not get invincibility ticks." + ChatColor.GREEN + "\nJitter" + ChatColor.GRAY + "\nYou have no attack cooldown." + ChatColor.GREEN + "\nSkilled" + ChatColor.GRAY + "\nYou do 1.5x of your damage when you use a sword");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 6) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [FIREFLY]" + ChatColor.RED + "\n\nBottom of the food chain" + ChatColor.GRAY + "\nYou can only eat plants." + ChatColor.RED + "\n\nSmall" + ChatColor.GRAY + "\nYou have 9 hearts.", ChatColor.GREEN + "Fly" + ChatColor.BLUE + "[F]" + ChatColor.GRAY + "\nWhen pressing F, you can swap between having levitation or slow falling." + ChatColor.GREEN + "\nLightning Bug" + ChatColor.GRAY + "\nYou have permanant night vision." + ChatColor.GREEN + "\nPoisonous" + ChatColor.GRAY + "\nAnyone who hits you gets poison");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 7) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [FOX]" + ChatColor.RED + "\n\nBerry Picky Eater" + ChatColor.GRAY + "\nYou can only eat glow berries and normal berries." + ChatColor.RED + "\n\nNight Owl" + ChatColor.GRAY + "\nYou cannot sleep.", ChatColor.GREEN + "Sly Fox" + ChatColor.GRAY + "\nYou run and jump faster" + ChatColor.GREEN + "\nStrong eyes" + ChatColor.GRAY + "\nYou have permanant night vision." + ChatColor.GREEN + "\nHoarder" + ChatColor.GRAY + "\nYou have keepinv.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 8) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [ENDERIAN]" + ChatColor.RED + "\n\nHydrophobic" + ChatColor.GRAY + "\nYou take damage in water" + ChatColor.RED + "\n\nScared Of Gourds" + ChatColor.GRAY + "\nYou cannot see players with pumpkins.", ChatColor.GOLD + "Teleportation" + ChatColor.GRAY + "\n9th slot is an enderpearl" + ChatColor.GREEN + "\nOld Friends" + ChatColor.GRAY + "\nEnd mobs do not attack you.");
            }
            if (p.getPersistentDataContainer().get(key13123, PersistentDataType.INTEGER) == 9) {
                bm.addPage(ChatColor.LIGHT_PURPLE + "Drop this book to choose this origin! [ALLAY]" + ChatColor.RED + "\n\nHungry Little Boi" + ChatColor.GRAY + "\nYou can only eat amethyst", ChatColor.GOLD + "\n\nGlide" + ChatColor.BLUE + "[F]" + ChatColor.GRAY + "\nYou constantly are in an elytra state and can glide over the world. Press F to gain a small boost up.", ChatColor.GOLD + "Regeneration" + ChatColor.GRAY + "\nYou regenerate" + ChatColor.GREEN);
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
            p.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(2, 0));
            p.getWorld().spawnParticle(Particle.DRIP_LAVA, p.getLocation(), 1);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("allay", p);
        }).forEach((p) -> {
            p.setFlySpeed(0.75f);
            p.addPotionEffect(PotionEffectType.JUMP.createEffect(2, -2));
            p.addPotionEffect(PotionEffectType.REGENERATION.createEffect(2, 0));
            //if (!p.isOnGround())
            //    p.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(2, 0));
            if (!p.isGliding())
                p.setGliding(true);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("fox", p);
        }).forEach((p) -> {
            p.setFallDistance(0);
            p.addPotionEffect(PotionEffectType.JUMP.createEffect(2, 1));
            p.addPotionEffect(PotionEffectType.SPEED.createEffect(2, 1));
            p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(300, 0));
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("firefly", p);
        }).forEach((p) -> {
            p.setMaxHealth(16);
            NamespacedKey key69 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "selectedPP");
            if (p.getPersistentDataContainer().get(key69, PersistentDataType.INTEGER) == null) {
                p.getPersistentDataContainer().set(key69, PersistentDataType.INTEGER, 0);
            }
            p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(900, 0));
            if (p.getPersistentDataContainer().get(key69, PersistentDataType.INTEGER) == 0) {
                if (p.isSneaking()) {
                    p.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(2, 0));
                }
            }
            if (p.getPersistentDataContainer().get(key69, PersistentDataType.INTEGER) == 1) {
                p.addPotionEffect(PotionEffectType.LEVITATION.createEffect(2, 0));
            }
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return !AgmassMiniPowers.hasPP("firefly", p);
        }).forEach((p) -> {
            p.setMaxHealth(20);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("swordsman", p);
        }).forEach((p) -> {
            if (p.isSwimming() || p.isGliding() || p.isClimbing()) {
                p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(2, 0));
                p.addPotionEffect(PotionEffectType.SLOW_DIGGING.createEffect(2, 0));
                p.addPotionEffect(PotionEffectType.SLOW.createEffect(2, 0));
                p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(2, 0));
            }
            p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(9999);
            p.setMaximumNoDamageTicks(0);
            if (p.getInventory().getItemInOffHand().getType() == Material.SHIELD) {
                p.getInventory().getItemInOffHand().setType(Material.STICK);
                p.playSound(p, Sound.ITEM_SHIELD_BREAK, 1, 1);
            }
            if (p.getInventory().getItemInMainHand().getType() == Material.SHIELD) {
                p.getInventory().getItemInMainHand().setType(Material.STICK);
                p.playSound(p, Sound.ITEM_SHIELD_BREAK, 1, 1);
            }
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return !AgmassMiniPowers.hasPP("swordsman", p);
        }).forEach((p) -> {
            p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
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
            return !AgmassMiniPowers.hasPP("enderian", p);
        }).forEach((p) -> {
            if (p.getInventory().getHelmet() != null) {
                if (p.getInventory().getHelmet().getType() == Material.PUMPKIN) {
                    Bukkit.getOnlinePlayers().stream().filter((pee) -> {
                        return AgmassMiniPowers.hasPP("enderian", p);
                    }).forEach((pee) -> {
                        pee.hidePlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), p);
                    });
                }
            }
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("enderian", p);
        }).forEach((p) -> {
            if (p.isInWater()) {
                p.damage(1);
            }
            ItemStack is = new ItemStack(Material.ENDER_PEARL, 2);
            p.getInventory().setItem(8, is);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("merling", p);
        }).forEach((p) -> {
            NamespacedKey key131232 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wasInWater");
            p.addPotionEffect(PotionEffectType.CONDUIT_POWER.createEffect(2, 3));
            p.addPotionEffect(PotionEffectType.DOLPHINS_GRACE.createEffect(2, 3 ));
            if (p.isInWater() && !new Location(p.getWorld(), p.getLocation().getX(), p.getEyeLocation().getY(), p.getLocation().getZ()).getBlock().getType().equals(Material.AIR) || !p.getWorld().isClearWeather() || p.hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
                if (p.getRemainingAir() >= 300 || p.getPersistentDataContainer().get(key131232, PersistentDataType.INTEGER) == 1) {
                    p.getPersistentDataContainer().set(key131232, PersistentDataType.INTEGER, 1);
                    p.setRemainingAir(0);
                } else {
                    p.setRemainingAir(p.getRemainingAir() + 6);
                }
            } else {
                if (p.getPersistentDataContainer().get(key131232, PersistentDataType.INTEGER) == 1) p.setRemainingAir(300);
                p.getPersistentDataContainer().set(key131232, PersistentDataType.INTEGER, 0);
                p.setRemainingAir(p.getRemainingAir() - 6);
                if (p.getRemainingAir() <= 0) {
                    p.damage(1);
                }
            }
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return !AgmassMiniPowers.hasPP("frog", p);
        }).forEach((p) -> {
            p.setWalkSpeed(0.2f);
        });
        Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return AgmassMiniPowers.hasPP("warden", p);
        }).forEach((p) -> {
            p.addPotionEffect(PotionEffectType.DARKNESS.createEffect(200, 2));
            p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(200, 0));
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
                .forEach(p -> p.setFreezeTicks(10));
    }
}

class MyTaske extends BukkitRunnable {
    @Override
    public void run(){
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> !AgmassMiniPowers.hasPP("swordsman", p))
                .forEach(p -> p.setMaximumNoDamageTicks(20));
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> AgmassMiniPowers.hasPP("warden", p))
                .forEach(p -> Bukkit.getWorld(p.getWorld().getName()).playSound(p.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, SoundCategory.VOICE, 1, 0));
    }
}

class CommandKit2 implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            AgmassMiniPowers.useAb((Player) sender);
        } else {
            return false;
        }
        return true;
    }
}