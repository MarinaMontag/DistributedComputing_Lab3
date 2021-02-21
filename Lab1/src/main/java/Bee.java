import java.util.concurrent.atomic.AtomicBoolean;

public class Bee implements Runnable {
    private Winnie winnie;
    private Pot pot;
    private Thread bee;
    private int number;
    private AtomicBoolean interrupted;

    Bee(int number, Winnie winnie, Pot pot) {
        interrupted=new AtomicBoolean(false);
        this.number = number;
        this.pot = pot;
        this.winnie = winnie;
        bee = new Thread(this);
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
