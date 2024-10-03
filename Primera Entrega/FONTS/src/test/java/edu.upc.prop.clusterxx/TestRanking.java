package edu.upc.prop.clusterxx;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.upc.prop.clusterxx.domain.Ranking;

public class TestRanking {
    @Test
    public void createRankingCorrectly() {
        Ranking r = new Ranking();
        assertNotNull(r);
    }

    @Test
    public void checkRankingSize() {
        Ranking r = new Ranking();
        int size = r.getSize();
        assertEquals(size, 0);
    }

    @Test
    public void checkRankingAdd() {
        Ranking r = new Ranking();
        r.add("Alice", 100);
        int size = r.getSize();
        assertEquals(size, 1);
    }

    @Test
    public void checkRankingSort() {
        Ranking r = new Ranking();
        r.add("Alice", 100);
        r.add("Bob", 200);
        r.add("Charlie", 50);
        r.sort();
        assertEquals(r.getRanking().get(0).getFirst(), "Bob");
        assertEquals(r.getRanking().get(1).getFirst(), "Alice");
        assertEquals(r.getRanking().get(2).getFirst(), "Charlie");
    }

    @Test
    public void checkRankingDelete() {
        Ranking r = new Ranking();
        r.add("Alice", 100);
        r.add("Bob", 200);
        r.add("Charlie", 50);
        r.deleteUser("Bob");
        int size = r.getSize();
        assertEquals(size, 2);
    }

    @Test
    public void checkRankingSetPoints() {
        Ranking r = new Ranking();
        r.add("Alice", 100);
        r.add("Bob", 20);
        r.add("Charlie", 50);
        r.sort();
        r.setPoints("Bob", 250);
        r.sort();
        assertEquals(r.getRanking().get(0).getFirst(), "Bob");
        assertEquals(r.getRanking().get(0).getSecond(), 250);
    }

    @Test
    public void checkRankingUpdatePoints() {
        Ranking r = new Ranking();
        r.add("Alice", 100);
        r.add("Bob", 20);
        r.add("Charlie", 50);
        r.sort();
        r.updatePoints("Bob", 250);
        r.sort();
        assertEquals(r.getRanking().get(0).getFirst(), "Bob");
        assertEquals(r.getRanking().get(0).getSecond(), 270);
    }



}
