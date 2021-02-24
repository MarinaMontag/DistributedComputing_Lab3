import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore barberReady=new Semaphore(0);
        Semaphore clientReady=new Semaphore(0);
        Semaphore barberDone=new Semaphore(0);
        Semaphore clientDone=new Semaphore(0);
        int limit=7;
        new Barber(barberReady,clientReady,barberDone,clientDone,limit);
        for(int i=0;i<limit;i++){
            try {
                Thread.sleep(new Random().nextInt(2000));
                new Client(i+1,barberReady,clientReady,barberDone,clientDone);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
