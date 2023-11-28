import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * 메뉴바 클래스.
 * 메뉴들을 가지고 있고, FileSelect을 누르면 이미지를 불러옴.
 */
class myMenubar extends JMenuBar{
    JMenu file, help, tool;
    JMenuItem fileSelect,fileSave,programexit, helpitem, optionSave, optionLoad;
    //
    ImgPanel imgpanel;
    ImgData imgData;
    OptionPanel optPanel;
    OptionData optionData;
    //
    BufferedImage loadImage;
    
    myMenubar(ImgPanel imgPanel, ImgData imgData, OptionPanel optPanel, OptionData optionData){
        this.imgpanel = imgPanel;
        this.imgData = imgData;
        this.optPanel = optPanel;
        this.optionData = optionData;
        file = new JMenu("File");
        help = new JMenu("Help");
        tool = new JMenu("Tool");

        fileSelect = new JMenuItem("File Select");
        fileSave = new JMenuItem("File Save");
        programexit = new JMenuItem("Program Exit");
        optionSave = new JMenuItem("Option Save");
        optionLoad = new JMenuItem("Option Load");
        helpitem = new JMenuItem("Help");

        add(file);
        add(tool);
        add(help);

        file.add(fileSelect);
        file.add(fileSave);
        file.addSeparator();
        file.add(programexit);

        tool.add(optionSave);
        tool.add(optionLoad);

        help.add(helpitem);

        fileSelect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                loadImage = FileSelect();
                if(loadImage != null){
                    imgData.setImage(loadImage);
                    imgPanel.updateImage();
                    optPanel.setVisible(true);
                }
            }
        });
        fileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileSave();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, "로드된 이미지가 없습니다.");
                }
            }
        });
        programexit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        optionSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(imgData.getImage() == null){
                    JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
                    return;
                }
                else{
                    optionData.optionSave();
                    JOptionPane.showMessageDialog(null, "현재 옵션을 저장하였습니다.");
                }
            }
        });
        optionLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(imgData.getImage() == null){
                    JOptionPane.showMessageDialog(null, "이미지가 로드되지 않았습니다.");
                    return;
                }
                else{
                    optionData.optionLoad();
                    optPanel.SyncOption(optionData);
                }
            }
        });
    }

    /**
     * 메뉴바를 누를때 실행되는 메소드, JFilechooser로 파일을 가져옴
     * @return BufferedImage
     */
    private BufferedImage FileSelect(){
        BufferedImage loadImage;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        int ret = fileChooser.showOpenDialog(null);

        if(ret == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            try{
                loadImage = ImageIO.read(selectedFile);
                return loadImage;
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "이미지를 불러오는데 실패했습니다.", "Load Failed.", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        else{
            return null;
        }
    }

    private void FileSave() throws IOException{
        BufferedImage image = imgData.getImage();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int ret = fileChooser.showOpenDialog(null);

        if(ret == JFileChooser.APPROVE_OPTION){
            try {
                File savepath = fileChooser.getSelectedFile();
                File outputFile = new File(savepath, "Output.png");
                ImageIO.write(image, "png", outputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}