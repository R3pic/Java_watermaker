import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 * 메인프레임
 * 이미지패널, 조작패널, 메뉴바를 가지고 있음.
 */
public class waterMaker extends JFrame{
    JMenuBar menuBar;
    ImgPanel imgPanel;
    OptionPanel optPanel;
    OptionData optionData;
    ImgData imgData;

    waterMaker(){
        super("워터마크 메이커");
        optionData = new OptionData();
        imgData = new ImgData(optionData);
        imgPanel = new ImgPanel(imgData);
        optPanel = new OptionPanel(imgPanel,imgData, optionData);
        menuBar = new myMenubar(imgPanel, imgData, optPanel, optionData);

        add("North",menuBar);
        add("Center", imgPanel);
        add("East", optPanel);
        
        if(imgData.getImage() == null)
            optPanel.setVisible(false);
        setVisible(true);
        setBounds(0,0,1400,1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }
    public static void main(String[] args) {
        new waterMaker();        
    }
}