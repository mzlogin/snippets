package dagger2;

import javax.inject.Inject;

public class Car {
    @Inject Engine engine;
    @Inject Seat seat;
    @Inject Wheel wheel;
    public Car() {
        DaggerCarComponent
                .builder()
                .carModule(new CarModule())
                .build()
                .inject(this);
        System.out.printf("new Car()");
    }

    public static void main(String[] args) {
        new Car();
    }
}
