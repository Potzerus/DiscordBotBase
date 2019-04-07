package potz.utils.database;

public class Capsule<T> {
    private T value;

    public Capsule(T value){
        this.value=value;
    }

    public T getValue() {
        return value;
    }
}
