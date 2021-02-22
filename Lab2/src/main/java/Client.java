import java.util.concurrent.Semaphore;

public class Client implements Runnable{
    private Semaphore barberReady;
    private Semaphore clientReady;
    private Semaphore barberDone;
    private Semaphore clientDone;
    private int number;

    public Client(int number, Semaphore barberReady, Semaphore clientReady, Semaphore barberDone, Semaphore clientDone) {
        this.barberReady = barberReady;
        this.clientReady = clientReady;
        this.barberDone = barberDone;
        this.clientDone = clientDone;
        this.number=number;
        new Thread(this).start();
    }
    @Override
    public void run() {
        enter();
        clientReady.release();
        try {
            barberReady.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sleep();
        clientDone.release();
        try {
            barberDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Client #"+number+" went out");
    }

    private void enter(){
        System.out.println("Client has entered the barbershop");
    }
    private void sleep(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
