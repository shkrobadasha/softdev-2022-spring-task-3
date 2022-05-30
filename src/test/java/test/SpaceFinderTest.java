package test;

import com.example.battleship.controller.util.SpaceFinder;
import com.example.battleship.controller.util.UsedIndexFinder;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceFinderTest {

    @Test
    public void findSpaceTest() {
        ArrayList<Boolean> list = new ArrayList<>() {{
            //5x5
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, true, true, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
            addAll(List.of(new Boolean[]{false, false, false, false, false}));
        }};

        ArrayList<Integer> result = SpaceFinder.findSpace(list, 5, 5, 5, 4, true);
        ArrayList<Integer> expected = new ArrayList<>() {{
            add(5);
            add(10);
            add(15);
            add(20);
        }};
        assertEquals(expected, result);


        result = SpaceFinder.findSpace(list, 5, 5, 5, 4, false);
        expected = new ArrayList<>();
        assertEquals(expected, result);

        result = SpaceFinder.findSpace(list, 5, 5, 5, 2, false);
        expected = new ArrayList<>(){{
            add(5);
            add(6);
        }};
        assertEquals(expected, result);

        result = SpaceFinder.findSpace(list, 15, 5, 5, 3, true);
        expected = new ArrayList<>();
        assertEquals(expected, result);

        result = SpaceFinder.findSpace(list, 18, 5, 5, 4, false);
        expected = new ArrayList<>();
        assertEquals(expected, result);
    }

}
