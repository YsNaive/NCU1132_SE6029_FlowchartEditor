package manager;

import core.ShapeScene;
import core.UIButton;

import javax.swing.*;
import java.awt.*;

public final class UIManager {
    // it's a static class, create instance is not allow
    private UIManager(){}

    private static JFrame m_frame;
    private static Container m_container;

    public static void Init(){
        m_frame = new JFrame("Editor");
        m_container = m_frame.getContentPane();
        m_container.setLayout(new BorderLayout());

        // 設定字體
        Font customFont = new Font("Microsoft JhengHei UI", Font.BOLD, 14);

        // 建立選單列並設置字體
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        fileMenu.setFont(customFont);
        editMenu.setFont(customFont);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        m_frame.setJMenuBar(menuBar);

        // 建立左側工具列，按鈕樣式與圖片相似
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new GridLayout(6, 1, 3, 3));
        toolPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        toolPanel.setBackground(Color.LIGHT_GRAY);
        String[] tools = {"Select", "Association", "Generalization", "Composition", "Rect", "Oval"};
        for (String tool : tools) {
            UIButton button = new UIButton(tool);
            button.setFont(customFont);  // 設定按鈕字體
            toolPanel.add(button);
        }
        m_container.add(toolPanel, BorderLayout.WEST);

        // 建立畫布區域，添加邊框
        JPanel canvas = new ShapeScene();
        canvas.setBackground(Color.WHITE);
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        m_container.add(canvas, BorderLayout.CENTER);

        // 設定視窗屬性
        m_frame.setSize(600, 450);
        m_frame.setLocation(600, 250);
        m_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        m_frame.setVisible(true);
    }
}
