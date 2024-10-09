package ru.liga.loadersystem.processor.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.liga.loadersystem.algorithm.LoadingCargoAlgorithm;
import ru.liga.loadersystem.model.entity.Cargo;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.processor.LoadingProcessor;

import java.util.List;

@Slf4j
@Service
public class DefaultLoadingProcessor implements LoadingProcessor {

    /**
     * запускает кастомный алгоритм погрузки
     *
     * @param algorithm кастомный алгоритм погрузки
     */

    @Override
    public void process(LoadingCargoAlgorithm algorithm,
                        List<Transport> transports,
                        List<Cargo> cargos) {
        algorithm.execute(
                cargos,
                transports
        );
    }
}
