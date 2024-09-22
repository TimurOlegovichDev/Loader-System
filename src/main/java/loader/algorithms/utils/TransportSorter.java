package loader.algorithms.utils;

import loader.db.TransportDataManager;
import loader.model.entites.Transport;

import java.util.List;

public interface TransportSorter {

    List<Transport> sort(TransportDataManager transportDataManager);
}
