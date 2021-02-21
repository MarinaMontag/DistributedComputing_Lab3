import java.util.concurrent.atomic.AtomicInteger;

public class Pot {
    private final int N=100;
    private AtomicInteger honey;
    Pot(){
       honey.set(0);
    }
    public Integer bringHoney(){
        return honey.incrementAndGet();
    }

    public void eatHoney(){
        honey.set(0);
    }
    public int getCapacity(){return N;}
}
