package core;

import javax.swing.*;
import java.awt.*;

public class ShapeScene extends JPanel {
    public ShapeScene() { }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 設定抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // 自訂繪製內容 (例如畫一個矩形)
        g2d.setColor(Color.RED);
        g2d.fillRect(50, 50, 100, 100);
    }
}
