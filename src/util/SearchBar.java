
package util;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
 

public class SearchBar extends JIconTextField implements FocusListener {
 
    private String textWhenNotFocused;
 
    public SearchBar() {
        super();
        this.textWhenNotFocused = "Cerca...";
        this.addFocusListener(this);
    }
 
    public String getTextWhenNotFocused() {
        return this.textWhenNotFocused;
    }
 
    public void setTextWhenNotFocused(String newText) {
        this.textWhenNotFocused = newText;
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        if (!this.hasFocus() && this.getText().equals("")) {
            int width = this.getWidth();
            int height = this.getHeight();
            Font prev = g.getFont();
            Font italic = prev.deriveFont(Font.ITALIC);
            Color prevColor = g.getColor();
            g.setFont(italic);
            g.setColor(Color.GRAY);//UIManager.getColor("textInactiveText")); colore scritta cerca
            int h = g.getFontMetrics().getHeight();
            int textBottom = (height - h) / 2 + h - 4;
            int x = this.getInsets().left;
            Graphics2D g2d = (Graphics2D) g;
            RenderingHints hints = g2d.getRenderingHints();
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.drawString(textWhenNotFocused, x, textBottom);
            g2d.setRenderingHints(hints);
            g.setFont(prev);
            g.setColor(prevColor);
        }
 
    }
 
    //FocusListener implementation:
    public void focusGained(FocusEvent e) {
        this.repaint();
    }
 
    public void focusLost(FocusEvent e) {
        this.repaint();
    }
}