package loader.utils.initializers.cargo;

import loader.entites.cargos.Cargo;

import java.util.List;

public interface CargoInitializer {

    List<Cargo> initializeToList(List<String> forms);

}
