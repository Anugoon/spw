package f2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter {

    GamePanel gp;

    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Bullet> bull = new ArrayList<Bullet>();
    private ArrayList<Item> items = new ArrayList<Item>();
    private ArrayList<BossShip> boss = new ArrayList<BossShip>();
    //  private int u = 0;
    private SpaceShip v;
    private int time = 0;
    private Timer timer;
    private Timer timer2;
    private long score = 0;
    private double difficulty = 0.1;
    private int getItem = 0;
    private int num_bullet = 10;

    public GameEngine(GamePanel gp, SpaceShip v) {
        this.gp = gp;
        this.v = v;

        gp.sprites.add(v);

        timer = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                process();
            }
        });
        timer.setRepeats(true);

    }

    public void start() {
        timer.start();
    }

    private void generateEnemy() {
        Enemy e = new Enemy((int) (Math.random() * 390), 30);
        gp.sprites.add(e);
        enemies.add(e);
    }
    private void generateBoss() {
        
        BossShip b = new BossShip((int) (Math.random() * 390)*6, 30);
        gp.sprites.add(b);
        boss.add(b);
    }

    private void generateBullet() {
        Bullet e1 = new Bullet(v.x, v.y - 30);
        gp.sprites.add(e1);
        bull.add(e1);
    }

    private void generateBullet2() {
        Bullet e2 = new Bullet(v.x - 10, v.y - 30);
        Bullet e3 = new Bullet(v.x, v.y - 30);
        Bullet e4 = new Bullet(v.x + 10, v.y - 30);
        gp.sprites.add(e2);
        bull.add(e2);
        gp.sprites.add(e3);
        bull.add(e3);
        gp.sprites.add(e4);
        bull.add(e4);
    }

    private void generateItem() {
        Item i1 = new Item((int) (Math.random() * 390)*2, 30);
        gp.sprites.add(i1);
        items.add(i1);
    }

    private void process() {
        if(time > 0){
            time--;
        }
        if (Math.random() < difficulty - 0.05) {
            generateItem();
        }
        if (Math.random() < difficulty) {
            generateEnemy();
        }
        if (Math.random() < difficulty ) {
            generateBoss();
        }
        
        Iterator<Enemy> e_iter = enemies.iterator();
        while (e_iter.hasNext()) {
            Enemy e = e_iter.next();
            e.proceed();

            if (!e.isAlive()) {
                e_iter.remove();
                gp.sprites.remove(e);
                score += 100;
            }
        }
        
        Iterator<BossShip> b_iter = boss.iterator();
        while (b_iter.hasNext()) {
            BossShip b = b_iter.next();
            b.proceed();

            if (!b.isAlive()) {
                b_iter.remove();
                gp.sprites.remove(b);
                score += 500;
            }
        }
		//
        if(num_bullet == 0){
            getItem = 0;
        }
        Iterator<Bullet> e_iter1 = bull.iterator();
        while (e_iter1.hasNext()) {
            Bullet e1 = e_iter1.next();
            e1.proceed();

            if (!e1.isAlive()) {
                e_iter1.remove();
                gp.sprites.remove(e1);
            }
        }
        
        Iterator<Item> i_iter = items.iterator();
        while (i_iter.hasNext()) {
            Item i = i_iter.next();
            i.proceed();

            if (!i.isAlive()) {
                i_iter.remove();
                gp.sprites.remove(i);
            }
        }

        //
        gp.updateGameUI(this);

        Rectangle2D.Double vr = v.getRectangle();
        Rectangle2D.Double er;
        Rectangle2D.Double br;
        Rectangle2D.Double ir;
        Rectangle2D.Double bosr;
        
        for (BossShip bo : boss) {
            bosr = bo.getRectangle();
            if (bosr.intersects(vr)) {
                die();
                return;
            }
            for (Item i5 : items) {
                ir = i5.getRectangle();
                if (ir.intersects(vr)) {
                        getItem = 1;
                        num_bullet = 10;      
                        i5.destroy();
                        
                        gp.sprites.remove(i5);
                    return;
                }
            }

            for (Bullet e1 : bull) {
                br = e1.getRectangle();
                 
                if (br.intersects(bosr)) {
                    if(time == 0){
                       
                    e1.destroy();
                    bo.shootBoss();
                    if(bo.getHpBoss() == 0){
                          gp.sprites.remove(bo);
                          bo.destroy();
                          
                    }
                     time = 4;
                    }            
                    return;
                }
       
            }
            
        }
        
        for (Enemy e : enemies) {
            er = e.getRectangle();
            if (er.intersects(vr)) {
                die();
                return;
            }

            for (Item i5 : items) {
                ir = i5.getRectangle();
                if (ir.intersects(vr)) {
                        getItem = 1;
                        num_bullet = 10;      
                        i5.destroy();
                        gp.sprites.remove(i5);
                    return;
                }
            }

            for (Bullet e1 : bull) {
                br = e1.getRectangle();
                if (br.intersects(er)) {
                    gp.sprites.remove(e);
                    e.destroy();

                    return;
                }
       
            }
            


        }
        
    }

    public void die() {
        timer.stop();
    }

    void controlVehicle(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                v.move(-1);
                break;
            case KeyEvent.VK_RIGHT:
                v.move(1);
                break;
            case KeyEvent.VK_D:
                difficulty += 0.1;
                break;
            case KeyEvent.VK_SPACE:
                if(getItem == 0)
                    gun();
                else
                    gun2();
                break;
        }
    }

    public long getScore() {
        return score;
    }

    public void gun() {
        generateBullet();
    }
    
     public void gun2() {
        generateBullet2();
        num_bullet--;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        controlVehicle(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothing		
    }
}
