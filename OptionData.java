import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class OptionData{
    private boolean istileMode;
    private int fontSize = 32;
    private float opacity = 0.5f;
    private String font;
    private String text = "Plain Text.";
    private Color color = Color.black;
    private int locX;
    private int locY;
    private int degree = 0;

    OptionData(){}
    OptionData(boolean tilemode, int fontSize, float opacity, String font, String text, Color color,int x, int y, int degree){
        this.istileMode = tilemode;
        this.fontSize = fontSize;
        this.opacity = opacity;
        this.font = font;
        this.text = text;
        this.color = color;
        this.locX = x;
        this.locY = y;
        this.degree = degree;
    }

    //OptionPanel에서 변경할 때 사용되는 메소드들
    public void setTileMode(boolean isTileChecked){ this.istileMode = isTileChecked; }
    public void setFontSize(int fontsize){  this.fontSize = fontsize;   }
    public void setOpacity(float opacity){ this.opacity = opacity;  }
    public void setFont(String font){ this.font = font; }
    public void setText(String text){ 
        if(text.length() == 0)
            this.text = " ";
        else this.text = text; 
    }
    public void setColor(Color color){ this.color = color; }
    public void setlocX(int x){ this.locX = x; }
    public void setlocY(int y){ this.locY = y; }
    public void setDegree(int degree){ this.degree = degree; }
    //현재 데이터 값 반환하는 메소드.
    public boolean getTileMode() { return this.istileMode; }
    public int getFontSize() { return this.fontSize; }
    public float getOpacity() {  return this.opacity; }
    public String getFont() { return this.font; }
    public String getText() { return this.text; }
    public Color getColor() { return this.color; }
    public int getLocX() { return this.locX; }
    public int getLocY() { return this.locY; }
    public int getDegree() { return this.degree; }

    /**
     * 옵션을 세이브하는 메소드 현재 객체의 정보를 config.properties에 저장한다.
     */
    public void optionSave() {
    Properties props = new Properties();
    props.setProperty("tileMode", Boolean.toString(istileMode));
    props.setProperty("fontSize", Integer.toString(fontSize));
    props.setProperty("opacity", Float.toString(opacity));
    props.setProperty("font", font != null ? font : "");
    props.setProperty("text", text);
    props.setProperty("color", colorToString(color)); // Color 객체를 문자열로 변환
    props.setProperty("locX", Integer.toString(locX));
    props.setProperty("locY", Integer.toString(locY));
    props.setProperty("degree", Integer.toString(degree));

    try (FileOutputStream fos = new FileOutputStream("config.properties")) {
            props.store(fos, null);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String colorToString(Color color) {
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
    /** 
     * 옵션을 로드하는 메소드, config.properties에 적혀있는 정보를 로드한다.
    */
    public void optionLoad() {
    Properties props = new Properties();

    try (FileInputStream fis = new FileInputStream("config.properties")) {
        props.load(fis);

        istileMode = Boolean.parseBoolean(props.getProperty("tileMode"));
        fontSize = Integer.parseInt(props.getProperty("fontSize"));
        opacity = Float.parseFloat(props.getProperty("opacity"));
        font = props.getProperty("font");
        text = props.getProperty("text");
        color = stringToColor(props.getProperty("color"));
        locX = Integer.parseInt(props.getProperty("locX"));
        locY = Integer.parseInt(props.getProperty("locY"));
        degree = Integer.parseInt(props.getProperty("degree"));
    } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private Color stringToColor(String value) {
        String[] rgb = value.split(",");
        return new Color(
            Integer.parseInt(rgb[0]),
            Integer.parseInt(rgb[1]),
            Integer.parseInt(rgb[2])
        );
    }

}
