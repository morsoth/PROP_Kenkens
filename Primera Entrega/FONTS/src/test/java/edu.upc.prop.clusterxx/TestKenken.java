package edu.upc.prop.clusterxx;

import org.junit.Test;
import org.junit.Before;
import java.util.List;
import java.util.ArrayList;

import edu.upc.prop.clusterxx.domain.Kenken;
import edu.upc.prop.clusterxx.domain.Kenken.*;
import edu.upc.prop.clusterxx.domain.Box;
import edu.upc.prop.clusterxx.domain.Region;
import edu.upc.prop.clusterxx.domain.operations.*;
import edu.upc.prop.clusterxx.exceptions.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.*;

public class TestKenken {
    @Mock
    Region rMock, rMockk;

    @Mock
    Box bMock, bMockk, bMock1, bMock2, bMock3;
    
    @Before
    public void initMocks() {
	MockitoAnnotations.initMocks(this);
    }

    private Box[][] boxMockMatrix(int size) {
	Box[][] ms = new Box[size][size];
	for (int x = 0; x < size; ++x) {
	    for (int y = 0; y < size; ++y) {
		ms[x][y] = bMock;
	    }
	}
	return ms;
    }
    
    @Test
    public void test_createKenken() {
	Kenken k = new Kenken(5);
	assertEquals(5, k.getSize());
	assertNotNull(k);
    }

    @Test(expected=InvalidKenkenSizeException.class)
    public void test_createKenkenIncorrect() {
	Kenken k = new Kenken(10);
    }
    
    @Test
    public void test_getID_setID() {
	Kenken k = new Kenken(3);
	long old_id = k.getID();
	k.setID(old_id+1);
	long new_id = k.getID();
	assertNotEquals(old_id, new_id);
	assertEquals(new_id, old_id+1);
    }

    @Test
    public void test_getSize() {
	Kenken k = new Kenken(5);
	int size = k.getSize();
	assertEquals(size, 5);
    }
    
    @Test
    public void test_getType_setType() {
	Kenken k = new Kenken(7);
	GameType old_type = k.getType();
	k.setType(GameType.RANKED);
	GameType new_type = k.getType();
	assertFalse(old_type == new_type);
	assertTrue(new_type == GameType.RANKED);
    }
    
    @Test
    public void test_getState_setState() {
	Kenken k = new Kenken(4);
	GameState old_state = k.getState();
	k.setState(GameState.IN_GAME);
	GameState new_state = k.getState();
	assertFalse(old_state == new_state);
	assertTrue(new_state == GameState.IN_GAME);
    }
    
    @Test
    public void test_KenkenType() {
	Kenken k = new Kenken(9, GameType.DAILY);
	assertNotNull(k);
	assertTrue(k.getType() == GameType.DAILY);
    }

    @Test
    public void test_getBox() {
	int invalid = 4, valid = 3;
	Kenken k = new Kenken(invalid);
	Box b = k.getBox(invalid, invalid);
	assertNull(b);
	b = k.getBox(-1, -3);
	assertNull(b);
	b = k.getBox(valid, valid);
	assertNotNull(b);
	Box bb = k.getBox(valid, valid);
	assertSame(b, bb);
    }

    @Test
    public void test_getBoxes() {
	int size = 6;
	Kenken k = new Kenken(size);
	Box[] bs = k.getBoxes();
	assertEquals(bs.length, size*size);
	for (int x = 0; x < size; ++x) {
	    for (int y = 0; y < size; ++y) {
		assertSame(k.getBox(x, y), bs[(size*x)+y]);
	    }
	}
    }

    @Test
    public void test_getBoxesCoords() {
	Kenken k = new Kenken(5);
	Box[] bs = k.getBoxes(new int[]{0, 0, 1, 1, 3, 4});

	assertNotNull(bs);
	assertEquals(bs.length, 3);
	assertNull(k.getBoxes(new int[]{-1, 4}));
	assertNull(k.getBoxes(new int[]{5, 5}));
	assertNull(k.getBoxes(new int[]{3}));
	assertNull(k.getBoxes(new int[]{}));
    }
    
    @Test
    public void test_fillBox() {
	when(bMock.setValue(anyInt())).thenReturn(true);
	Kenken k = new Kenken(boxMockMatrix(3));

	assertTrue(k.fillBox(0, 0, 1));
	assertFalse(k.fillBox(0, 0, -1));
	assertFalse(k.fillBox(2, 9, 4));
	assertFalse(k.fillBox(-2, 3, 4));
	assertFalse(k.fillBox(2, 3, 4));
	assertTrue(k.fillBox(2, 2, 1));
    }
    
    @Test
    public void test_clearBox() {
	when(bMock.clearValue()).thenReturn(true);
	Kenken k = new Kenken(boxMockMatrix(5));

	assertTrue(k.clearBox(0,0));
	assertFalse(k.clearBox(-1,8));
	assertFalse(k.clearBox(5,6));
	assertTrue(k.clearBox(1,1));
    }

    @Test
    public void test_isBoardFull() {
	when(bMock.isEmpty()).thenReturn(false);
	Box[][] ms = boxMockMatrix(4);
    	Kenken k = new Kenken(ms);

	assertTrue(k.isBoardFull());

	ms[3][3] = bMockk;
	when(bMockk.isEmpty()).thenReturn(true);

	assertFalse(k.isBoardFull());

	when(bMock.isEmpty()).thenReturn(true);

	assertFalse(k.isBoardFull());
    }

    @Test
    public void test_addRegion() {
	Kenken k = new Kenken(8);
	List<Region> rs = k.getRegions();

	assertFalse(k.addRegion(null));

	Box[] ms = new Box[]{ bMock, bMock };
	when(rMock.getBoxList()).thenReturn(ms);
	when(bMock.getRegion()).thenReturn(null);
	when(bMock.setRegion(rMock)).thenReturn(true);

	assertTrue(k.addRegion(rMock));
	assertTrue(rs.contains(rMock));
	assertFalse(k.addRegion(rMock));

	Box[] msk = new Box[]{ bMock, bMockk };
	when(rMock.getBoxList()).thenReturn(ms);
	when(bMock.setRegion(rMockk)).thenReturn(true);
	when(rMockk.getBoxList()).thenReturn(msk);
	when(bMockk.getRegion()).thenReturn(rMockk);
	when(bMockk.setRegion(rMockk)).thenReturn(true);

	assertFalse(k.addRegion(rMockk));
	assertFalse(rs.contains(rMockk));
	
	msk[0] = bMockk;

	when(bMockk.getRegion()).thenReturn(null);

	assertTrue(k.addRegion(rMockk));
	assertTrue(rs.contains(rMockk));
    }

    @Test
    public void test_removeRegion() {
	Kenken k = new Kenken(boxMockMatrix(4));
	Box[] ms = new Box[]{ bMock, bMock };
	List<Region> rs = k.getRegions();
	when(rMock.getBoxList()).thenReturn(ms);
	when(bMock.removeRegion()).thenReturn(true);
	when(bMock.setRegion(rMock)).thenReturn(true);

	when(bMock.getRegion()).thenReturn(null);
	k.addRegion(rMock);
	when(bMock.getRegion()).thenReturn(rMock);
	
	assertFalse(k.removeRegion(-1,1));
	assertFalse(k.removeRegion(4,1));
	assertTrue(k.removeRegion(1,1));
	assertTrue(rs.isEmpty());
	assertFalse(rs.contains(rMock));
    }

    @Test
    public void test_getRegions() {
	Kenken k = new Kenken(boxMockMatrix(3));
	Box[] ms = new Box[]{ bMock, bMock };
	List<Region> rs = k.getRegions();

	when(rMock.getBoxList()).thenReturn(ms);
	when(bMock.getRegion()).thenReturn(null);
	when(bMock.setRegion(rMock)).thenReturn(true);
	when(bMock.removeRegion()).thenReturn(true);
	
	k.addRegion(rMock);
	assertNotNull(rs);
	assertEquals(1, rs.size());
	assertTrue(rs.contains(rMock));

	when(rMockk.getBoxList()).thenReturn(ms);
	when(bMock.setRegion(rMockk)).thenReturn(true);
	
	k.addRegion(rMockk);
	assertNotNull(rs);
	assertEquals(2, rs.size());
	assertTrue(rs.contains(rMock));
	assertTrue(rs.contains(rMockk));
    }

    @Test
    public void test_checkRowsCols() {
	Box[][] ms = boxMockMatrix(3);
	Kenken k = new Kenken(ms);
	when(bMock.isEmpty()).thenReturn(true);
	when(bMock1.isEmpty()).thenReturn(false);
	when(bMock2.isEmpty()).thenReturn(false);
	when(bMock3.isEmpty()).thenReturn(false);
	when(bMock1.getValue()).thenReturn(1);
	when(bMock2.getValue()).thenReturn(2);
	when(bMock3.getValue()).thenReturn(3);
	
	assertFalse(k.checkRowsCols(1,1));
	ms[1][1] = bMock2;
	assertTrue(k.checkRowsCols(1,1));
	ms[0][1] = bMock2;
	assertFalse(k.checkRowsCols(1,1));
	assertFalse(k.checkRowsCols(1,1,2));
	ms[0][1] = bMock;
	assertTrue(k.checkRowsCols(1,1));
	ms[0][1] = bMock1;
	ms[0][2] = bMock3;
	ms[1][0] = bMock1;
	ms[2][0] = bMock3;
	assertTrue(k.checkRowsCols(1,1));
	assertTrue(k.checkRowsCols(1,1,2));
	ms[1][1] = bMock1;
	ms[2][2] = bMock1;
	assertFalse(k.checkRowsCols(1,2));
	assertTrue(k.checkRowsCols(2,2));
	assertTrue(k.checkRowsCols(2,2,1));
    }

    @Test
    public void test_checkRegion() {
	Box[][] ms = boxMockMatrix(9);
	Kenken k = new Kenken(ms);
	when(bMock.isEmpty()).thenReturn(false);
	when(bMock.getRegion()).thenReturn(rMock);
	when(rMock.isFull()).thenReturn(true);
	when(rMock.check()).thenReturn(true);

	assertFalse(k.checkRegion(9,9));
	assertFalse(k.checkRegion(-1,-2));
	assertTrue(k.checkRegion(5,7));

	when(bMock.isEmpty()).thenReturn(true);
	when(bMock.setValue(anyInt())).thenReturn(true);

	assertTrue(k.checkRegion(4,8));
	assertTrue(k.checkRegion(4,8,2));
	assertFalse(k.checkRegion(4,8,-2));
	assertFalse(k.checkRegion(4,8,10));

	when(rMock.check()).thenReturn(false);

	assertFalse(k.checkRegion(4,8));
    }

    @Test
    public void test_checkBox() {
	Box[][] ms = boxMockMatrix(3);
	ms[0][0] = bMock3;
	ms[0][1] = bMock1;
	ms[0][2] = bMock2;
	ms[1][0] = bMock1;
	ms[2][0] = bMock2;
	Box[] mss = new Box[]{ bMock1, bMock2, bMock3 };
	
	Kenken k = new Kenken(ms);
	when(bMock.isEmpty()).thenReturn(true);
	when(bMock.getRegion()).thenReturn(null);
	
	when(bMock1.isEmpty()).thenReturn(false);
	when(bMock2.isEmpty()).thenReturn(false);
	when(bMock3.isEmpty()).thenReturn(false);
	when(bMock1.getValue()).thenReturn(1);
	when(bMock2.getValue()).thenReturn(2);
	when(bMock3.getValue()).thenReturn(3);
	when(bMock1.getRegion()).thenReturn(rMock);
	when(bMock2.getRegion()).thenReturn(rMock);
	when(bMock3.getRegion()).thenReturn(rMock);

	when(rMock.isFull()).thenReturn(true);
	when(rMock.check()).thenReturn(true);

	assertFalse(k.checkBox(3,0));
	assertFalse(k.checkBox(0,-1));
	assertTrue(k.checkBox(0,0));
	assertFalse(k.checkBox(0,0,1));
	assertTrue(k.checkBox(0,1));
	assertFalse(k.checkBox(1,1));

	when(bMock1.getX()).thenReturn(0);
	when(bMock1.getY()).thenReturn(0);

	assertTrue(k.checkBox(bMock1));
    }

    @Test
    public void test_checkBoard() {
	Box[][] ms = boxMockMatrix(3);
	Kenken k = new Kenken(ms);
	when(bMock.isEmpty()).thenReturn(true);
	when(bMock.getRegion()).thenReturn(null);

	assertFalse(k.checkBoard());

	when(bMock1.getValue()).thenReturn(1);
	when(bMock2.getValue()).thenReturn(2);
	when(bMock3.getValue()).thenReturn(3);
	when(bMock1.isEmpty()).thenReturn(false);
	when(bMock2.isEmpty()).thenReturn(false);
	when(bMock3.isEmpty()).thenReturn(false);
	when(bMock1.getRegion()).thenReturn(null);
	when(bMock2.getRegion()).thenReturn(null);
	when(bMock3.getRegion()).thenReturn(null);
	when(bMock1.setRegion(rMock)).thenReturn(true);
	when(bMock2.setRegion(rMock)).thenReturn(true);
	when(bMock3.setRegion(rMock)).thenReturn(true);
	ms[1][0] = ms[0][1] = ms[2][2] = bMock1;
	ms[2][0] = ms[1][1] = ms[0][2] = bMock2;
	ms[0][0] = ms[2][1] = ms[1][2] = bMock3;
	Box[] mss = new Box[]{ bMock1, bMock2, bMock3 };
	when(rMock.getBoxList()).thenReturn(mss);
	when(rMock.isFull()).thenReturn(true);
	when(rMock.check()).thenReturn(true);

	k.addRegion(rMock); 

	when(bMock1.getRegion()).thenReturn(rMock);
	when(bMock2.getRegion()).thenReturn(rMock);
	when(bMock3.getRegion()).thenReturn(rMock);

	assertTrue(k.checkBoard());
    }
}
