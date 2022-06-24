package agmass.agmassminipowers;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public final class AgmassMiniPowers extends JavaPlugin {

    public static boolean hasPP(String ppe, Player p) {
        NamespacedKey key1 = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        boolean b = p.getPersistentDataContainer().get(key1, PersistentDataType.STRING).equals(ppe);
        return b;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("resetPP").setExecutor(new CommandKit());
        this.getLogger().info("You're currently running AMP version " + this.getDescription().getVersion() + " (Made for MC" + this.getDescription().getAPIVersion() + ")");
        MyTask t1 = new MyTask();
        t1.runTaskTimer(getPlugin(this.getClass()), 0, 1);
        MyTaske t2 = new MyTaske();
        t2.runTaskTimer(getPlugin(this.getClass()), 0, 20);
        getServer().getPluginManager().registerEvents(new MyListener(), this);
        if (Bukkit.getWorld("pickingWorld") == null) {
            WorldCreator wc = new WorldCreator("pickingWorld");

            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.FLAT);
            wc.generatorSettings("0;0;0;");

            wc.createWorld();
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
        if (event.getPlayer().getPersistentDataContainer().get(key1, PersistentDataType.STRING) == null) {
            event.getPlayer().getPersistentDataContainer().set(key1, PersistentDataType.STRING, "warden");
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
            if (AgmassMiniPowers.hasPP("warden", Bukkit.getPlayer(event.getEntity().getName()))) {
                event.setCancelled(true);
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
}



class MyTask extends BukkitRunnable {
    @Override
    public void run(){
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

