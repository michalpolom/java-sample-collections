package pl.michalpolom.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QueueTest {

    /**
     * <h1>PriorityQueue</h1>
     * Przechowuje elementy w kolejności określonej przez ich priorytet. <p>
     * Priorytet jest określany na podstawie naturalnego porządku elementów (dla obiektów implementujących Comparable) lub za pomocą zewnętrznego komparatora. <p>
     */
    @Test
    void priorityQueue() {

        final var names = new PriorityQueue<>();

        names.add("Bob");
        names.add("Alice");
        names.add("John");
        names.add("Zbigniew");

        assertAll(
                () -> assertTrue(names.contains("John")),

                // PEEK (get)
                () -> assertEquals("Alice", names.peek()),
                () -> assertEquals("Alice", names.peek()),

                // POLL (get and remove)
                () -> assertEquals("Alice", names.poll()),
                () -> assertEquals("Bob", names.poll()),
                () -> assertEquals("John", names.poll()),
                () -> assertEquals("Zbigniew", names.poll()),

                // SIZE
                () -> assertEquals(0, names.size()),

                // null
                () -> assertThrows(NullPointerException.class, () -> names.add(null))
        );
    }

    /**
     * <h1>PriorityQueue - part 2</h1>
     */
    @Test
    void priorityQueueWithComparator() {

//        final var names = new PriorityQueue<String>((s1, s2) -> s2.compareTo(s1));
        final var names = new PriorityQueue<String>(Comparator.reverseOrder());

        names.add("Bob");
        names.add("Alice");
        names.add("John");
        names.add("Zbigniew");

        assertAll(
                () -> assertTrue(names.contains("John")),

                // PEEK (get)
                () -> assertEquals("Zbigniew", names.peek()),
                () -> assertEquals("Zbigniew", names.peek()),

                // POLL (get and remove)
                () -> assertEquals("Zbigniew", names.poll()),
                () -> assertEquals("John", names.poll()),
                () -> assertEquals("Bob", names.poll()),
                () -> assertEquals("Alice", names.poll()),

                // SIZE
                () -> assertEquals(0, names.size())
        );
    }

    /**
     * <h1>ArrayDeque</h1>
     * Zapewnia efektywne dodawanie i usuwanie elementów na obu końcach deque.<p>
     */
    @Test
    void arrayDeque() {

        final var names = new ArrayDeque<>();

        names.add("Alice");
        names.addLast("Bob");
        names.addFirst("John");

        assertAll(
                () -> assertTrue(names.contains("John")),

                // GET
                () -> assertEquals("John", names.getFirst()),
                //() -> assertEquals("Alice", names.get(1)),                                    // <-- Różnica z LinkedList
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
                //() -> assertThrows(IndexOutOfBoundsException.class, () -> names.get(0)),      // <-- Różnica z LinkedList

                // null
                () -> assertThrows(NullPointerException.class, () -> names.add(null))           // <-- Różnica z LinkedList

                // REMOVE
                //() -> assertEquals(null, names.remove())
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
        // in ListTest
    }


    /**
     * <h1>Queues from Guava</h1>
     * Jest to klasa pomocnicza zawierająca różne metody do tworzenia i manipulowania kolejkami
     */
    @Test
    void queues() {
        //Queues.
    }

}