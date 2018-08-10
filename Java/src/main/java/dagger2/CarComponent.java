package dagger2;

import dagger.Component;

@Component(modules = CarModule.class)
public interface CarComponent {
    void inject(Car car);
}
