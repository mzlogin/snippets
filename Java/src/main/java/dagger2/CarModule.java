package dagger2;

import dagger.Module;
import dagger.Provides;

@Module
public class CarModule {
    @Provides
    public Engine provideEngine() {
        return new Engine();
    }

    @Provides
    public Seat provideSeat() {
        return new Seat();
    }

    @Provides
    public Wheel provideWheel() {
        return new Wheel();
    }
}
