package loader.model.utils.initializers.cargo;

import loader.model.entites.cargos.Cargo;

import java.util.List;

public interface CargoInitializer {

    List<Cargo> initializeToList(List<String> forms);

}
