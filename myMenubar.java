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
    JMenu file, help;
    JMenuItem fileSelect,fileSave,programexit, helpitem;
    //
    ImgPanel imgpanel;
    ImgData imgData;
    OptionPanel optPanel;
    //
    BufferedImage loadImage;
    myMenubar(ImgPanel imgPanel, ImgData imgData, OptionPanel optPanel){
        this.imgpanel = imgPanel;
        this.imgData = imgData;
        this.optPanel = optPanel;
        file = new JMenu("File");
        help = new JMenu("Help");

        fileSelect = new JMenuItem("File Select");
        fileSave = new JMenuItem("File Save");
        programexit = new JMenuItem("Program Exit");
        helpitem = new JMenuItem("Help");

        add(file);
        add(help);

        file.add(fileSelect);
        file.add(fileSave);
        file.addSeparator();
        file.add(programexit);

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
                    e1.printStackTrace();
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