package pl.michalpolom.collections;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListTest {


    /**
     * <h1>ArrayList</h1>
     * Dynamiczna tablica, która może zmieniać swój rozmiar.<p>
     * Szybki dostęp do elementów, ponieważ indeksy są oparte na numerach.<p>
     * Wstawianie i usuwanie elementów może być wolniejsze, ponieważ może wymagać przesunięcia innych elementów.<p>
     * Zwykle preferowany, gdy często potrzebujemy uzyskać dostęp do elementów.
     */
    @Test
    void arrayList() {

        final var names = new ArrayList<String>();

        names.add("Alice");
        names.add("Bob");
        names.add(0, "John");

        assertAll(
                () -> assertTrue(names.contains("John")),

                // GET
                () -> assertEquals("John", names.get(0)),
                () -> assertEquals("Alice", names.get(1)),
                () -> assertEquals("Bob", names.get(2)),

                // null
                () -> assertDoesNotThrow(() -> names.add(null))
        );
    }


    /**
     * <h1>List.of()</h1>
     * Lista utworzona za pomocą List.of() jest niemodyfikowalna.<p>
     * Nie dopuszcza wartości null.<p>
     * Lista jest bezpieczna dla wielowątkowości - jej stan nie może zostać zmieniony po utworzeniu.
     */
    @Test
    void listOf() {

        final var names = List.of("John", "Alice", "Bob");

        assertAll(
                () -> assertThrows(Exception.class, () -> names.add("Marcin")),
                () -> assertThrows(Exception.class, () -> List.of(null))
        );
    }


    /**
     * <h1>LinkedList <i>implements Set, Deque (Queue)</i></h1>
     * Lista dwukierunkowa, w której każdy element ma wskaźnik na poprzedni i następny element.<p>
     * Wstawianie i usuwanie elementów jest szybsze, ponieważ wymaga tylko aktualizacji wskaźników.<p>
     * Dostęp do elementów jest wolniejszy, ponieważ wymaga przejścia przez listę od początku lub końca.<p>
     * Zwykle preferowany, gdy często wstawiamy lub usuwamy elementy.
     */
    @Test
    void linkedList() {

        final var names = new LinkedList<String>();

        names.add("Alice");
        names.addLast("Bob");
        names.addFirst("John");

        assertAll(
                () -> assertTrue(names.contains("John")),

                // GET
                () -> assertEquals("John", names.getFirst()),
                () -> assertEquals("Alice", names.get(1)),
                () -> assertEquals("Bob", names.getLast()),

                // PEEK (get)
                () -> assertEquals("John", names.peek()),
                () -> assertEquals("John", names.peekFirst()),
                () -> assertEquals("Bob", names.peekLast()),

                // POLL (get and remove)
                () -> assertEquals("John", names.poll()),
                () -> assertEquals("Alice", names.pollFirst()),
                () -> assertEquals("Bob", names.pollLast()),

                // SIZE
                () -> assertEquals(0, names.size()),

                // PEEK, POLL, GET, POP - DIFFERENCE
                /** difference
                 pop():
                     * Jest to metoda z interfejsu Deque.
                     * Usuwa i zwraca pierwszy element z listy (początek kolejki).
                     * Rzuca wyjątek NoSuchElementException, gdy lista jest pusta.
                 poll():
                     * Jest to metoda z interfejsu Queue.
                     * Usuwa i zwraca pierwszy element z listy (początek kolejki).
                     * Zwraca null, gdy lista jest pusta.
                 peek():
                     * Jest to metoda z interfejsu Queue.
                     * Zwraca pierwszy element z listy (początek kolejki) bez usuwania go.
                     * Zwraca null, gdy lista jest pusta.
                 get(int index):
                     * Jest to metoda z interfejsu List.
                     * Zwraca element na podanej pozycji index w liście bez usuwania go.
                     * Rzuca wyjątek IndexOutOfBoundsException, gdy indeks jest poza zakresem listy (indeks < 0 lub indeks >= rozmiar listy).
                 */
                () -> assertEquals(null, names.peekFirst()),
                () -> assertEquals(null, names.pollFirst()),
                () -> assertThrows(NoSuchElementException.class, () -> names.pop()),
                () -> assertThrows(NoSuchElementException.class, () -> names.getFirst()),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> names.get(0)),

                // null
                () -> assertDoesNotThrow(() -> names.add(null))

                // REMOVE
                //() -> assertEquals(null, names.remove())
        );
    }

    /**
     * <h1>Vector</h1>
     * Podobny do ArrayList, ale jest zsynchronizowany, co oznacza, że jest bezpieczny dla wątków (ang. thread-safe).<p>
     * Zwykle jest wolniejszy od ArrayList z powodu narzutu synchronizacji.<p>
     * Dzisiaj rzadziej używany niż ArrayList, ponieważ większość programistów wybiera struktury danych niezsynchronizowane i zarządza synchronizacją samodzielnie, jeśli jest to konieczne.<p>
     */
    @Test
    void vector() {
        final var names = new Vector<String>();

        names.add("Alice");
        names.add("Bob");
        names.add(0, "John");

        assertAll(
                () -> assertTrue(names.contains("John")),

                // GET
                () -> assertEquals("John", names.get(0)),
                () -> assertEquals("Alice", names.get(1)),
                () -> assertEquals("Bob", names.get(2)),

                // null
                () -> assertDoesNotThrow(() -> names.add(null))
        );
    }

    /**
     * <h1>Lists from Guava</h1>
     * Jest to klasa pomocnicza zawierająca różne metody do tworzenia i manipulowania listami
     */
    @Test
    void lists() {
        // 1. Tworzenie ArrayList
        ArrayList<String> arrayList = Lists.newArrayList("Element 1", "Element 2", "Element 3");

        // 2. Tworzenie LinkedList
        LinkedList<String> linkedList = Lists.newLinkedList(arrayList);

        // 3. Tworzenie listy z określonym początkowym rozmiarem
        ArrayList<Integer> arrayListWithCapacity = Lists.newArrayListWithCapacity(5);
        arrayListWithCapacity.addAll(Arrays.asList(1, 2, 3, 4, 5));
        arrayListWithCapacity.add(11);

        // 4. Tworzenie listy z określonym oczekiwanym rozmiarem
        ArrayList<Integer> arrayListWithExpectedSize = Lists.newArrayListWithExpectedSize(5);
        arrayListWithExpectedSize.addAll(Arrays.asList(6, 7, 8, 9, 10));
        arrayListWithExpectedSize.add(11);

        // 5. Dzielenie listy na podlisty
        List<List<String>> partitioned = Lists.partition(arrayList, 2);
        System.out.println("Podzielona lista: " + partitioned);

        // 6. Odwracanie listy
        List<String> reversed = Lists.reverse(arrayList);
        System.out.println("Odwrócona lista: " + reversed);

        // 7. Produkt kartezjański
        var cartesianList = Lists.cartesianProduct(arrayList, arrayList);
        System.out.println("Produkt kartezjański: " + cartesianList);

        /** difference
         Różnice między tymi metodami są następujące:
         Lists.newArrayListWithCapacity(int initialCapacity):
             * Tworzy ArrayList z określoną początkową pojemnością (liczbą elementów, które może pomieścić bez zmiany rozmiaru).
             * Jest użyteczna, gdy znana jest dokładna liczba elementów, które będą dodane do listy, ponieważ można zminimalizować koszt zmiany rozmiaru listy.
             * Jeśli lista przekroczy swoją początkową pojemność, zostanie automatycznie powiększona, ale będzie to miało wpływ na wydajność.

         Lists.newArrayListWithExpectedSize(int expectedSize):
             * Tworzy ArrayList z oczekiwanym rozmiarem, który jest szacunkową liczbą elementów, które zostaną dodane do listy.
             * Wewnętrznie oblicza optymalną początkową pojemność na podstawie oczekiwanego rozmiaru, co prowadzi do lepszego wykorzystania pamięci.
             * Jest użyteczna, gdy liczba elementów do dodania do listy jest tylko szacunkowa, a nie dokładna.
         */
    }
}