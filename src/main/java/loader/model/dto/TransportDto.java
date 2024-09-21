package loader.model.dto;

import loader.model.entites.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransportDto {
    private char[][] body;
    private List<Cargo> cargos;
}
