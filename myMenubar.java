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
    JMenuItem fileselect,programexit, helpitem;
    JFileChooser fileChooser;
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

        fileselect = new JMenuItem("File Select");
        programexit = new JMenuItem("Program Exit");
        helpitem = new JMenuItem("Help");

        add(file);
        add(help);

        file.add(fileselect);
        file.addSeparator();
        file.add(programexit);

        help.add(helpitem);

        fileselect.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                loadImage = FileSelect();
                if(loadImage != null){
                    imgData.setImage(loadImage);
                    imgPanel.updateImage();
                    optPanel.setVisible(true);
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
        fileChooser = new JFileChooser();
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
}