import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Winnie implements Runnable{
    private final List<Bee>bees;
    private final Pot pot;
    private final AtomicBoolean semaphore = new AtomicBoolean(false);
    Winnie(Pot pot){
        int BEES_CAPACITY = 20;
        this.bees=new ArrayList<>(BEES_CAPACITY);
        this.pot=pot;
        new Thread(this).start();
        for(int i = 0; i< BEES_CAPACITY; i++)
           bees.add(new Bee(i,this,pot));
    }

    public AtomicBoolean getSemaphore(){
        return semaphore;
    }

    @Override
    public void run() {
        int iterations = 5;
        for(int i = 0; i< iterations; i++){
            semaphore.set(true);
            synchronized (this){
                try {
                    wait();
                    semaphore.set(false);
                    pot.eatHoney();
                    System.out.println("\nWinnie ate all honey and went to bed\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        synchronized (this){
            for(Bee bee:bees)
                bee.setInterrupted();
        }
    }
}
