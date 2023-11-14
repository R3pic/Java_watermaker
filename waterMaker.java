import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;


// TODO
// 먼저 imgData에서는 받아낸 이미지를 가공함(outputimg라는 변수에 저장하고 get하면 그변수 리턴)
// 옵션패널에서 조정을 하면 imgdata에 값을 넘김.
// imgdata

/**
 * 메인프레임
 * 이미지패널, 조작패널, 메뉴바를 가지고 있음.
 */
public class waterMaker extends JFrame{
    JMenuBar menuBar;
    ImgPanel imgPanel;
    OptionPanel optPanel;
    ImgData imgData;

    waterMaker(){
        super("워터마크 메이커");

        imgData = new ImgData();
        imgPanel = new ImgPanel(imgData);
        optPanel = new OptionPanel(imgPanel,imgData);
        menuBar = new myMenubar(imgPanel, imgData, optPanel);

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