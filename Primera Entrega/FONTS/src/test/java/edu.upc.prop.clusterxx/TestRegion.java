package edu.upc.prop.clusterxx;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import edu.upc.prop.clusterxx.domain.Box;
import edu.upc.prop.clusterxx.domain.Region;
import edu.upc.prop.clusterxx.domain.Kenken;
import edu.upc.prop.clusterxx.domain.operations.*;



public class TestRegion {
    private int INT_MAX_VALUE = 2147483647; //2,147,483,647

    @Mock
    Kenken kenkenMock;

    @Mock
    Box boxMock;

    @Mock
    Operation operationMock;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void creatorTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        when(operationMock.calculate(boxes)).thenReturn(10);
        Region r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
    }

    @Test
    public void creatorWithResultTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        Region r = new Region(kenkenMock, boxes, operationMock,23);
        assertNotNull(r);
    }

    @Test
    public void checkTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);

        /* Tests using default creator */
        when(operationMock.calculate(boxes)).thenReturn(23);
        Region r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
        boolean test = r.check();
        assertTrue(test);
        when(operationMock.calculate(boxes)).thenReturn(10);
        test = r.check();
        assertFalse(test);

        when(operationMock.calculate(boxes)).thenReturn(1);
        r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
        test = r.check();
        assertTrue(test);

        when(operationMock.calculate(boxes)).thenReturn(INT_MAX_VALUE);
        r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
        test = r.check();
        assertTrue(test);

        /* Tests using creator with result */
        r = new Region(kenkenMock, boxes, operationMock, 23);
        when(operationMock.calculate(boxes)).thenReturn(23);
        assertNotNull(r);
        test = r.check();
        assertTrue(test);
        when(operationMock.calculate(boxes)).thenReturn(10);
        test = r.check();
        assertFalse(test);

        when(operationMock.calculate(boxes)).thenReturn(1);
        r = new Region(kenkenMock, boxes, operationMock, 1);
        assertNotNull(r);
        test = r.check();
        assertTrue(test);

        when(operationMock.calculate(boxes)).thenReturn(INT_MAX_VALUE);
        r = new Region(kenkenMock, boxes, operationMock, INT_MAX_VALUE);
        assertNotNull(r);
        test = r.check();
        assertTrue(test);
    }

    @Test
    public void getResultTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);

        /* Tests using default creator */
        when(operationMock.calculate(boxes)).thenReturn(34);
        Region r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
        int result = r.getResult();
        assertEquals(result,34);
        assertNotEquals(result,20);

        when(operationMock.calculate(boxes)).thenReturn(1);
        r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
        result = r.getResult();
        assertEquals(result,1);
        assertNotEquals(result,20);

        when(operationMock.calculate(boxes)).thenReturn(INT_MAX_VALUE);
        r = new Region(kenkenMock, boxes, operationMock);
        assertNotNull(r);
        result = r.getResult();
        assertEquals(result,INT_MAX_VALUE);
        assertNotEquals(result,20);

        /* Tests using creator with result */
        r = new Region(kenkenMock, boxes, operationMock, 23);
        assertNotNull(r);
        result = r.getResult();
        assertEquals(result,23);
        assertNotEquals(result,20);

        r = new Region(kenkenMock, boxes, operationMock, 1);
        assertNotNull(r);
        result = r.getResult();
        assertEquals(result,1);
        assertNotEquals(result,20);

        r = new Region(kenkenMock, boxes, operationMock, INT_MAX_VALUE);
        assertNotNull(r);
        result = r.getResult();
        assertEquals(result,INT_MAX_VALUE);
        assertNotEquals(result,20);
    }

    @Test
    public void getBoxListTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        Region r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        Box[] new_boxes = r.getBoxList();
        assertNotNull(new_boxes);
        assertSame(boxes, new_boxes);
        assertEquals(new_boxes.length,3);

        r = new Region(kenkenMock, null, operationMock, 10);
        assertNotNull(r);
        new_boxes = r.getBoxList();
        assertNull(new_boxes);
    }

    @Test
    public void getKenkenTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        Region r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        Kenken k = r.getKenken();
        assertSame(kenkenMock, k);

        r = new Region(null, boxes, operationMock, 10);
        assertNotNull(r);
        k = r.getKenken();
        assertNull(k);
    }

    @Test
    public void getOperationTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        Region r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        Operation op = r.getOperation();
        assertSame(operationMock, op);

        r = new Region(kenkenMock, boxes, null, 10);
        assertNotNull(r);
        op = r.getOperation();
        assertNull(op);
    }

    @Test
    public void getSizeTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        Region r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        int size = r.getSize();
        assertEquals(size, 3);

        boxes = new Box[1];
        r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        size = r.getSize();
        assertEquals(size, 1);

        boxes = new Box[81];
        r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        size = r.getSize();
        assertEquals(size, 81);
    }

    @Test
    public void isFullTest() {
        assertNotNull(kenkenMock);
        assertNotNull(boxMock);
        assertNotNull(operationMock);
        Box[] boxes = new Box[3];
        Arrays.fill(boxes, boxMock);
        Region r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        when(boxMock.isEmpty()).thenReturn(false);
        boolean test = r.isFull();
        assertTrue(test);

        r = new Region(kenkenMock, boxes, operationMock, 10);
        assertNotNull(r);
        when(boxMock.isEmpty()).thenReturn(true);
        test = r.isFull();
        assertFalse(test);
    }
}

/* Class created by Guillem Nieto */
