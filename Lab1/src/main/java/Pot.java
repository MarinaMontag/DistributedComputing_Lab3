import java.util.concurrent.atomic.AtomicInteger;

public class Pot {
    private final AtomicInteger honey = new AtomicInteger(0);
    public synchronized Integer bringHoney(Bee bee){
        System.out.println(honey.incrementAndGet()+". Bee #"+bee.getNumber()+" brought some honey");
        return honey.get();
    }

    public void eatHoney() throws InterruptedException {
        honey.set(0);
        Thread.sleep(500);
    }
    public int getCapacity(){
       return 100;
    }
}
