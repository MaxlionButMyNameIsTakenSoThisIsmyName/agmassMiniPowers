package agmass.agmassminipowers;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public final class AgmassMiniPowers extends JavaPlugin {

    public static void hasPP(String ppe, Player p) {
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        return if(p.getPersistentDataContainer().get(key, PersistentDataType.STRING) == ppe);
    }
    public static void wardenTimerReset(Player p, Double t) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (all.hasPermission("miniwarden.isWarden")) {
                all.showPlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), p);
                NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "wTime");
                p.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, t);
            }
        }
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getLogger().info("You're currently running AMP version " + this.getDescription().getVersion() + " (Made for MC" + this.getDescription().getAPIVersion() + ")");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}


class MyListener implements Listener {
    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (!event.getPlayer().isSneaking()) {
            AgmassMiniPowers.wardenTimerReset(event.getPlayer(), 30.0);
        }
    }

    @EventHandler
    public void noise2(BlockPlaceEvent event) {
        AgmassMiniPowers.wardenTimerReset(event.getPlayer(), 30.0);
    }

    @EventHandler
    public void noise5(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType().equals(EntityType.PLAYER)) {
            AgmassMiniPowers.wardenTimerReset(Bukkit.getServer().getPlayer(event.getDamager().getName()), 30.0);
        }
    }

    @EventHandler
    public void useAbility(PlayerSwapHandItemsEvent e) {
        NamespacedKey key = new NamespacedKey(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), "pp");
        e.getPlayer().getPersistentDataContainer().set(key, PersistentDataType.STRING, "warden");
        if (e.getPlayer().getPersistentDataContainer().get(key, PersistentDataType.STRING) == "warden") {
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
        AgmassMiniPowers.wardenTimerReset(event.getPlayer(), 30.0);
    }

    @EventHandler
    public void noise6(BlockReceiveGameEvent event) {
        if (event.getEntity().hasPermission("miniwarden.isWarden")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void replaceWarden(EntitySpawnEvent e) {
        if (e.getEntity().getType() == EntityType.WARDEN && Bukkit.getOnlinePlayers().stream().filter((p) -> {
            return p.hasPermission("miniwarden.isWarden");
        }).count() > 0L) {
            e.setCancelled(true);
            Bukkit.getOnlinePlayers().stream().filter((p) -> {
                return p.hasPermission("miniwarden.isWarden");
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
            if (all.getPlayer().getPersistentDataContainer().get(key, PersistentDataType.DOUBLE) < 1 && !all.hasPermission("miniwarden.isWarden")) {
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> p.hasPermission("miniwarden.isWarden"))
                        .forEach(p -> p.hidePlayer(AgmassMiniPowers.getPlugin(AgmassMiniPowers.class), all));
            }
        }
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("miniwarden.isWarden"))
                .forEach(p -> p.setFreezeTicks(40));
    }
}

class MyTaske extends BukkitRunnable {
    @Override
    public void run(){
        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("miniwarden.isWarden"))
                .forEach(p -> Bukkit.getWorld(p.getWorld().getName()).playSound(p.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, SoundCategory.VOICE, 1, 0));
    }
}

