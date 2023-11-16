import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 이미지를 가공, 보관하는 클래스
 */
class ImgData {
    private BufferedImage oriImage = null;
    OptionData optionData;

    ImgData(OptionData optionData){
        this.optionData = optionData;
    }

    /*
     * 원본 이미지를 저장함
     */
    public void setImage(BufferedImage img){
        oriImage = img;
    }

    /*
     * 워터마크와 합성한 이미지를 리턴함.
     */
    public BufferedImage getImage() {
        if(oriImage == null)
            return null;
        return mergeImage();
    }

    
    /**
     * isTileChecked의 값에 따라 일반 이미지 또는 타일이미지와 로드된 이미지를 결합한 결과 반환
     * @return BufferedImage
     */
    private BufferedImage mergeImage(){
        BufferedImage waterMark;
        BufferedImage copyImage;
        copyImage = copyImage(oriImage);
        if(optionData.getTileMode() == false)
            waterMark = textToImage();
        else
            waterMark = textToTiledImage();
        
        Graphics2D g2d = copyImage.createGraphics();
        g2d.drawImage(waterMark, optionData.getLocX(), optionData.getLocY(), null);
        g2d.dispose();
        return copyImage;
    }

    private BufferedImage copyImage(BufferedImage original){
        BufferedImage copy = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        Graphics2D g2d = copy.createGraphics();
        g2d.drawImage(original, 0, 0, null);
        g2d.dispose();
        return copy;
    }
    /**
     * 텍스트를 이미지화 하는 메소드
     * @return BufferedImage
     */
    private BufferedImage textToImage(){
        Font cfont = new Font(optionData.getFont(), Font.PLAIN, optionData.getFontSize());

        // 텍스트 크기 측정을 위한 임시 그래픽스 컨텍스트
        BufferedImage tempImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = tempImg.createGraphics();
        g2d.setFont(cfont);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(optionData.getText());
        int textHeight = fm.getHeight();
        g2d.dispose();

        // 회전에 필요한 이미지 크기 계산
        double radians = Math.toRadians(optionData.getDegree());
        double sin = Math.abs(Math.sin(radians)), cos = Math.abs(Math.cos(radians));
        int width = (int) Math.floor(textWidth * cos + textHeight * sin);
        int height = (int) Math.floor(textHeight * cos + textWidth * sin);

        // 회전된 텍스트를 위한 이미지 생성
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, optionData.getOpacity()));
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        // 회전 설정
        AffineTransform at = AffineTransform.getRotateInstance(radians, width / 2.0, height / 2.0);
        at.translate((width - textWidth) / 2.0, (height - textHeight) / 2.0 + fm.getAscent());
        g2d.setTransform(at);

        // 텍스트 렌더링
        g2d.setFont(cfont);
        g2d.setColor(optionData.getColor());
        g2d.drawString(optionData.getText(), 0, 0);
        g2d.dispose();

        return img;
    }

    /**
     * 이미지화된 텍스트를 타일화 시키는 메소드
     * @return BufferedImage
     */
    private BufferedImage textToTiledImage() {
            // 이미지 불러오기
            Image image = textToImage();
            int tilePadding = 3;
            // 타일을 그릴 영역 지정
            Rectangle2D bounds = new Rectangle(0, 0, oriImage.getWidth() * 2, oriImage.getHeight() * 2);
            // 타일을 그릴 그래픽 생성
            BufferedImage outputImage = new BufferedImage((int)bounds.getWidth(), (int)bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = outputImage.createGraphics();
            int x = (int) bounds.getX();
            int y = (int) bounds.getY();
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            int cols = (int) Math.ceil(bounds.getWidth() / (w + tilePadding));
            int rows = (int) Math.ceil(bounds.getHeight() / (h + tilePadding));
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    g2.drawImage(image, x, y, x + w, y + h, 0, 0, w, h, null);
                    x += w + tilePadding;
                }
                y += h - tilePadding;
                x = (int) bounds.getX();
            }
            return outputImage;
    }

}