package loader_system.db;

import java.util.List;

public interface Datable {
    List<?> getData();
    void add(Object o);
    void remove(Object o);
}
