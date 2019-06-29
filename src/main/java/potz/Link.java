package potz;

public class Link<V1,V2> {
    private V1 first;
    private V2 second;

    public Link(V1 first,V2 second){
        this.first=first;
        this.second=second;
    }

    public V1 getFirst() {
        return first;
    }

    public V2 getSecond() {
        return second;
    }

    public void setFirst(V1 first) {
        this.first = first;
    }

    public void setSecond(V2 second) {
        this.second = second;
    }
}
