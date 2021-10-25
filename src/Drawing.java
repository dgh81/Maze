//import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.OptionalInt;

// popup - dialogboks:
//JOptionPane.showMessageDialog(null,"test");

// Hvis man ændrer Frame til JFrame slettes den forrige position af den røde tile ikke ?!:
public class Drawing extends Frame implements KeyListener {

    // tile størrelse - dx og dy SKAL være større end 30, men må gerne være forskellige. Vælg x-y kombinationer som går op i vindue størrelse (pt 600):
    int dx = 50;
    int dy = 50;
    // rød tile start position:
    int x = dx * 5;
    int y = dy * 1;

    // x-y koordinater til væg-tiles. Udbyg arrays for at bygge vægge i din maze:
    int[] xKoordinaterTiles = {1*dx,2*dx,3*dx,8*dx,9*dx,10*dx,11*dx,12*dx,12*dx,12*dx,4*dx,12*dx};
    int[] yKoordinaterTiles = {1*dy,1*dy,1*dy,8*dy,8*dy,8*dy,8*dy,8*dy,9*dy,10*dy,4*dy,12*dy};
    // variabel til test af fremtidig position:
    int nyPosX = 0;
    int nyPosY = 0;
    // x-y koordinater for målet:
    int goalPositionX = dx*12;
    int goalPositionY = dy*11;

    int windowSizeX = Arrays.stream(xKoordinaterTiles).max().getAsInt() + dx;
    int windowSizeY = Arrays.stream(yKoordinaterTiles).max().getAsInt() + dy;
    // vindue størrelse:
    //int windowSizeX = 600;
    //int windowSizeY = 600;
    // vindue padding:
    int windowPadding = 100;
    //
    int rectSizeX = windowSizeX - dx;
    int rectSizeY = windowSizeY - dy;

    public Drawing() {
        setTitle("Test");
        setSize(windowSizeX + windowPadding,windowSizeY + windowPadding);
        // tilføj key event listener:
        addKeyListener(this);
        // tilføj vindue closing event listener:
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        // tilføj vindue resize event listener:
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                // Resize Maze border:
                //windowSizeX = getWidth()-dx*2;
                //windowSizeY = getHeight()-dy*2;
                //repaint();
            }
        });
    }

    public void opretMazeTiles(Graphics g, int [] xKoordinaterTiles, int [] yKoordinaterTiles) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < xKoordinaterTiles.length; i++) {
            g.fillRect(xKoordinaterTiles[i],yKoordinaterTiles[i],dx,dy);
        }
    }

    public void paint(Graphics g){
        // tegn label - husk at labels har x værdi og y værdi sat til nederste venstre hjørne... altså labels vokser i størrelse fra bunden af:
        g.setColor(Color.BLACK);
        g.drawString("Find den blå firkant!",dx,dy);
        // tegn kant:
        g.setColor(Color.BLACK);
        g.drawRect(dx,dy,rectSizeX,rectSizeY);
        // tegn rød player tile:
        g.setColor(Color.RED);
        g.fillRect(x,y,dx,dy);
        // tegn blå mål tile
        g.setColor(Color.BLUE);
        g.fillRect(goalPositionX,goalPositionY,dx,dy);
        // vis rød tile er i mål:
        boolean goalReached = (x == goalPositionX && y == goalPositionY);
        if (goalReached) {
            JOptionPane.showMessageDialog(null,"Du klarede det!: ");
        }
        // tegn alle vægge:
        opretMazeTiles(g, xKoordinaterTiles,yKoordinaterTiles);
    }

/*
    public boolean goalReached() {
        if (x == goalPositionX && y == goalPositionY) {
            return true;
        } else {
            return false;
        }
    }
    ERSTATTET AF:
    boolean goalReached = (x == goalPositionX && y == goalPositionY);
*/

    public static void main(String[] args) {
        Drawing maze = new Drawing();
        maze.show(); // alternativ?
    }

    // bruges ved tryk ned:
    @Override
    public void keyPressed(KeyEvent e) {
        // sæt nye tile-koordinater ved piletryk:
        switch (e.getKeyCode()) {

            case KeyEvent.VK_UP:
                // stop når rød tile når den nederste kant af grå tile (eller kanten af den sorte ramme):
                // der sker ikke noget med x når vi går til op/ned:
                nyPosX = x;
                nyPosY = y - dy;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean upHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        upHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        upHasTile = true;
                    }
                }
                if (upHasTile) {
                    break;
                } else {
                    y = y - dy;
                }
                break;

            case KeyEvent.VK_DOWN:
                // stop når rød tile når den øverste kant af grå tile (eller kanten af den sorte ramme):
                // der sker ikke noget med x når vi går til op/ned:
                nyPosX = x;
                nyPosY = y + dy;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean downHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        downHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        downHasTile = true;
                    }
                }
                if (downHasTile) {
                    break;
                } else {
                    y = y + dy;
                }
                break;

            case KeyEvent.VK_LEFT:
                // stop når rød tile når den højre kant af grå tile (eller kanten af den sorte ramme):
                nyPosX = x - dx;
                // der sker ikke noget med y når vi går højre/venstre:
                nyPosY = y;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean leftHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        leftHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        leftHasTile = true;
                    }
                }
                if (leftHasTile) {
                    break;
                } else {
                    x = x - dx;
                }
                break;

            case KeyEvent.VK_RIGHT:
                // stop når rød firkant når venstre kant af grå tile (eller kanten af den sorte ramme):
                nyPosX = x + dx;
                // der sker ikke noget med y når vi går højre/venstre:
                nyPosY = y;
                System.out.println("Ny xPos: " + nyPosX + ". Ny yPos: " + nyPosY);
                boolean rightHasTile = false;
                for (int i = 0; i < xKoordinaterTiles.length; i++) {
                    if (nyPosX == xKoordinaterTiles[i] && nyPosY == yKoordinaterTiles[i]) {
                        rightHasTile = true;
                    } else if(nyPosX > rectSizeX || nyPosY > rectSizeY || nyPosX == 0 || nyPosY == 0) {
                        rightHasTile = true;
                    }
                }
                if (rightHasTile) {
                    break;
                } else {
                    x = x + dx;
                }
                break;
        }
        // når nye koordinater er sat, recall paint:
        repaint();
    }

    // bruges ved tryk ned og slip:
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // bruges ved slip:
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
