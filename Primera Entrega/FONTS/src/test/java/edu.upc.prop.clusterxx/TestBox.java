package edu.upc.prop.clusterxx;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import edu.upc.prop.clusterxx.domain.Box;
import edu.upc.prop.clusterxx.domain.Region;
import edu.upc.prop.clusterxx.domain.Kenken;
import edu.upc.prop.clusterxx.domain.operations.*;

public class TestBox {
    @Mock
    Kenken kenkenMock;

    @Mock
    Region regionMock;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void creatorTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
    }

    @Test
    public void creatorWithValueTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0,6);
        assertNotNull(b);
    }

    @Test
    public void getXTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        int x = b.getX();
        assertEquals(x,0);
        assertNotEquals(x,5);

        b = new Box(kenkenMock,3,0);
        assertNotNull(b);
        x = b.getX();
        assertEquals(x,3);
        assertNotEquals(x,5);

        b = new Box(kenkenMock,8,0);
        assertNotNull(b);
        x = b.getX();
        assertEquals(x,8);
        assertNotEquals(x,5);
    }

    @Test
    public void getYTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        int y = b.getY();
        assertEquals(y,0);
        assertNotEquals(y,5);

        b = new Box(kenkenMock,0,4);
        assertNotNull(b);
        y = b.getY();
        assertEquals(y,4);
        assertNotEquals(y,5);

        b = new Box(kenkenMock,0,8);
        assertNotNull(b);
        y = b.getY();
        assertEquals(y,8);
        assertNotEquals(y,5);
    }

    @Test
    public void getValueTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        int v = b.getValue();
        assertEquals(v,-1);
        assertNotEquals(v,0);

        b = new Box(kenkenMock,0,0,6);
        assertNotNull(b);
        v = b.getValue();
        assertEquals(v,6);
        assertNotEquals(v,0);
        
        b = new Box(kenkenMock,0,0,1);
        assertNotNull(b);
        v = b.getValue();
        assertEquals(v,1);
        assertNotEquals(v,0);
        
        b = new Box(kenkenMock,0,0,8);
        assertNotNull(b);
        v = b.getValue();
        assertEquals(v,8);
        assertNotEquals(v,0);
    }

    @Test
    public void getPossibleValuesTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        Set<Integer> possibleValues = b.getPossibleValues();
        assertNotNull(possibleValues);
        b.setPossibleValue(3);
        b.setPossibleValue(6);
        b.setPossibleValue(8);
        possibleValues = b.getPossibleValues();
        assertNotNull(possibleValues);
        assertThat(possibleValues, hasItem(3));
        assertThat(possibleValues, hasItem(6));
        assertThat(possibleValues, hasItem(8));
        assertThat(possibleValues, not(hasItem(2)));
        b.setPossibleValue(8);
        b.removePossibleValue(8);
        possibleValues = b.getPossibleValues();
        assertNotNull(possibleValues);
        assertThat(possibleValues, hasItem(3));
        assertThat(possibleValues, hasItem(6));
        assertThat(possibleValues, not(hasItem(8)));
        assertThat(possibleValues, not(hasItem(2)));
    }

    @Test
    public void getRegionTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        Region r = b.getRegion();
        assertNull(r);
        b.setRegion(regionMock);
        r = b.getRegion();
        assertNotNull(r);
        assertSame(r,regionMock);
    }

    @Test
    public void getKenkenTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        Kenken k = b.getKenken();
        assertNotNull(k);
        assertSame(k, kenkenMock);
    }

    @Test
    public void isEmptyTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        assertTrue(b.isEmpty());
        b = new Box(kenkenMock,0,0,2);
        assertNotNull(b);
        assertFalse(b.isEmpty());
    }

    @Test
    public void setValueTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        assertTrue(b.setValue(3));
        int v = b.getValue();
        assertEquals(v,3);
        assertTrue(b.setValue(9));
        v = b.getValue();
        assertEquals(v,9);
    }

    @Test
    public void clearValueTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0,4);
        assertNotNull(b);
        assertEquals(b.getValue(),4);
        assertTrue(b.clearValue());
        assertEquals(b.getValue(),-1);
    }

    @Test
    public void setPossibleValueTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        assertTrue(b.setPossibleValue(3));
        assertTrue(b.setPossibleValue(5));
        assertTrue(b.setPossibleValue(8));
        Set<Integer> possibleValues = b.getPossibleValues();
        assertNotNull(b);
        assertThat(possibleValues, hasItem(3));
        assertThat(possibleValues, hasItem(5));
        assertThat(possibleValues, hasItem(8));
        assertThat(possibleValues, not(hasItem(4)));
        assertThat(possibleValues, not(hasItem(-1))); 
    }

    @Test
    public void clearPossibleValueTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        b.setPossibleValue(3);
        b.setPossibleValue(3);
        b.setPossibleValue(5);
        b.setPossibleValue(8);
        assertTrue(b.removePossibleValue(3));
        Set<Integer> possibleValues = b.getPossibleValues();
        assertNotNull(b);
        assertThat(possibleValues, not(hasItem(3)));
        assertThat(possibleValues, hasItem(5));
        assertThat(possibleValues, hasItem(8));
        assertThat(possibleValues, not(hasItem(4)));
        assertThat(possibleValues, not(hasItem(-1)));
        assertTrue(b.removePossibleValue(5));
        assertTrue(b.removePossibleValue(8));
        possibleValues = b.getPossibleValues();
        assertNotNull(b);
        assertThat(possibleValues, not(hasItem(4)));
        assertThat(possibleValues, not(hasItem(-1)));
        assertThat(possibleValues, not(hasItem(8)));
        assertThat(possibleValues, not(hasItem(5)));
    }

    @Test
    public void setRegionTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        assertTrue(b.setRegion(regionMock));
        assertSame(b.getRegion(),regionMock);
    }

    @Test
    public void removeRegionTest() {
        assertNotNull(kenkenMock);
        assertNotNull(regionMock);
        Box b = new Box(kenkenMock,0,0);
        assertNotNull(b);
        b.setRegion(regionMock);
        assertNotNull(b.getRegion());
        assertTrue(b.removeRegion());
        assertNull(b.getRegion());
    }
}

/* Class created by Guillem Nieto */
