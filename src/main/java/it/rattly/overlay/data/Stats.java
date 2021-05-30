package it.rattly.overlay.data;

import it.rattly.overlay.Constants;
import it.rattly.overlay.utils.Utils;
import org.json.JSONObject;

import java.io.IOException;

@SuppressWarnings("unused")
public class Stats {
    public static int stars = 0;
    public static int kills = 0;
    public static int deaths = 0;
    public static int wins = 0;
    public static int losses = 0;
    public static double kdr = 0;
    public static double wlr = 0;

    public static void updateStats() {
        JSONObject stats = getPlayerStats();

        if (stats == null) {
            System.out.println("error");
            return;
        }

        if (stats.has("success")) {
            if (!stats.getBoolean("success")) {
                return;
            }
        }

        JSONObject obj = stats.getJSONObject("player").getJSONObject("stats").getJSONObject("SkyWars");

        stars = (int) Math.floor(Utils.getLevel(obj.getInt("skywars_experience")));
        kills = obj.getInt("kills");
        deaths = obj.getInt("deaths");
        wins = obj.getInt("wins");
        losses = obj.getInt("losses");

        kdr = (double) kills / (double) deaths;
        wlr = (double) wins / (double) losses;

        kdr = Utils.round(kdr);
        wlr = Utils.round(wlr);
    }

    public static JSONObject getPlayerStats() {
        String pURL = "https://api.hypixel.net/player?key=" + Constants.KEY + "&uuid=" + Constants.NAME;
        try {
            return new JSONObject(Utils.doRequest(pURL));
        } catch (IOException e) {
            return null;
        }
    }
}
