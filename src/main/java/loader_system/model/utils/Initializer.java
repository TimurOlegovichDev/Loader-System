package loader_system.model.utils;

import loader_system.db.CargoData;

import java.util.List;

public interface Initializer {

    void initialize(List<String> forms, CargoData cargoData);

}
