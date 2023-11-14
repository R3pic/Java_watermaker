import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 이미지를 표시하는 클래스
 * ImgData 클래스에 존재하는 이미지 데이터를 Panel에 표시해줌.
 */
class ImgPanel extends JPanel{
    //이미지 패널
    JPanel imgPanel;
    JLabel imgLabel;
    //이미지 가로세로 라벨 패널
    JPanel imgwhpPanel;
    JLabel imgwhLabel;
    //데이터
    ImgData imgData;
    int originalWidth;
    int originalHeight;
    ImgPanel(ImgData imgData){
        this.imgData = imgData;
        setLayout(new BorderLayout());

        imgPanel = new JPanel();
        imgLabel = new JLabel();
        imgLabel.setIcon(null);
        imgLabel.setHorizontalAlignment(JLabel.CENTER);
        imgPanel.add(imgLabel);

        imgwhpPanel = new JPanel();
        imgwhLabel = new JLabel();
        imgwhpPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        imgwhpPanel.add(imgwhLabel);

        imgPanel.setBackground(Color.darkGray);
        imgwhpPanel.setBackground(Color.lightGray);

        add(imgPanel, BorderLayout.CENTER);
        add(imgwhpPanel, BorderLayout.SOUTH);
    }
    /**
     * ImgData에 존재하는 이미지를 가져옴.
     */
    public void updateImage(){
        BufferedImage img = imgData.getImage();
        Image image;
            if(img.getWidth() > 1920 || img.getHeight() > 1080)
                image = imageResize(img);
            else
                image = img;
        ImageIcon icon = new ImageIcon(image);
        imgLabel.setIcon(icon);
        originalWidth = imgData.getImage().getWidth();
        originalHeight = imgData.getImage().getHeight();
        imgwhLabel.setText(originalWidth+" X "+originalHeight);
    }

    /**
     * 이미지를 리사이징해 반환함.
     * @param BufferedImage original
     * @return BufferedImage 리사이즈된 이미지
     */
    private BufferedImage imageResize(BufferedImage original){
        int frWidth = getParent().getSize().width;
        int frHeight = getParent().getSize().height;
        int maxWidth = frWidth;
        int maxHeight = frHeight;

        double aspectRatio = (double) originalWidth / originalHeight;
        int newWidth = maxWidth;
        int newHeight = (int) (maxWidth / aspectRatio);

        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) (maxHeight * aspectRatio);
        }
        Image tmp = original.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        resized.getGraphics().drawImage(tmp, 0, 0, null);
        return resized;
    }
}
