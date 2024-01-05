package pl.michalpolom.collections;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import pl.michalpolom.collections.entity.Names;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetTest {


    /**
     * <h1>HashSet</h1>
     * Bazuje na strukturze danych tablicy mieszającej (hash table). <p>
     * Nie zachowuje kolejności elementów. <p>
     * Zapewnia szybkie operacje dodawania, usuwania i wyszukiwania elementów. <p>
     * Zwykle preferowany, gdy nie zależy nam na kolejności elementów, a ważne są szybkie operacje.
     */
    @Test
    void hashSet() {

        final var names = new HashSet<String>();

        names.add("Alice");
        names.add("Bob");
        names.add("John");
        names.add("John");
        names.add("John");

        assertAll(
                () -> assertTrue(names.contains("John")),
                () -> assertDoesNotThrow(() -> names.add(null)),
                () -> assertEquals(4, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertTrue(names.remove("John")),
                () -> assertFalse(names.remove("John"))
        );
    }


    /**
     * <h1>LinkedHashSet extends HashSet</h1>
     * Zachowuje kolejność wstawiania elementów. <p>
     * Operacje dodawania, usuwania i wyszukiwania są nieco wolniejsze niż w przypadku HashSet, ale nadal szybkie dzięki hash table. <p>
     * Preferowany, gdy zależy nam na zachowaniu kolejności elementów oraz szybkich operacjach.
     */
    @Test
    void linkedHashSet() {

        final var names = new LinkedHashSet<String>();

        names.add("Bob");
        names.add("Alice");
        names.add("John");
        names.add("John");
        names.add("John");

        assertAll(
                () -> assertEquals("[Bob, Alice, John]", names.toString()),

                () -> assertTrue(names.contains("John")),
                () -> assertDoesNotThrow(() -> names.add(null)),
                () -> assertEquals(4, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertTrue(names.remove("John")),
                () -> assertFalse(names.remove("John"))
        );
    }

    /**
     * <h1>TreeSet - part 2</h1>
     */
    @Test
    void treeSetWithComparator() {
//        final var names = new TreeSet<String>((s1, s2) -> s2.compareTo(s1));
        final var names = new TreeSet<String>(Comparator.reverseOrder());

        names.add("Bob");
        names.add("John");
        names.add("John");
        names.add("John");
        names.add("Alice");

        assertAll(
                () -> assertEquals("[John, Bob, Alice]", names.toString()),

                () -> assertTrue(names.contains("John")),
                () -> assertThrows(NullPointerException.class, () -> names.add(null)), // <--- różnica
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertTrue(names.remove("John")),
                () -> assertFalse(names.remove("John"))
        );
    }

    @Test
    void treeSet() {

        final var names = new TreeSet<String>();

        names.add("Bob");
        names.add("John");
        names.add("John");
        names.add("John");
        names.add("Alice");

        assertAll(
                () -> assertEquals("[Alice, Bob, John]", names.toString()),

                () -> assertTrue(names.contains("John")),
                () -> assertThrows(NullPointerException.class, () -> names.add(null)), // <--- różnica
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertTrue(names.remove("John")),
                () -> assertFalse(names.remove("John"))
        );
    }

    /**
     * <h1>EnumSet</h1>
     * EnumSet to specjalna implementacja interfejsu Set w Java Collections Framework, przeznaczona wyłącznie do przechowywania elementów wyliczenia (ang. enum). EnumSet zapewnia wydajność i kompaktowość w porównaniu z innymi implementacjami Set w przypadku przechowywania elementów wyliczen. <p>
     * <h3>IMPLEMENTACJE:</h3>
     * RegularEnumSet: Jest używana, gdy liczba elementów wyliczenia jest mniejsza lub równa 64.<p>
     * JumboEnumSet: Jest używana, gdy liczba elementów wyliczenia jest większa niż 64.
     */
    @Test
    void enumSet() {

        final var namesAll = EnumSet.allOf(Names.class);
        final var namesRange = EnumSet.range(Names.BOB, Names.ALICE);
        final var namesRange2 = EnumSet.range(Names.JOHN, Names.ALICE);

        final var names = EnumSet.of(Names.BOB, Names.BOB, Names.ALICE, Names.JOHN);
        names.add(Names.BOB);

        assertAll(
                () -> assertTrue(names.contains(Names.BOB)),
                () -> assertThrows(NullPointerException.class, () -> names.add(null)),
                () -> assertEquals(3, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertTrue(names.remove(Names.JOHN)),
                () -> assertFalse(names.remove(Names.JOHN)),

                () -> assertTrue(namesAll.contains(Names.BOB)),
                () -> assertTrue(namesAll.contains(Names.ALICE)),
                () -> assertTrue(namesAll.contains(Names.JOHN)),

                () -> assertTrue(namesRange.contains(Names.BOB)),
                () -> assertTrue(namesRange.contains(Names.ALICE)),
                () -> assertTrue(namesRange.contains(Names.JOHN)),

                () -> assertFalse(namesRange2.contains(Names.BOB)), //False
                () -> assertTrue(namesRange2.contains(Names.ALICE)),
                () -> assertTrue(namesRange2.contains(Names.JOHN))
        );
    }

    /**
     * <h1>CopyOnWriteArraySet</h1>
     * Przeznaczona do współdzielenia przez wiele wątków w środowiskach, w których operacje odczytu są znacznie częstsze niż operacje modyfikacji. CopyOnWriteArraySet używa mechanizmu "copy-on-write", co oznacza, że każda modyfikująca operacja, taka jak dodawanie, usuwanie lub aktualizacja elementów, powoduje utworzenie nowej kopii wewnętrznej struktury danych. W rezultacie operacje odczytu są wykonywane na niewspółdzielonym, niezmienionym obiekcie, co eliminuje konieczność stosowania blokad. <p>
     * Przechowuje elementy w kolejności, w jakiej zostały dodane do zbioru, podobnie jak LinkedHashSet. <p>
     * Elementy przechowywane w CopyOnWriteArraySet powinny być niemodyfikowalne, aby zapewnić bezpieczeństwo wątków.
     */
    @Test
    void copyOnWriteArraySet() {

        final var names = new CopyOnWriteArraySet<String>();

        names.add("Bob");
        names.add("John");
        names.add("John");
        names.add("John");
        names.add("Alice");

        assertAll(
                () -> assertEquals("[Bob, John, Alice]", names.toString()),

                () -> assertTrue(names.contains("John")),
                () -> assertDoesNotThrow(() -> names.add(null)),
                () -> assertEquals(4, names.size()),
                () -> assertFalse(names.isEmpty()),
                () -> assertTrue(names.remove("John")),
                () -> assertFalse(names.remove("John"))
        );
    }

    /**
     * <h1>Set.of()</h1>
     * Zbiór utworzona za pomocą Set.of() jest niemodyfikowalny.<p>
     * Nie dopuszcza wartości null.<p>
     * Zbiór jest bezpieczna dla wielowątkowości - jej stan nie może zostać zmieniony po utworzeniu.
     */
    @Test
    void setOf() {

        final var names = Set.of("John", "Alice", "Bob");

        assertAll(
                () -> assertThrows(Exception.class, () -> names.add("Marcin")),
                () -> assertThrows(Exception.class, () -> List.of(null))
        );
    }

    /**
     * <h1>Sets from Guava</h1>
     * Jest to klasa pomocnicza zawierająca różne metody do tworzenia i manipulowania zbiorami
     */
    @Test
    void sets() {
        Set<String> set1 = new HashSet<>();
        set1.add("A");
        set1.add("B");
        set1.add("C");

        Set<String> set2 = new HashSet<>();
        set2.add("B");
        set2.add("C");
        set2.add("D");

        // Przykład 1: Sets.union(Set<E> set1, Set<E> set2)
        Set<String> union = Sets.union(set1, set2);
        System.out.println("Unia zbiorów: " + union);

        // Przykład 2: Sets.intersection(Set<E> set1, Set<E> set2)
        Set<String> intersection = Sets.intersection(set1, set2);
        System.out.println("Przecięcie zbiorów: " + intersection);

        // Przykład 3: Sets.difference(Set<E> set1, Set<E> set2)
        Set<String> difference = Sets.difference(set1, set2);
        System.out.println("Różnica zbiorów (set1 - set2): " + difference);

        // Przykład 4: Sets.symmetricDifference(Set<E> set1, Set<E> set2)
        Set<String> symmetricDifference = Sets.symmetricDifference(set1, set2);
        System.out.println("Różnica symetryczna zbiorów: " + symmetricDifference);

        // Przykład 5: Sets.newHashSet(E... elements)
        Set<String> newHashSet = Sets.newHashSet("E", "F", "G");
        System.out.println("Nowy HashSet: " + newHashSet);

        // Przykład 6: Sets.filter(Set<E> unfiltered, Predicate<? super E> predicate)
        Set<String> filteredSet = Sets.filter(set1, s -> s.compareTo("B") > 0);
        System.out.println("Filtrowanie zbioru (elementy większe od B): " + filteredSet);

        // Przykład 7: Sets.cartesianProduct(Set<E> set1, Set<E> set2)
        Set<String> set3 = Sets.newHashSet("1", "2");
        Set<String> set4 = Sets.newHashSet("A", "B");
        Set<List<String>> cartesianProduct = Sets.cartesianProduct(set3, set4);
        System.out.println("Iloczyn kartezjański zbiorów: " + cartesianProduct);
    }
}