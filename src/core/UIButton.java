package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIButton extends JButton {
    private static final int m_padding = 2;
    private static final int m_radius = 8;
    private Color defaultColor = Color.WHITE;
    private Color hoverColor = new Color(220, 220, 220); // 淺灰色

    public UIButton(String text) {
        super(text);
        setFocusPainted(false);
        setBackground(defaultColor);
        setContentAreaFilled(false);

        // 滑鼠監聽事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(m_padding, m_padding, getWidth() - m_padding * 2, getHeight() - m_padding * 2, m_radius, m_radius);

        // 繪製文字
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(m_padding, m_padding, getWidth() - m_padding * 2, getHeight() - m_padding * 2, m_radius, m_radius);
    }
}
