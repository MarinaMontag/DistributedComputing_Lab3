import java.util.ArrayList;
import java.util.List;

public class Winnie implements Runnable{
    private final List<Bee>bees;
    private final Pot pot;
    private final int iterations=5;
    Winnie(List<Bee>bees,Pot pot){
        this.bees=bees;
        this.pot=pot;
        new Thread(this).start();
    }
    @Override
    public void run() {
        for(int i=0;i<iterations;i++){
            synchronized (this){
                try {
                    wait();
                    pot.eatHoney();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (this){
            for(Bee bee:bees)
                bee.setInterrupted();
        }
    }
}
