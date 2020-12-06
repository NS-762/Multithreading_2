import java.util.concurrent.CountDownLatch;

public class MainClass {

    public static final int CARS_COUNT = 4;
    private static CountDownLatch preparationOfCars = new CountDownLatch(MainClass.CARS_COUNT);
    private static CountDownLatch finishingCars = new CountDownLatch(MainClass.CARS_COUNT);


    public static CountDownLatch getPreparationOfCars() {
        return preparationOfCars;
    }

    public static CountDownLatch getFinishingCars() {
        return finishingCars;
    }

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT]; //массив машин

        for (int i = 0; i < cars.length; i++) { //заполнение массива
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        for (int i = 0; i < cars.length; i++) { //запуск потока каждой машины
            new Thread(cars[i]).start();
        }

        try {
            preparationOfCars.await(); //ждать, когда будут готовы все машины
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        Car.getCdlInCar().countDown(); // машины могут ехать


        try {
            finishingCars.await(); //ждать, когда финишируют все машины
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}
