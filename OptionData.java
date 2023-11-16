import java.awt.Color;

public class OptionData {
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
}
