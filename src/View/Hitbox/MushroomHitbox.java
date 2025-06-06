package View.Hitbox;
import Model.Shroomer.*;
import View.Drawable.DrawableTexture;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

public class MushroomHitbox extends Hitbox{
    private Mushroom mushroom;
    private Point centerPoint;
    private String mushroomType;
    private int width;
    public MushroomHitbox(Mushroom mushroom,Point centerPoint, String mushroomType, int width) {
        this.mushroom = mushroom;
        this.centerPoint = new Point((int)(mushroom.getLocation().getHitbox().getCenterPoint().x-width*0.5555),mushroom.getLocation().getHitbox().getCenterPoint().y);
        this.mushroomType = mushroomType;
        this.width = width;

        mushroom.addObserver(this);

        BufferedImage image = null;
        try {
            Path imagePath = Path.of(System.getProperty("user.dir"), "Assets", "Mushrooms", "young"+ mushroomType,"5Y.png");
            image=ImageIO.read(imagePath.toFile());
        }catch (IOException e){
            e.printStackTrace();
        }
        if (image == null) {
            throw new IllegalArgumentException("Image could not be loaded");
        }

       drawable=new DrawableTexture(centerPoint, image, width);


    }
    public boolean isHit(Point point){
        if(point.distance(centerPoint)<=width*0.5)
            return true;
        return false;
    }

    /**
     * a hozzá tartozó Mushroom objektum hívja le, ha változik a
     *  spóra dobási képessége
     *  kora
     *  spóráinak száma
     */
    public void onTextureChanged(){
        boolean isyoung = mushroom.getAge()<=4;
        int spores = 5-mushroom.getSporesThrown();
        boolean abletothrow = mushroom.getNumberOfSpores()==1;
        BufferedImage image = null;


        try {
            Path imagePath = Path.of(System.getProperty("user.dir"), "Assets", "Mushrooms", (isyoung?"young":"old")+ mushroomType,spores+(abletothrow?"Y":"N")+".png");
            image=ImageIO.read(imagePath.toFile());
        }catch (IOException e){
            e.printStackTrace();
        }
        if (image == null) {
            throw new IllegalArgumentException("Image could not be loaded");
        }

        ((DrawableTexture)drawable).refreshImage(image);
    }

    public void onPositionChanged(){
        centerPoint = new Point((int)(mushroom.getLocation().getHitbox().getCenterPoint().getX()-width*0.5555),(int)(mushroom.getLocation().getHitbox().getCenterPoint().getY()));
        ((DrawableTexture)drawable).setPosition(centerPoint);
    }



}
