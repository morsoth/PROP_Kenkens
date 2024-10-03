package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import edu.upc.prop.clusterxx.domain.*;
import edu.upc.prop.clusterxx.domain.operations.*;

public class TestOperations {
    @Mock
    Box boxMock1, boxMock2;

    @Before
    public void initMocks() {
	    MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddition() {
        Addition add = new Addition();
        assertNotNull(add);
        
        Box[] boxes = new Box[4];
        Arrays.fill(boxes, boxMock1);
        when(boxMock1.getValue()).thenReturn(2);
        int result = add.calculate(boxes); // 2+2+2+2 = 8
        assertEquals(8, result);
    }

    @Test
    public void testSubstraction() {
        Substraction sub = new Substraction();
        assertNotNull(sub);

        Box[] boxes = new Box[4];
        Arrays.fill(boxes, boxMock1);
        when(boxMock1.getValue()).thenReturn(2);
        int result = sub.calculate(boxes); // more than 2 boxes!!!
        assertEquals(-1, result);

        boxes = new Box[2];
        boxes[0] = boxMock1; boxes[1] = boxMock2;
        when(boxMock1.getValue()).thenReturn(3);
        when(boxMock2.getValue()).thenReturn(7);
        result = sub.calculate(boxes); // 7-3 = 4
        assertEquals(4, result);
    }

    @Test
    public void testMultiplication() {
        Multiplication mul = new Multiplication();
        assertNotNull(mul);

        Box[] boxes = new Box[4];
        Arrays.fill(boxes, boxMock1);
        when(boxMock1.getValue()).thenReturn(2);
        int result = mul.calculate(boxes); // 2*2*2*2 = 16
        assertEquals(16, result);
    }

    @Test
    public void testDivision() {
        Division div = new Division();
        assertNotNull(div);

        Box[] boxes = new Box[4];
        Arrays.fill(boxes, boxMock1);
        when(boxMock1.getValue()).thenReturn(2);
        int result = div.calculate(boxes); // more than 2 boxes!!!
        assertEquals(-1, result);

        boxes = new Box[2];
        boxes[0] = boxMock1; boxes[1] = boxMock2;
        when(boxMock1.getValue()).thenReturn(4);
        when(boxMock2.getValue()).thenReturn(8);
        result = div.calculate(boxes); // 8/4 = 2
        assertEquals(2, result);
    }

    @Test
    public void testModulus() {
        Modulus mod = new Modulus();
        assertNotNull(mod);

        Box[] boxes = new Box[4];
        Arrays.fill(boxes, boxMock1);
        when(boxMock1.getValue()).thenReturn(2);
        int result = mod.calculate(boxes); // more than 2 boxes!!!
        assertEquals(-1, result);

        boxes = new Box[2];
        boxes[0] = boxMock1; boxes[1] = boxMock2;
        when(boxMock1.getValue()).thenReturn(3);
        when(boxMock2.getValue()).thenReturn(5);
        result = mod.calculate(boxes); // 5%3 = 2
        assertEquals(2, result);
    }

    @Test
    public void testPower() {
        Power pow = new Power();
        assertNotNull(pow);

        Box[] boxes = new Box[4];
        Arrays.fill(boxes, boxMock1);
        when(boxMock1.getValue()).thenReturn(2);
        int result = pow.calculate(boxes); // more than 2 boxes!!!
        assertEquals(-1, result);

        boxes = new Box[2];
        boxes[0] = boxMock1; boxes[1] = boxMock2;
        when(boxMock1.getValue()).thenReturn(2);
        when(boxMock2.getValue()).thenReturn(3);
        result = pow.calculate(boxes); // 3² = 9
        assertEquals(9, result);
    }
}

// Class created by Pau Zaragoza Gallardo