import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 이미지를 가공, 보관하는 클래스
 */
class ImgData {
    private BufferedImage oriImage = null;
    private boolean isTileChecked;
    int fontsize = 32;
    float opacity = 0.5f;
    String font;
    String text = "Plain Text.";
    Color selectColor = Color.black;
    int loc_x,loc_y;

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

    //OptionPanel에서 변경할 때 사용되는 메소드들
    public void setTileMode(boolean isTileChecked){ this.isTileChecked = isTileChecked; }
    public void setFontSize(int fontsize){  this.fontsize = fontsize;   }
    public void setOpacity(float opacity){ this.opacity = opacity;  }
    public void setFont(String font){ this.font = font; }
    public void setText(String text){ 
        if(text.length() == 0)
            this.text = " ";
        else this.text = text; 
    }
    public void setColor(Color color){ this.selectColor = color; }
    public void setloc(int x, int y){ this.loc_x = x; this.loc_y = y; }
    /**
     * isTileChecked의 값에 따라 일반 이미지 또는 타일이미지와 로드된 이미지를 결합한 결과 반환
     * @return BufferedImage
     */
    private BufferedImage mergeImage(){
        BufferedImage waterMark;
        BufferedImage copyImage;
        copyImage = copyImage(oriImage);
        if(isTileChecked == false)
            waterMark = textToImage();
        else
            waterMark = textToTiledImage();
        
        Graphics2D g2d = copyImage.createGraphics();
        g2d.drawImage(waterMark, loc_x, loc_y, null);
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
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font cfont = new Font(font, Font.PLAIN, fontsize);
        g2d.setFont(cfont);
        java.awt.FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(cfont);
        fm = g2d.getFontMetrics();
        g2d.setColor(selectColor);
        g2d.drawString(text, 0, fm.getAscent());
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