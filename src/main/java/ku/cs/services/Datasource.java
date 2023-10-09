package ku.cs.services;

public interface Datasource <T> {
    T readData();
    void insertData(T t);
}
