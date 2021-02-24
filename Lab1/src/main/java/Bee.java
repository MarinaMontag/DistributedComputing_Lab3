import java.util.concurrent.atomic.AtomicBoolean;

public class Bee implements Runnable {
    private final Winnie winnie;
    private final Pot pot;
    private final int number;
    private final AtomicBoolean interrupted = new AtomicBoolean(false);

    Bee(int number, Winnie winnie, Pot pot) {
        this.number = number;
        this.pot = pot;
        this.winnie = winnie;
        Thread bee = new Thread(this);
        bee.start();
    }

    public void setInterrupted() {
      interrupted.set(true);
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void run() {
        while (!interrupted.get()) {
            if (winnie.getSemaphore().get()) {
                synchronized (winnie) {
                    if (pot.bringHoney(this) == pot.getCapacity())
                        winnie.notify();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
