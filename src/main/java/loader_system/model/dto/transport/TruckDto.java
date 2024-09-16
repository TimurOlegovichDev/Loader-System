package loader_system.model.dto.transport;

import loader_system.model.entites.cargos.Box;
import loader_system.model.entites.cargos.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TruckDto {
    private char[][] body;
    private List<Box> cargos;
}
