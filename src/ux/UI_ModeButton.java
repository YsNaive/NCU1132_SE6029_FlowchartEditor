package ux;

import visual.VisualPanel;
import visual.VisualPanelMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI_ModeButton extends JButton {
    private static final int m_padding = 2;
    private static final int m_radius = 8;
    private Color defaultColor = Color.WHITE;
    private Color hoverColor = new Color(220, 220, 220);
    private Color selectedColor = new Color(240,230,255);
    private final VisualPanel     targetPanel;
    private final VisualPanelMode targetMode;
    public UI_ModeButton(String text, VisualPanel target, VisualPanelMode mode) {
        super(text);
        setFocusPainted(false);
        setBackground(defaultColor);
        setContentAreaFilled(false);
        this.targetPanel = target;
        this.targetMode  = mode;
        // 滑鼠監聽事件
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                targetPanel.SetMode(targetMode);
                setBackground(selectedColor);
                targetPanel.revalidate();
                targetPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
                targetPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
                targetPanel.repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(targetPanel.GetMode() == targetMode)
            g2.setColor(selectedColor);
        else
            g2.setColor(getBackground());
        g2.fillRoundRect(m_padding, m_padding, getWidth() - m_padding * 2, getHeight() - m_padding * 2, m_radius, m_radius);

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
