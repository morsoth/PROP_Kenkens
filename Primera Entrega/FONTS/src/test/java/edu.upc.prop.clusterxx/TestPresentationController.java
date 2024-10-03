package edu.upc.prop.clusterxx;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.io.*;

import edu.upc.prop.clusterxx.domain.DomainController;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.exceptions.*;
import java.util.Scanner;

public class TestPresentationController {
    @Mock
    DomainController DomainCntrlMock;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void creatorTest() {
        assertNotNull(DomainCntrlMock);
        PresentationController PresentationCntrl = new PresentationController();
        assertNotNull(PresentationCntrl);
    }

    @Test
    public void readKenkenTest() throws InvalidOperationIdException, NullKenkenException {
        assertNotNull(DomainCntrlMock);
        
        String data = "3 2\n 1 12 6 1 1 1 2 2 1 2 2 3 1 3 2\n 1 6 3 1 3 2 3 3 3\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); int R = in.nextInt(); in.nextLine();
        String[] kenken = new String[R+1];
        kenken[0] = String.valueOf(N) + " " + String.valueOf(R);
        for (int i = 1; i <= R; ++i) {
            kenken[i] = in.nextLine();
        }

        PresentationController PresentationCntrl = new PresentationController(DomainCntrlMock);
        assertNotNull(PresentationCntrl);

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        when(DomainCntrlMock.createKenken(kenken)).thenReturn(true);
        when(DomainCntrlMock.playGame()).thenReturn(true);
        assertTrue(PresentationCntrl.readKenken());

        System.setIn(new ByteArrayInputStream(data.getBytes()));
        when(DomainCntrlMock.createKenken(kenken)).thenReturn(false);
        when(DomainCntrlMock.playGame()).thenReturn(true);
        assertFalse(PresentationCntrl.readKenken());
    }

    @Test
    public void showKenkenTest() {
        int kenken[][] = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertNotNull(DomainCntrlMock);
        PresentationController PresentationCntrl = new PresentationController(DomainCntrlMock);
        assertNotNull(PresentationCntrl);
        when(DomainCntrlMock.getCurrentKenken()).thenReturn(kenken);
        assertTrue(PresentationCntrl.showKenken());
    }

    @Test
    public void solveTest() throws NullKenkenException, InvalidGameStateException {
        assertNotNull(DomainCntrlMock);
        PresentationController PresentationCntrl = new PresentationController(DomainCntrlMock);
        assertNotNull(PresentationCntrl);
        when(DomainCntrlMock.solveKenken()).thenReturn(true);
        assertTrue(PresentationCntrl.solve());
        when(DomainCntrlMock.solveKenken()).thenReturn(false);
        assertFalse(PresentationCntrl.solve());
    }
}
