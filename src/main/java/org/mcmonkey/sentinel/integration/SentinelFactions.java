package org.mcmonkey.sentinel.integration;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.mcmonkey.sentinel.SentinelIntegration;

public class SentinelFactions extends SentinelIntegration {

    @Override
    public String getTargetHelp() {
        return "factions:FACTION_NAME, factionsenemy:NAME, factionsally:NAME";
    }

    @Override
    public String[] getTargetPrefixes() {
        return new String[] { "factions", "factionsenemy", "factionsally" };
    }

    @Override
    public boolean isTarget(LivingEntity ent, String prefix, String value) {
        try {
            if (prefix.equals("factions") && ent instanceof Player) {
                Faction faction = Factions.getInstance().getByTag(value);
                for (FPlayer pl: faction.getFPlayers()) {
                    if (pl.getPlayer() != null && pl.getPlayer().getUniqueId() != null
                            && pl.getPlayer().getUniqueId().equals(ent.getUniqueId())) {
                        return true;
                    }
                }
            }
            else if (prefix.equals("factionsenemy") && ent instanceof Player) {
                Faction faction = Factions.getInstance().getByTag(value);
                FPlayer plf = FPlayers.getInstance().getByPlayer((Player)ent);
                if (faction.getRelationTo(plf).equals(Relation.ENEMY)) {
                    return true;
                }
            }
            else if (prefix.equals("factionsally") && ent instanceof Player) {
                Faction faction = Factions.getInstance().getByTag(value);
                FPlayer plf = FPlayers.getInstance().getByPlayer((Player)ent);
                if (faction.getRelationTo(plf).equals(Relation.ALLY)) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
