//T102-08
//Qi Tang(qt4ur), Elaina Michelle Hill(emh2hb), Hye Won Chang(hc5we)

package qt4ur.cs2110.virginia.edu.gameproject;

import android.graphics.Rect;
import android.widget.ImageView;
import java.util.Random;

/**
 * Created by tomoei on 4/18/15.
 */
public class SingleGhost {

    Boy boy;
    float x,y,rx,ry,dis;
    Rect hitbox;
    boolean killed,playmusic;
    int tick ;


    Random random = new Random();

    public SingleGhost(Boy boy, ImageView ghost){
        this.x = ghost.getX();
        this.y = ghost.getY();
        this.boy = boy;
        rx = 0;
        ry = 0;
        dis = 0;
        tick = -1;
        killed = true;
        playmusic = false;
        this.hitbox = new Rect(ghost.getLeft(),ghost.getTop(),ghost.getRight(),ghost.getBottom());
    }

    public void move(){
        rx = boy.getX()-x;
        ry = boy.getY()-y;
        dis = (float) Math.sqrt(rx * rx + ry * ry);

        if (dis<=7){
            x = boy.getX();
            y = boy.getY();
        }else {
            x = x + 2 * rx / dis;
            y = y + 2 * ry / dis;
        }

        setHitbox(new Rect((int)x,(int)y,(int)(x+32),(int)(y+32)));
    }

    public void reset(){
        x = random.nextFloat()*1000;
        y = random.nextFloat()*1500;
    }

    public void disappear(){
        x = 1500;
        y = 2000;
    }

    public boolean collide(Boy boy){
        return this.getHitbox().intersect(boy.getHitbox());
    }










     //getters
    public Rect getHitbox() {
        return hitbox;
    }

    public float getDis() {
        return dis;
    }

    public float getRy() {
        return ry;
    }

    public float getRx() {
        return rx;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public Boy getBoy() {
        return boy;
    }

    public boolean isKilled() {
        return killed;
    }

    public int getTick() {
        return tick;
    }

    public boolean isPlaymusic() {
        return playmusic;
    }

    //setters
    public void setBoy(Boy boy) {
        this.boy = boy;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setRx(float rx) {
        this.rx = rx;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public void setDis(float dis) {
        this.dis = dis;
    }

    public void setHitbox(Rect hitbox) {
        this.hitbox = hitbox;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setPlaymusic(boolean playmusic) {
        this.playmusic = playmusic;
    }
}
