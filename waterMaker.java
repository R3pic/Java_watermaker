import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 이미지패널, 조작패널, 메뉴바
 */
public class waterMaker extends JFrame{
    JMenuBar menuBar;
    JPanel imgPanel,optPanel;

    waterMaker(){
        super("워터마크 메이커");

        menuBar = new myMenubar();

        imgPanel = new imgPanel();

        add("North",menuBar);
        add("Center", imgPanel);
        
        setVisible(true);
        setBounds(0,0,500,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new waterMaker();        
    }
}

class myMenubar extends JMenuBar{
    JMenu file, help;
    JMenuItem fileselect,programexit, helpitem;
    JFileChooser fileChooser;

    //
    BufferedImage loadImage;
    myMenubar(){
        super();

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
            }
            
        });

    }
    public BufferedImage getloadedImage(){
            return loadImage;
        }

    /**
     * 메뉴바를 누를때 실행되는 메소드, JFilechooser로 파일을 가져옴
     * @return BufferedImage
     */
    private BufferedImage FileSelect(){
        BufferedImage loadImage;
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("User.Downloads")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));

        int ret = fileChooser.showOpenDialog(null);

        if(ret != JFileChooser.APPROVE_OPTION){
            JOptionPane.showMessageDialog(null, "경로를 선택하지 않았습니다.", "Non Path", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        else{
            File selectedFile = fileChooser.getSelectedFile();
            try{
                loadImage = ImageIO.read(selectedFile);
                return loadImage;
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "이미지를 불러오는데 실패했습니다.", "Load Failed.", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
    }
}

/**
 * 이미지를 표시하는 클래스
 * Menu에서 로드한 이미지를 Panel에 표시해야함.
 */
class imgPanel extends JPanel{
    JLabel imgLabel;
    imgPanel(){
        super();
        imgLabel = new JLabel();
        imgLabel.setIcon(null);
    }

    public void updateImage(BufferedImage img){
        Image image = img;
        ImageIcon icon = new ImageIcon(image);
        imgLabel.setIcon(icon);
    }
}
