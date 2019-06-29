package potz.utils.database;

public interface TileMap<T> {

    public T getTile(int... coordinates);
}
