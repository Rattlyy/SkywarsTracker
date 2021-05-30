package it.rattly.overlay;

import it.rattly.overlay.data.Stats;
import it.rattly.overlay.data.StatsThread;
import it.rattly.overlay.utils.Utils;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static JLabel stars;
    public static JLabel kills;
    public static JLabel deaths;
    public static JLabel wins;
    public static JLabel losses;
    public static JLabel kdr;
    public static JLabel wlr;
    
    @SneakyThrows
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setBackground(new Color(1.0f,1.0f,1.0f,0));
        frame.setLayout(new GridLayout(7,1));
        
        stars = Utils.makeString("Stars: " + Stats.stars);
        kills = Utils.makeString("Kills: " + Stats.kills);
        deaths = Utils.makeString("Deaths: " + Stats.deaths);
        wins = Utils.makeString("Wins: " + Stats.wins);
        losses = Utils.makeString("Losses: " + Stats.losses);
        kdr = Utils.makeString("KDR: " + Stats.kdr);
        wlr = Utils.makeString("WLR: " + Stats.wlr);

        frame.getContentPane().add(stars);
        frame.getContentPane().add(kills);
        frame.getContentPane().add(deaths);
        frame.getContentPane().add(wins);
        frame.getContentPane().add(losses);
        frame.getContentPane().add(kdr);
        frame.getContentPane().add(wlr);

        frame.pack();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

        int x = (int) rect.getMaxX() - frame.getWidth();
        int y = 30;

        x -= 90;

        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    @SneakyThrows
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar <file>.jar <apiKey> <uuid>");
            return;
        }

        Constants.KEY = args[0];

        if (!Utils.isUUID(args[1])) {
            System.out.println("Invalid uuid.");
            return;
        }

        Constants.NAME = args[1];

        Stats.updateStats();
        StatsThread.lastCheck = System.currentTimeMillis();
        new StatsThread().start();

        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}
