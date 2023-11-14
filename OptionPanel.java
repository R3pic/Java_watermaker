import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 사용자가 상호작용할 수 있는 패널.
 */
class OptionPanel extends JPanel{
    JSpinner fontSize_Spinners;
    JTextField inputText_Field;
    JSlider opacitySlider;
    JComboBox<String> chooseFont;
    JColorChooser jColorChooser;
    JButton colorChooser;
    JCheckBox tilemodbCheckBox;
    JSpinner loc_xtf,loc_ytf;
    //데이터
    ImgData imgData;
    ImgPanel imgPanel;
    BufferedImage bufimg;
    int fontsize = 32;
    float opacity = 0.5f;
    String font;
    String text = "Plain Text.";
    Color selectColor = Color.black;
    boolean isTileChecked = false;
    int loc_x,loc_y;

    OptionPanel(ImgPanel imgPanel, ImgData imgData){
        this.imgData = imgData;
        this.imgPanel = imgPanel;
        bufimg = (BufferedImage) imgData.getImage();
        setBorder(new LineBorder(Color.black));
        setLayout(new GridLayout(30,3));
        //폰트 선택 박스 만들기
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        chooseFont = new JComboBox<>(fontNames);
        chooseFont.setSelectedIndex(1);
        //폰트크기, 
        fontSize_Spinners = new JSpinner();
        fontSize_Spinners.setValue(32);
        //투명도 선택
        opacitySlider = new JSlider(0,100);
        //컬러 선택
        colorChooser = new JButton("색상 선택");
        //텍스트 입력필드
        inputText_Field = new JTextField("Plain Text.", 18);
        //타일모드 버튼
        tilemodbCheckBox = new JCheckBox("타일 모드");
        //단일모드일때 X,Y변경
        loc_xtf = new JSpinner();
        loc_ytf = new JSpinner();
        loc_xtf.setValue(0);
        loc_ytf.setValue(0);

        add(new JLabel("폰트선택"));
        add(chooseFont);
        add(new JLabel("폰트 크기"));
        add(fontSize_Spinners);
        add(new JLabel("폰트 투명도"));
        add(opacitySlider);
        add(new JLabel("색상 선택"));
        add(colorChooser);
        add(new JLabel("텍스트 입력"));
        add(inputText_Field);
        add(tilemodbCheckBox);
        add(new JLabel("X"));
        add(loc_xtf);
        add(new JLabel("Y"));
        add(loc_ytf);

        colorChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                colorChooser.setBackground(selectColor = pickColor());
                imgData.setColor(selectColor);
                imgPanel.updateImage();
            }
        });
        chooseFont.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    font = chooseFont.getSelectedItem().toString();
                    imgData.setFont(font);
                    imgPanel.updateImage();
                }
            }
        });
        loc_xtf.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                loc_x = (int) loc_xtf.getValue();
                imgData.setloc(loc_x, loc_y);
                imgPanel.updateImage();
            }
        });
        loc_ytf.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                loc_y = (int) loc_ytf.getValue();
                imgData.setloc(loc_x, loc_y);
                imgPanel.updateImage();
            }
        });
        inputText_Field.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void removeUpdate(DocumentEvent e) {
                text = inputText_Field.getText();
                imgData.setText(text);
                imgPanel.updateImage();
            }
            public void insertUpdate(DocumentEvent e) {
                text = inputText_Field.getText();
                imgData.setText(text);
                imgPanel.updateImage();
            }
        });
        fontSize_Spinners.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                fontsize = (int) fontSize_Spinners.getValue();
                imgData.setFontSize(fontsize);
                imgPanel.updateImage();
            }
        });
        opacitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                opacity = (float)opacitySlider.getValue() / 100f;
                imgData.setOpacity(opacity);
                imgPanel.updateImage();
            }
        });
        tilemodbCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    isTileChecked = true;
                    imgData.setTileMode(isTileChecked);
                    imgPanel.updateImage();
                }
                else{
                    isTileChecked = false;
                    imgData.setTileMode(isTileChecked);
                    imgPanel.updateImage();
                }
            }
        });
    }


    private Color pickColor(){
        JColorChooser chooser = new JColorChooser();
        chooser.getSelectionModel().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e){
                selectColor = chooser.getColor();
            }
        });
        JDialog dialog = JColorChooser.createDialog(null, "Choose Color", true, chooser, null, null);
        dialog.setVisible(true);
            return selectColor;
    }

}