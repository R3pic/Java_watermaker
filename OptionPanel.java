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
    JLabel opacityLabel;
    JComboBox<String> chooseFont;
    JColorChooser jColorChooser;
    JButton colorChooser;
    JCheckBox tilemodbCheckBox;
    JSpinner loc_xtf,loc_ytf;
    JSlider degreeSlider;
    //데이터
    ImgData imgData;
    ImgPanel imgPanel;
    BufferedImage bufimg;
    OptionData optionData;

    OptionPanel(ImgPanel imgPanel, ImgData imgData, OptionData optionData){
        jColorChooser = new JColorChooser();
        this.imgData = imgData;
        this.imgPanel = imgPanel;
        this.optionData = optionData;
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
        opacityLabel = new JLabel("폰트 투명도     "+opacitySlider.getValue()+"%");
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
        //각도 슬라이더
        degreeSlider = new JSlider(0, 360);
        degreeSlider.setMajorTickSpacing(60);
        degreeSlider.setMinorTickSpacing(10);
        degreeSlider.setPaintTicks(true);

        add(new JLabel("폰트선택"));
        add(chooseFont);
        add(new JLabel("폰트 크기"));
        add(fontSize_Spinners);
        add(opacityLabel);
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
        add(new JLabel("텍스트 각도"));
        add(degreeSlider);

        colorChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color color = pickColor();
                colorChooser.setBackground(color);
                optionData.setColor(color);
                imgPanel.updateImage();
            }
        });
        chooseFont.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    String font = chooseFont.getSelectedItem().toString();
                    optionData.setFont(font);
                    imgPanel.updateImage();
                }
            }
        });
        loc_xtf.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int loc_x = (int) loc_xtf.getValue();
                optionData.setlocX(loc_x);
                imgPanel.updateImage();
            }
        });
        loc_ytf.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int loc_y = (int) loc_ytf.getValue();
                optionData.setlocY(loc_y);
                imgPanel.updateImage();
            }
        });
        inputText_Field.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {}
            public void removeUpdate(DocumentEvent e) {
                optionData.setText(inputText_Field.getText());
                imgPanel.updateImage();
            }
            public void insertUpdate(DocumentEvent e) {
                optionData.setText(inputText_Field.getText());
                imgPanel.updateImage();
            }
        });
        fontSize_Spinners.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                optionData.setFontSize((int) fontSize_Spinners.getValue());
                imgPanel.updateImage();
            }
        });
        opacitySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                float opacity = (float)opacitySlider.getValue() / 100f;
                optionData.setOpacity(opacity);
                opacityLabel.setText("폰트 투명도     "+opacitySlider.getValue()+"%");
                imgPanel.updateImage();
            }
        });
        degreeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int degree = degreeSlider.getValue();
                optionData.setDegree(degree);
                imgPanel.updateImage();
            }
        });
        tilemodbCheckBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    optionData.setTileMode(true);
                    imgPanel.updateImage();
                }
                else{
                    optionData.setTileMode(false);
                    imgPanel.updateImage();
                }
            }
        });
    }

    private Color pickColor(){
        
        // chooser.getSelectionModel().addChangeListener(new ChangeListener() {
        //     public void stateChanged(ChangeEvent e){
        //         Color selectColor = chooser.getColor();
        //     }
        // });
        JDialog dialog = JColorChooser.createDialog(null, "Choose Color", true, jColorChooser, null, null);
        dialog.setVisible(true);
        
        return jColorChooser.getColor();
    }

    /*
     * 옵션데이터로 현재패널에 연동하는 메소드
     */
    public void SyncOption(OptionData optionData) {
        fontSize_Spinners.setValue(optionData.getFontSize());
        inputText_Field.setText(optionData.getText());
        opacitySlider.setValue((int) (optionData.getOpacity() * 100));
        opacityLabel.setText("폰트 투명도     "+String.valueOf((int) (optionData.getOpacity() * 100)));
        chooseFont.setSelectedItem(optionData.getFont());
        jColorChooser.setColor(optionData.getColor());
        colorChooser.setBackground(optionData.getColor());
        tilemodbCheckBox.setSelected(optionData.getTileMode());
        loc_xtf.setValue(optionData.getLocX());
        loc_ytf.setValue(optionData.getLocY());
        degreeSlider.setValue(optionData.getDegree());
    }
}