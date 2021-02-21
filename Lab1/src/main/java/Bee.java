public class Bee implements Runnable{
    private Winnie winnie;
    private Pot pot;
    private Thread bee;
    Bee(Winnie winnie, Pot pot){
        this.pot=pot;
        this.winnie=winnie;
        bee=new Thread(this);
        bee.start();
    }

    public void setInterrupted(){
        bee.interrupt();
    }

    @Override
    public void run() {
        while(!bee.isInterrupted()){
            synchronized (winnie){
                if(pot.bringHoney()==pot.getCapacity())
                    notify();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
