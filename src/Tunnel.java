import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private Semaphore smp = new Semaphore(MainClass.CARS_COUNT / 2);

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {
                if (!smp.tryAcquire()) { //если не удалось занять семафор
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    smp.acquire(); //ждать, когда освободится место в семафоре
                }

                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                smp.release(); //освободить место в семафоре
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
