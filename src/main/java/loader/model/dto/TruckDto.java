package loader.model.dto;

import loader.model.entites.cargos.Cargo;
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
    private List<Cargo> cargos;
}
