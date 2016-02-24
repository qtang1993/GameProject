//T102-08
//Qi Tang(qt4ur), Elaina Michelle Hill(emh2hb), Hye Won Chang(hc5we)

package qt4ur.cs2110.virginia.edu.gameproject;

import android.graphics.Rect;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.AsyncTask;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
* Created by tomoei on 4/6/15.
*/
public class Boy {

    float x,y;
    int money;
    Rect hitbox;
    int hp;


//    int bomb, tick;
//    MeteringRectangle hitbox;

    public Boy(ImageView boy){
        x = boy.getX();
        y = boy.getY();
        hp =100;
        money = 0;

        this.hitbox = new Rect(boy.getLeft(),boy.getTop(),boy.getRight(), boy.getBottom());

    }

    public void move(MotionEvent m) {

        float rx = m.getX() - x;
        float ry = m.getY() - y;
        float dis = (float) Math.sqrt(rx * rx + ry * ry);

        if (dis <= 50){
            setX(m.getX());
            setY(m.getY());
        }else{
            setX(x + 35 * rx / dis);
            setY(y + 35 * ry / dis);
        }
        setHitbox(new Rect((int)x,(int)y,(int)(x+44),(int)(y+48)));



    }

    public void smallBomb(){

    }

    public boolean collide(SingleGhost ghost){
        return this.getHitbox().intersect(ghost.getHitbox());
    }






    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }



    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }



    public Rect getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rect hitbox) {
        this.hitbox = hitbox;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

