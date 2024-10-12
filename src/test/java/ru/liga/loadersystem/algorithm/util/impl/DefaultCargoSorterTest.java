package ru.liga.loadersystem.algorithm.util.impl;

import org.junit.jupiter.api.Test;
import ru.liga.loadersystem.model.entity.Cargo;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultCargoSorterTest {

    private final DefaultCargoSorter sorter = new DefaultCargoSorter();

    @Test
    void testSort_EmptyList_ReturnsEmptyList() {
        List<Cargo> cargos = new ArrayList<>();
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertThat(sortedCargos).isNotNull();
        assertThat(sortedCargos).isEmpty();
    }

    @Test
    void testSort_SingleElementList_ReturnsSameList() {
        Cargo cargo = new Cargo("cargo1", "A");
        List<Cargo> cargos = List.of(cargo);
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertThat(sortedCargos).isNotNull();
        assertThat(sortedCargos).hasSize(1);
        assertThat(sortedCargos).containsExactly(cargo);
    }

    @Test
    void testSort_MultipleElementsList_SortedByAreaDescending() {
        Cargo cargo1 = new Cargo("cargo1", "AA;AA"); // area = 4
        Cargo cargo2 = new Cargo("cargo2", "B"); // area = 1
        Cargo cargo3 = new Cargo("cargo3", "CCC;CCC"); // area = 9
        List<Cargo> cargos = List.of(cargo1, cargo2, cargo3);
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertThat(sortedCargos).isNotNull();
        assertThat(sortedCargos).hasSize(3);
        assertThat(sortedCargos).containsExactly(cargo3, cargo1, cargo2);
    }

    @Test
    void testSort_MultipleElementsListWithEqualAreas_SortedByAreaDescending() {
        Cargo cargo1 = new Cargo("cargo1", "AA;AA"); // area = 4
        Cargo cargo2 = new Cargo("cargo2", "BB;BB"); // area = 4
        Cargo cargo3 = new Cargo("cargo3", "C"); // area = 1
        List<Cargo> cargos = List.of(cargo1, cargo2, cargo3);
        List<Cargo> sortedCargos = sorter.sort(cargos);
        assertThat(sortedCargos).isNotNull();
        assertThat(sortedCargos).hasSize(3);
        assertThat(sortedCargos).containsExactly(cargo1, cargo2, cargo3);
    }
}