package it.polimi.ingsw.bean.options;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class ChooseOptionsTest {

    private final ChooseOptions options = new ChooseOptions("juri", "", null);

    @Test
    public void isValid() {
        assertNull(options.isValid("y"));
        assertNull(options.isValid("yes"));
        assertNull(options.isValid("n"));
        assertNull(options.isValid("no"));
    }
}