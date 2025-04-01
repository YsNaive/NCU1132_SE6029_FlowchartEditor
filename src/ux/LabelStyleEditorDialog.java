package ux;

import core.ShapeType;
import visual.LabelElement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LabelStyleEditorDialog extends JDialog {
    private JTextField nameField;
    private JButton colorButton;
    private JComboBox<ShapeType> shapeComboBox;
    private JSpinner fontSizeSpinner;
    private Color selectedColor = Color.BLACK;
    private LabelElement target;

    // 構造函數
    public LabelStyleEditorDialog(JFrame parent) {
        super(parent, "Edit Label Style", true);

        // 創建主面板，並設置 15px padding
        JPanel contentPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // 設置內邊距 (上, 左, 下, 右)

        contentPanel.add(new JLabel("Label Name:"));
        nameField = new JTextField();
        contentPanel.add(nameField);

        contentPanel.add(new JLabel("Font Size:"));
        fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 8, 72, 1));
        contentPanel.add(fontSizeSpinner);

        contentPanel.add(new JLabel("Shape Type:"));
        shapeComboBox = new JComboBox<>(ShapeType.values());
        contentPanel.add(shapeComboBox);

        contentPanel.add(new JLabel("Label Color:"));
        colorButton = new JButton("Choose Color");
        colorButton.addActionListener(e -> chooseColor());
        contentPanel.add(colorButton);

        // 創建按鈕面板 (使用 GridLayout 讓按鈕 50/50 均分)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, 10px 間距
        buttonPanel.setBorder(new EmptyBorder(10, 15, 15, 15)); // 設置內邊距 (上, 左, 下, 右)

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        okButton.addActionListener(e -> {
            target.text = nameField.getText();
            target.font = new Font(target.font.getFontName(), target.font.getStyle(), (int) fontSizeSpinner.getValue());
            target.bgShape = (ShapeType) shapeComboBox.getSelectedItem();
            target.bgColor = selectedColor;
            target.MarkDirty();
            target = null;
            dispose();
        });

        cancelButton.addActionListener(e -> {
            target = null;
            dispose();
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // 設置主佈局
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(350, 250);
        setLocationRelativeTo(parent);
    }

    public void Open(LabelElement target) {
        this.target = target;
        setDefault(target.text, target.font.getSize(), target.bgColor, target.bgShape);
        this.setVisible(true);
    }

    private void setDefault(String label, int fontSize, Color color, ShapeType shape) {
        nameField.setText(label);
        fontSizeSpinner.setValue(fontSize);
        selectedColor = color;
        colorButton.setBackground(color);
        shapeComboBox.setSelectedItem(shape);
    }

    private void chooseColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose Label Color", selectedColor);
        if (newColor != null) {
            selectedColor = newColor;
            colorButton.setBackground(newColor);
        }
    }
}
