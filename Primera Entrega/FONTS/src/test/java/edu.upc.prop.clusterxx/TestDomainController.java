package edu.upc.prop.clusterxx;

import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.MockedConstruction;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.upc.prop.clusterxx.domain.*;
import edu.upc.prop.clusterxx.domain.operations.*;
import edu.upc.prop.clusterxx.exceptions.*;

public class TestDomainController {
    @Mock
    Kenken kenkenMock;

    @Mock
    Region regionMock;

    @Mock
    Box boxMock;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreator() {
        DomainController DomainCntrl = new DomainController();
        assertNotNull(DomainCntrl);
    }

    @Test
    public void testCreateKenkenCorrectly() throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        String[] kenkenData =
        {
            "3 2",
            "1 12 6 1 1 [2] 1 2 [1] 2 1 2 2 [2] 3 1 3 2 [3]",
            "1 6 3 1 3 [3] 2 3 3 3"
        };
        
        DomainController DomainCntrl = spy(new DomainController());

        doReturn(kenkenMock).when(DomainCntrl).createNewKenken(anyInt());

        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);
        when(kenkenMock.addRegion(regionMock)).thenReturn(true);

        doNothing().when(kenkenMock).setState(Kenken.GameState.IN_EDITION);
        doNothing().when(kenkenMock).setType(Kenken.GameType.NORMAL);

        when(boxMock.setValue(anyInt())).thenReturn(true);

        boolean kenkenCreated = DomainCntrl.createKenken(kenkenData);

        assertTrue(kenkenCreated);

        assertNotNull(DomainCntrl.getCurrentKenken());
    }

    @Test(expected = NumberFormatException.class)
    public void testCreateKenkenInvalidNumber() throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        String[] kenkenData =
        {
            "3 abc", /* wrong format */ 
            "1 12 6 1 1 [2] 1 2 [1] 2 1 2 2 [2] 3 1 3 2 [3]",
            "1 6 3 1 3 [3] 2 3 3 3"
        };

        DomainController DomainCntrl = spy(new DomainController());

        doReturn(kenkenMock).when(DomainCntrl).createNewKenken(anyInt());

        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);
        when(kenkenMock.addRegion(regionMock)).thenReturn(true);

        doNothing().when(kenkenMock).setState(Kenken.GameState.IN_EDITION);
        doNothing().when(kenkenMock).setType(Kenken.GameType.NORMAL);

        when(boxMock.setValue(anyInt())).thenReturn(true);

        DomainCntrl.createKenken(kenkenData);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testCreateKenkenInvalidData() throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        String[] kenkenData =
        {
            "3 1",
            "1 12 6 1 1 [2] 1 2 [1] 2 1", /* lacks a pair of coords */
            "1 6 3 1 3 [3] 2 3 3 3"
        };

        DomainController DomainCntrl = spy(new DomainController());

        doReturn(kenkenMock).when(DomainCntrl).createNewKenken(anyInt());

        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);
        when(kenkenMock.addRegion(regionMock)).thenReturn(true);

        doNothing().when(kenkenMock).setState(Kenken.GameState.IN_EDITION);
        doNothing().when(kenkenMock).setType(Kenken.GameType.NORMAL);

        when(boxMock.setValue(anyInt())).thenReturn(true);

        DomainCntrl.createKenken(kenkenData);
    }

    @Test(expected = InvalidOperationIdException.class)
    public void testCreateKenkenInvalidOperation() throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        String[] kenkenData =
        {
            "3 2",
            "1 12 6 1 1 [2] 1 2 [1] 2 1 2 2 [2] 3 1 3 2 [3]",
            "9 6 3 1 3 [3] 2 3 3 3" /* 9 is not a valid op */ 
        };

        DomainController DomainCntrl = spy(new DomainController());

        doReturn(kenkenMock).when(DomainCntrl).createNewKenken(anyInt());

        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);
        when(kenkenMock.addRegion(regionMock)).thenReturn(true);

        doNothing().when(kenkenMock).setState(Kenken.GameState.IN_EDITION);
        doNothing().when(kenkenMock).setType(Kenken.GameType.NORMAL);

        when(boxMock.setValue(anyInt())).thenReturn(true);

        DomainCntrl.createKenken(kenkenData);
    }

    @Test(expected = InvalidKenkenSizeException.class)
    public void testCreateKenkenInvalidSize() throws NumberFormatException, ArrayIndexOutOfBoundsException, InvalidOperationIdException, InvalidKenkenSizeException {
        String[] kenkenData =
        {
            "2 2", /* 2 is not a valid kenken size */ 
            "1 3 2 1 1 [2] 1 2 [1]",
            "1 3 2 2 1 [1] 2 2"
        };

        DomainController DomainCntrl = spy(new DomainController());

        doThrow(new InvalidKenkenSizeException()).when(DomainCntrl).createNewKenken(2);

        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);
        when(kenkenMock.addRegion(regionMock)).thenReturn(true);

        doNothing().when(kenkenMock).setState(Kenken.GameState.IN_EDITION);
        doNothing().when(kenkenMock).setType(Kenken.GameType.NORMAL);

        when(boxMock.setValue(anyInt())).thenReturn(true);

        DomainCntrl.createKenken(kenkenData);
    }

    @Test
    public void testGetCurrentKenken() {
        DomainController DomainCntrl = new DomainController();

        int[][] board = DomainCntrl.getCurrentKenken();
        assertNull(board);

        DomainCntrl.assignKenken(kenkenMock);

        when(kenkenMock.getSize()).thenReturn(3);
        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);

        when(boxMock.getValue()).thenReturn(1);

        board = DomainCntrl.getCurrentKenken();
        assertNotNull(board);
    }

    @Test
    public void testPlayGame() throws NullKenkenException {
        DomainController DomainCntrl = new DomainController();

        DomainCntrl.assignKenken(kenkenMock);

        doNothing().when(kenkenMock).setState(Kenken.GameState.IN_GAME);

        boolean gamePlayeable = DomainCntrl.playGame();

        assertTrue(gamePlayeable);
    }

    @Test(expected = NullKenkenException.class)
    public void testPlayGameNullKenken() throws NullKenkenException {
        DomainController DomainCntrl = new DomainController();

        DomainCntrl.playGame();
    }

    @Test
    public void testLeaveGame() {
        DomainController DomainCntrl = new DomainController();

        DomainCntrl.leaveGame();

        int[][] board = DomainCntrl.getCurrentKenken();

        assertNull(board);
    }

    @Test
    public void testSolveKenken() throws NullKenkenException, InvalidGameStateException {
        DomainController DomainCntrl = new DomainController();

        DomainCntrl.assignKenken(kenkenMock);

        when(kenkenMock.getState()).thenReturn(Kenken.GameState.IN_GAME);
        when(kenkenMock.getSize()).thenReturn(3);
        when(kenkenMock.getBox(anyInt(), anyInt())).thenReturn(boxMock);
        when(kenkenMock.checkBox(boxMock)).thenReturn(true);
        
        when(boxMock.isEmpty()).thenReturn(true);
        when(boxMock.setValue(anyInt())).thenReturn(true);
        when(boxMock.clearValue()).thenReturn(true);

        boolean hasSolution = DomainCntrl.solveKenken();
        assertTrue(hasSolution);

        when(kenkenMock.checkBox(boxMock)).thenReturn(false);

        hasSolution = DomainCntrl.solveKenken();
        assertFalse(hasSolution);
    }

    @Test(expected = NullKenkenException.class)
    public void testSolveNullKenken() throws NullKenkenException, InvalidGameStateException {
        DomainController DomainCntrl = new DomainController();

        DomainCntrl.solveKenken();
    }

    @Test(expected = InvalidGameStateException.class)
    public void testSolveNotPlayableKenken() throws NullKenkenException, InvalidGameStateException {
        DomainController DomainCntrl = new DomainController();

        DomainCntrl.assignKenken(kenkenMock);

        when(kenkenMock.getState()).thenReturn(Kenken.GameState.IN_EDITION);

        DomainCntrl.solveKenken();
    }
}

// Class created by Pau Zaragoza Gallardo