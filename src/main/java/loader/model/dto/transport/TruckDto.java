package loader.model.dto.transport;

import loader.model.entites.cargos.Box;
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
