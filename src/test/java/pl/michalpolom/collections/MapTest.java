package pl.michalpolom.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import org.junit.jupiter.api.Test;
import pl.michalpolom.collections.entity.Names;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapTest {


    /**
     * <h1>HashMap</h1>
     * Przechowuje pary klucz-wartość w strukturze danych opartej na tablicy mieszającej (hash table).  <p>
     * Kolejność elementów w HashMap nie jest gwarantowana.  <p>
     * Pozwala na przechowywanie jednej wartości null jako klucza i wielu wartości null jako wartości.
     */
    @Test
    void hashMap() {

        final var names = new HashMap<String, String>();

        names.put("Alice", "test1");
        names.put("Bob", "test2");
        names.put("John", "test3");
        names.put("John", "test4");

        assertAll(
                () -> assertEquals("test4", names.get("John")),
                () -> assertDoesNotThrow(() -> names.put(null, null)),
                () -> assertEquals(4, names.size()),
                () -> assertFalse(names.isEmpty())
        );
    }


    /**
     * <h1>LinkedHashMap extends HashMap</h1>
     * Działa podobnie jak HashMap, ale dodatkowo utrzymuje kolejność dodawania elementów dzięki zastosowaniu listy dwukierunkowej. <p>
     * Wydajność podstawowych operacji jest zbliżona do HashMap, ale z nieco większym zużyciem pamięci ze względu na dodatkowe wskaźniki używane do utrzymania kolejności.
     */
    @Test
    void linkedHashMap() {

        final var names = new LinkedHashMap<String, String>();

        names.put("Alice", "test1");
        names.put("Bob", "test2");
        names.put("John", "test3");
        names.put("John", "test4");


        assertAll(
                () -> assertEquals("{Alice=test1, Bob=test2, John=test4}", names.toString()),

                () -> assertEquals("test4", names.get("John")),
                () -> assertDoesNotThrow(() -> names.put(null, null)),
                () -> assertEquals(4, names.size()),
                () -> assertFalse(names.isEmpty())
        );
    }

    /**
     * <h1>TreeMap</h1>
     * Bazuje na strukturze danych drzewa czerwono-czarnego (red-black tree). <p>
     * Kolejność sortowania jest określana przez naturalny porządek kluczy (dla obiektów implementujących Comparable) lub za pomocą zewnętrznego komparatora. <p>
     * Nie pozwala na przechowywanie wartości null jako klucza, ale pozwala na przechowywanie wielu wartości null jako wartości.
     */
    @Test
    void treeMap() {

        final var names = new TreeMap<String, String>();
        final var namesWithComparator = new TreeMap<String, String>(Comparator.reverseOrder());

        names.put("Bob", "test2");
        names.put("John", "test3");
        names.put("John", "test4");
        names.put("Alice", "test1");

        assertAll(
                () -> assertEquals("{Alice=test1, Bob=test2, John=test4}", names.toString()),

                () -> assertEquals("test4", names.get("John")),
                () -> assertThrows(NullPointerException.class, () -> names.put(null, null)),
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty())
        );
    }

    /**
     * <h1>TreeMap - part 2</h1>
     */
    @Test
    void treeMapWithComparator() {

        final var names = new TreeMap<String, String>(Comparator.reverseOrder());

        names.put("Bob", "test2");
        names.put("John", "test3");
        names.put("John", "test4");
        names.put("Alice", "test1");

        assertAll(
                () -> assertEquals("{John=test4, Bob=test2, Alice=test1}", names.toString()),

                () -> assertEquals("test4", names.get("John")),
                () -> assertThrows(NullPointerException.class, () -> names.put(null, null)),
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty())
        );
    }

    /**
     * <h1>EnumMap</h1>
     * Może przechowywać tylko elementy wyliczenia jako klucze.<p>
     * Nie może zawierać wartości null jako kluczy.<p>
     * Zachowuje naturalną kolejność elementów wyliczenia, tzn. kolejność, w jakiej zostały zadeklarowane w definicji wyliczenia.<p>
     */
    @Test
    void enumMap() {

        //final var names = new EnumMap<>(Names.class);
        final var names = new EnumMap<Names, String>(Names.class);

        names.put(Names.JOHN, "test1");
        names.put(Names.ALICE, "test2");
        names.put(Names.BOB, "test3");
        names.put(Names.BOB, "test4");

        assertAll(
                () -> assertEquals("{BOB=test4, JOHN=test1, ALICE=test2}", names.toString()), // naturalna kolejność enuma
                () -> assertTrue(names.containsKey(Names.BOB)),
                () -> assertThrows(NullPointerException.class, () -> names.put(null, null)),
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertEquals("test1", names.remove(Names.JOHN)),
                () -> assertEquals(null, names.remove(Names.JOHN))
        );
    }

    /**
     * <h1>ConcurrentHashMap</h1>
     * Bezpieczna dla wielowątkowości. <p>
     * Nie pozwala na przechowywanie wartości null jako kluczy ani wartości. <p>
     */
    @Test
    void concurrentHashMap() {

        final var names = new ConcurrentHashMap<String, String>();

        names.put("Alice", "test1");
        names.put("Bob", "test2");
        names.put("John", "test3");
        names.put("John", "test4");

        assertAll(
                () -> assertEquals("test4", names.get("John")),
                () -> assertThrows(NullPointerException.class, () -> names.put(null, null)),
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty())
        );
    }

    /**
     * <h1>Map.of()</h1>
     * Mapa utworzona za pomocą Map.of() jest niemodyfikowalna.<p>
     * Nie dopuszcza wartości null.<p>
     * Mapa jest bezpieczna dla wielowątkowości - jej stan nie może zostać zmieniony po utworzeniu.<p>
     * Nie gwarantuje żadnej konkretnej kolejności przechowywania elementów.
     */
    @Test
    void mapOf() {

        final var names = Map.of("John", "test1", "Alice", "test2", "Bob", "test3");

        assertAll(
                () -> assertThrows(UnsupportedOperationException.class, () -> names.put("Marcin", "test4")),
                () -> assertThrows(NullPointerException.class, () -> Map.of(null, "test5")),
                () -> assertThrows(NullPointerException.class, () -> Map.of("Marcin", null))
        );
    }

    /**
     * <h1>Maps from Guava</h1>
     * Jest to klasa pomocnicza zawierająca różne metody do tworzenia i manipulowania mapami
     */
    @Test
    void maps() {
        // Tworzenie mapy z użyciem Maps.newHashMap()
        Map<String, Integer> newHashMap = Maps.newHashMap();
        newHashMap.put("one", 1);
        newHashMap.put("two", 2);
        System.out.println("New HashMap: " + newHashMap);

        // Tworzenie mapy z użyciem Maps.asMap() - ZALEŻNA OD WEJŚCIOWEGO ZBIORU
        Map<String, Integer> asMap = Maps.asMap(Sets.newHashSet("one", "two", "three"), String::length);
        System.out.println("Map created with Maps.asMap(): " + asMap);

        // Tworzenie mapy z użyciem Maps.toMap() - NIEZALEŻNA OD WEJŚCIOWEGO ZBIORU
        Map<String, Integer> toMap = Maps.toMap(Sets.newHashSet("one", "two", "three"), String::length);
        System.out.println("Map created with Maps.toMap(): " + toMap);

        // Tworzenie mapy z użyciem Maps.uniqueIndex() - WYNIK IMMUTABLE MAP
        Map<Integer, String> uniqueIndex = Maps.uniqueIndex(Sets.newHashSet("one", "two", "three"), String::hashCode);
        System.out.println("Map created with Maps.uniqueIndex(): " + uniqueIndex);

        // Filtrowanie mapy z użyciem Maps.filterKeys() i Maps.filterValues()
        Map<String, Integer> filteredKeys = Maps.filterKeys(newHashMap, key -> key.startsWith("t"));
        System.out.println("Map filtered by keys: " + filteredKeys);
        Map<String, Integer> filteredValues = Maps.filterValues(newHashMap, value -> value > 1);
        System.out.println("Map filtered by values: " + filteredValues);


        // RÓŻNICA POMIĘDZY toMap a asMap
        Set<String> keys = Sets.newHashSet("one", "two");

        // Tworzenie mapy za pomocą Maps.asMap()
        Map<String, Integer> asMap1 = Maps.asMap(keys, String::length);
        System.out.println("\nMap created with Maps.asMap(): " + asMap1);
        keys.add("three");
        System.out.println("Map after adding a key to the original set: " + asMap1);

        // Tworzenie mapy za pomocą Maps.toMap()
        Map<String, Integer> toMap1 = Maps.toMap(keys, String::length);
        System.out.println("\nMap created with Maps.toMap(): " + toMap1);
        keys.add("four");
        System.out.println("Map after adding a key to the original set: " + toMap1);
    }

    /**
     * <h1>BiMaps from Guava</h1>
     * Reprezentuje mapę dwukierunkową (dwustronną), gdzie każda para klucz-wartość ma unikalne odwzorowanie na parę wartość-klucz. W praktyce oznacza to, że BiMap pozwala na szybkie wyszukiwanie wartości na podstawie klucza, a także klucza na podstawie wartości. <p>
     * W zwykłych mapach, takich jak HashMap, wyszukiwanie klucza na podstawie wartości wymaga iterowania po całej mapie, co może być mało wydajne.<p>
     * Gwarantuje unikalność wartości w mapie.
     */
    @Test
    void biMap(){
        BiMap<String, Integer> biMap = HashBiMap.create();
        BiMap<String, Integer> immutableBiMap = ImmutableBiMap.of("one", 1, "two", 2, "three", 3);

        biMap.put("one", 1);
        biMap.put("two", 2);
        biMap.put("three", 3);

        assertAll(
                // GET
                () -> assertEquals(1, biMap.get("one")),
                () -> assertEquals("one", biMap.inverse().get(1)),

                // PUT
                () -> assertThrows(IllegalArgumentException.class, () -> biMap.put("one2", 1)),
                () -> assertDoesNotThrow(() -> biMap.put("one", 10)),

                // FORCE PUT
                () -> assertDoesNotThrow(() -> biMap.forcePut("one2", 1)),

                () -> assertDoesNotThrow(() -> biMap.put(null, null)),
                () -> assertEquals(5, biMap.size()),
                () -> assertFalse(biMap.isEmpty())
        );
    }

    /**
     * <h1>Multimap from Guava</h1>
     * Interfejs w bibliotece Guava, który reprezentuje mapę, gdzie jeden klucz może być mapowany na wiele wartości. <p>
     * W związku z tym Multimap pozwala na przechowywanie duplikatów wartości dla tego samego klucza. <p>
     * Dostępne są różne implementacje Multimap, takie jak ArrayListMultimap, HashMultimap, LinkedHashMultimap, TreeMultimap i ImmutableMultimap.
     */
    @Test
    void multimap(){
        Multimap<String, Integer> multimap = ArrayListMultimap.create();

        multimap.put("one", 1);
        multimap.put("two", 2);
        multimap.put("three", 3);
        multimap.put("three", 33);

        assertAll(
                () -> assertEquals("[3, 33]", multimap.get("three").toString()),
                () -> assertTrue(multimap.remove("three", 3)),
                () -> assertEquals("[33]", multimap.get("three").toString()),

                () -> assertDoesNotThrow(() -> multimap.put(null, null))
        );
    }

    /**
     * <h1>Table from Guava</h1>
     * Reprezentuje dwuwymiarową tablicę, gdzie wartości są indeksowane zarówno przez wiersz, jak i kolumnę. <p>
     * Dostępne są różne implementacje Table, takie jak HashBasedTable, ArrayTable, TreeBasedTable i ImmutableTable.
     */
    @Test
    void table(){
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("R1", "C1", 1);
        table.put("R1", "C2", 2);
        table.put("R2", "C1", 3);
        table.put("R2", "C2", 4);

        assertAll(
                () -> assertEquals("{R1={C1=1, C2=2}, R2={C1=3, C2=4}}", table.toString()),

                () -> assertEquals(2, table.get("R1", "C2")),
                () -> assertEquals("{R1=2, R2=4}", table.column("C2").toString()),
                () -> assertEquals("{C1=1, C2=2}", table.row("R1").toString()),

                () -> assertEquals(1, table.remove("R1", "C1"))
        );
    }
}