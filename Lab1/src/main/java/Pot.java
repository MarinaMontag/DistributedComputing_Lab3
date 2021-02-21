import java.util.concurrent.atomic.AtomicInteger;

public class Pot {
    private final int N=100;
    private AtomicInteger honey;
    Pot(){
       honey=new AtomicInteger(0);
    }
    public synchronized Integer bringHoney(Bee bee){
        System.out.println(honey.incrementAndGet()+". Bee #"+bee.getNumber()+" brought some honey");
        return honey.get();
    }

    public void eatHoney(){
        honey.set(0);
    }
    public int getCapacity(){return N;}
}
