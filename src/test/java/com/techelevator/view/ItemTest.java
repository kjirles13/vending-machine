package com.techelevator.view;

import com.techelevator.Item;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest {
    @Test
            public void typeOf_Candy_ShouldReturn_AccuratePhrase() {
        Item item = new Item("candy", 3.05, "Chip", 5);
        String actualOutput = item.getPhrase();
        String expectedOutput = "Crunch Crunch, It's Yummy!";

        Assert.assertEquals(actualOutput, expectedOutput);
    }
}
