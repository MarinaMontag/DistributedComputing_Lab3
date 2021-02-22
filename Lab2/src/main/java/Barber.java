import java.util.concurrent.Semaphore;

public class Barber implements Runnable{
    private Semaphore barberReady;
    private Semaphore clientReady;
    private Semaphore barberDone;
    private Semaphore clientDone;
    private int limit;

    public Barber(Semaphore barberReady, Semaphore clientReady, Semaphore barberDone, Semaphore clientDone, int limit) {
        this.barberReady = barberReady;
        this.clientReady = clientReady;
        this.barberDone = barberDone;
        this.clientDone = clientDone;
        this.limit=limit;
        new Thread(this).start();
    }

    @Override
    public void run() {
        for(int i=0;i<limit;i++){
            try {
                clientReady.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            barberReady.release();
            cutHair();
            try {
                clientDone.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            carryOutClient();
            barberDone.release();
        }

    }

    private void cutHair(){
        System.out.println("Barber is working...");
    }

    private void carryOutClient(){
        System.out.println("Barber is carrying out client to exit...");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
