package it.rattly.overlay.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

@UtilityClass
public class Utils {
    public static double getLevel(double xp) {
        int[] xps = {0, 20, 70, 150, 250, 500, 1000, 2000, 3500, 6000, 10000, 15000};
        if (xp >= 15000) {
            return (xp - 15000) / 10000 + 12;
        } else {
            for (int i = 0; i < xps.length; i++) {
                if (xp < xps[i]) {
                    return i + (xp - xps[i - 1]) / (xps[i] - xps[i - 1]);
                }
            }
        }

        return 0;
    }

    @SneakyThrows
    public JLabel makeString(String str) {
        JLabel label = new JLabel(str);

        Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("Minecraft.ttf"));
        Font biggerFont = font.deriveFont(Font.PLAIN, 40);
        label.setFont(biggerFont);
        label.setForeground(Color.WHITE);

        return label;
    }

    public double round(double val) {
        return Double.parseDouble(String.format("%.2f", val).replace(",", "."));
    }

    public String doRequest(String pURL) throws IOException {
        URL url = new URL(pURL);
        URLConnection request = url.openConnection();
        /* Cloudflare Bypass */
        request.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36 Edg/90.0.818.66");
        request.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        return content.toString();
    }

    public boolean validateKey(String arg) throws IOException {
        try {
            return !Utils.doRequest("https://api.hypixel.net/player?key=" + arg).contains("Invalid API key");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUUID(String uuid) {
        return Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$").matcher(uuid).matches();
    }
}
