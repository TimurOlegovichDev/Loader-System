package ru.liga.loader.algorithm.util.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.loader.model.entity.Transport;
import ru.liga.loader.repository.TransportCrudRepository;
import ru.liga.loader.service.TransportService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransportSorterByOccupiedAreaDescTest {

    @Mock
    private TransportService transportServiceq11;

    @Mock
    private TransportCrudRepository transportCrudRepository;

    @InjectMocks
    private TransportSorterByOccupiedAreaDesc transportSorterByOccupiedAreaDesc;

    @Test
    void testSort_EmptyList_ReturnsEmptyList() {
        List<Transport> transports = new ArrayList<>();
        List<Transport> sortedTransports = transportSorterByOccupiedAreaDesc.sort(transportCrudRepository, transports);
        assertEquals(0, sortedTransports.size());
    }

    @Test
    void testSort_SingleElementList_ReturnsSameList() {
        Transport transport = new Transport(1, 1);
        List<Transport> transports = List.of(transport);
        List<Transport> sortedTransports = transportSorterByOccupiedAreaDesc.sort(transportCrudRepository, transports);
        assertEquals(1, sortedTransports.size());
        assertEquals(transport, sortedTransports.get(0));
    }

    @Test
    void testSort_MultipleElementsList() {
        Transport transport1 = new Transport(1, 1);
        Transport transport2 = new Transport(2, 2);
        Transport transport3 = new Transport(3, 3);
        List<Transport> transports = List.of(transport1, transport2, transport3);
        when(transportServiceq11.percentageOfOccupancy(transport1)).thenReturn(25);
        when(transportServiceq11.percentageOfOccupancy(transport2)).thenReturn(50);
        when(transportServiceq11.percentageOfOccupancy(transport3)).thenReturn(75);
        List<Transport> sortedTransports = transportSorterByOccupiedAreaDesc.sort(transportCrudRepository, transports);
        assertEquals(3, sortedTransports.size());
        assertEquals(transport3, sortedTransports.get(0));
        assertEquals(transport2, sortedTransports.get(1));
        assertEquals(transport1, sortedTransports.get(2));
    }

    @Test
    void testSort_MultipleElementsListWithEqualWeights() {
        Transport transport1 = new Transport(1, 1);
        Transport transport2 = new Transport(2, 2);
        Transport transport3 = new Transport(3, 3);
        List<Transport> transports = List.of(transport1, transport2, transport3);
        when(transportServiceq11.percentageOfOccupancy(transport1)).thenReturn(75);
        when(transportServiceq11.percentageOfOccupancy(transport2)).thenReturn(50);
        when(transportServiceq11.percentageOfOccupancy(transport3)).thenReturn(25);
        List<Transport> sortedTransports = transportSorterByOccupiedAreaDesc.sort(transportCrudRepository, transports);
        assertEquals(3, sortedTransports.size());
        assertEquals(transport1, sortedTransports.get(0));
        assertEquals(transport2, sortedTransports.get(1));
        assertEquals(transport3, sortedTransports.get(2));
    }
}