package it.rattly.overlay.data;

import it.rattly.overlay.Main;

public class StatsThread extends Thread {
    public static long lastCheck = 0L;

    @Override
    public void run() {
        while (true) {
            if ((System.currentTimeMillis() - lastCheck) >= 15000) {
                System.out.println("updating stats");
                
                lastCheck = System.currentTimeMillis();
                Stats.updateStats();

                Main.stars.setText("Stars: " + Stats.stars);
                Main.kills.setText("Kills: " + Stats.kills);
                Main.deaths.setText("Deaths: " + Stats.deaths);
                Main.wins.setText("Wins: " + Stats.wins);
                Main.losses.setText("Losses: " + Stats.losses);
                Main.kdr.setText("KDR: " + Stats.kdr);
                Main.wlr.setText("WLR: " + Stats.wlr);
            }
        }
    }
}
