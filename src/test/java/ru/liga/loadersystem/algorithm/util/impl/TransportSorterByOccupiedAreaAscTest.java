package ru.liga.loadersystem.algorithm.util.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loadersystem.model.entity.Transport;
import ru.liga.loadersystem.repository.TransportCrudRepository;
import ru.liga.loadersystem.service.TransportService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransportSorterByOccupiedAreaAscTest {

    @Mock
    private TransportService transportService;

    @Mock
    private TransportCrudRepository transportCrudRepository;

    @InjectMocks
    private TransportSorterByOccupiedAreaAsc transportSorterByOccupiedAreaAsc;

    @Test
    void testSort_EmptyList_ReturnsEmptyList() {
        List<Transport> transports = new ArrayList<>();
        List<Transport> sortedTransports = transportSorterByOccupiedAreaAsc.sort(transportCrudRepository, transports);
        assertThat(sortedTransports).isEmpty();
    }

    @Test
    void testSort_SingleElementList() {
        Transport transport = new Transport(1, 1);
        List<Transport> transports = List.of(transport);
        List<Transport> sortedTransports = transportSorterByOccupiedAreaAsc.sort(transportCrudRepository, transports);
        assertThat(sortedTransports).hasSize(1);
        assertThat(sortedTransports).containsExactly(transport);
    }

    @Test
    void testSort_MultipleElementsList() {
        Transport transport1 = new Transport(1, 1);
        Transport transport2 = new Transport(2, 2);
        Transport transport3 = new Transport(3, 3);
        List<Transport> transports = List.of(transport1, transport2, transport3);
        when(transportService.percentageOfOccupancy(transport1)).thenReturn(50);
        when(transportService.percentageOfOccupancy(transport2)).thenReturn(75);
        when(transportService.percentageOfOccupancy(transport3)).thenReturn(25);
        List<Transport> sortedTransports = transportSorterByOccupiedAreaAsc.sort(transportCrudRepository, transports);
        assertThat(sortedTransports).hasSize(3);
        assertThat(sortedTransports).containsExactly(transport3, transport1, transport2);
    }

    @Test
    void testSort_MultipleElementsListWithEqualArea() {
        Transport transport1 = new Transport(1, 1);
        Transport transport2 = new Transport(2, 2);
        Transport transport3 = new Transport(3, 3);
        List<Transport> transports = List.of(transport1, transport2, transport3);
        when(transportService.percentageOfOccupancy(transport1)).thenReturn(50);
        when(transportService.percentageOfOccupancy(transport2)).thenReturn(50);
        when(transportService.percentageOfOccupancy(transport3)).thenReturn(50);
        List<Transport> sortedTransports = transportSorterByOccupiedAreaAsc.sort(transportCrudRepository, transports);
        assertThat(sortedTransports).hasSize(3);
    }
}