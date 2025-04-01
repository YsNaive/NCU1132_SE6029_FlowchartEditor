package manager;

import core.Pair;
import ux.LabelStyleEditorDialog;
import visual.LabelElement;
import visual.VisualElement;
import visual.VisualPanel;
import ux.UI_ModeButton;
import visual.VisualPanelMode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public final class UIManager {
    // it's a static class, create instance is not allow
    private UIManager(){  }

    private static JFrame m_frame;
    private static Container m_container;
    private static VisualPanel m_visualPanel;
    private static JMenuBar m_menuBar;
    private static LabelStyleEditorDialog m_labelStyleEditor;
    private static Font customFont = new Font("Microsoft JhengHei UI", Font.BOLD, 16);

    public static void Init(){
        m_frame = new JFrame("Editor");
        m_container = m_frame.getContentPane();
        m_container.setLayout(new BorderLayout());
        m_visualPanel = new VisualPanel();
        initMenu();

        // 建立左側工具列，按鈕樣式與圖片相似
        JPanel      toolPanel   = new JPanel();

        toolPanel.setLayout(new GridLayout(6, 1, 3, 3));
        toolPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        toolPanel.setBackground(Color.LIGHT_GRAY);
        List<Pair<String, VisualPanelMode>> toolModes = Arrays.asList(
                new Pair<>("Select"         , VisualPanelMode.Select        ),
                new Pair<>("Association"    , VisualPanelMode.Association   ),
                new Pair<>("Generalization" , VisualPanelMode.Generalization),
                new Pair<>("Composition"    , VisualPanelMode.Composition   ),
                new Pair<>("Rect"           , VisualPanelMode.Rect          ),
                new Pair<>("Oval"           , VisualPanelMode.Oval          )
        );

        for (Pair<String, VisualPanelMode> toolMode : toolModes) {
            UI_ModeButton button = new UI_ModeButton(toolMode.key, m_visualPanel, toolMode.value); // 傳入名稱與模式
            button.setFont(customFont);  // 設定按鈕字體
            toolPanel.add(button);
        }
        m_container.add(toolPanel, BorderLayout.WEST);

        // 建立畫布區域，添加邊框
        m_visualPanel.setBackground(Color.WHITE);
        m_visualPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        m_container.add(m_visualPanel, BorderLayout.CENTER);

        // 設定視窗屬性
        m_frame.setSize(600, 450);
        m_frame.setLocation(600, 250);
        m_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        m_frame.setVisible(true);

        m_labelStyleEditor = new LabelStyleEditorDialog(m_frame);
    }

    private static void initMenu(){
        m_menuBar= new JMenuBar();
        m_frame.setJMenuBar(m_menuBar);
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        fileMenu.setFont(customFont);
        editMenu.setFont(customFont);
        m_menuBar.add(fileMenu);
        m_menuBar.add(editMenu);

        JMenuItem labelStyleMenuItem = new JMenuItem("Edit Label");
        labelStyleMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(m_visualPanel.selecting.isEmpty())
                    return;
                LabelElement target = m_visualPanel.selecting.get(0).Q(LabelElement.class);
                if(target == null)
                    return;
                m_labelStyleEditor.Open(target);
            }
        });
        JMenuItem groupMenuItem = new JMenuItem("Group");
        groupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_visualPanel.GroupSelecting();
            }
        });
        JMenuItem ungroupMenuItem = new JMenuItem("UnGroup");
        ungroupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_visualPanel.UnGroupSelecting();
            }
        });
        editMenu.add(labelStyleMenuItem);
        editMenu.add(groupMenuItem);
        editMenu.add(ungroupMenuItem);
    }
}
