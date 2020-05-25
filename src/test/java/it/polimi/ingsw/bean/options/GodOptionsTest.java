package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.utilities.MessageType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GodOptionsTest {

    GodOptions godOptions;
    List<GodDescription> godsList;

    @Before
    public void setUp() throws Exception {
        godsList = new ArrayList<>(Arrays.asList(
                GodDescription.ATHENA,
                GodDescription.APOLLO,
                GodDescription.ARTEMIS,
                GodDescription.MINOTAUR,
                GodDescription.HESTIA
        ));
        godOptions = new GodOptions("nickname", godsList, MessageType.CHOOSE_GOD);
    }

    @After
    public void tearDown() throws Exception {
        godsList = null;
        godOptions = null;
    }

    @Test
    public void isValid() {
        String mockUpInput = "Athena, Apollo, Artemis";
        assertNull(godOptions.isValid(mockUpInput));
        mockUpInput = "A t H e N a , A p o l l o, MINOTAUR";
        assertNull(godOptions.isValid(mockUpInput));
        mockUpInput = "John, Jason, Paul";
        assertTrue(godOptions.isValid(mockUpInput).contains("Not valid input:"));
        mockUpInput = "paul";
        assertTrue(godOptions.isValid(mockUpInput).contains("Not valid input:"));

        godOptions.getGodsToChoose().remove(0);
        godOptions.getGodsToChoose().remove(0);
        mockUpInput = "paul";
        assertTrue(godOptions.isValid(mockUpInput).contains("Not valid input:"));
        mockUpInput = "John, Jason, Paul";
        assertTrue(godOptions.isValid(mockUpInput).contains("Not valid input:"));
        mockUpInput = "Hestia";
        assertNull(godOptions.isValid(mockUpInput));

    }

}