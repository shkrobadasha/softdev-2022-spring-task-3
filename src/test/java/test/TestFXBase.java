package test;

import com.example.battleship.Main;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

public class TestFXBase extends ApplicationTest {

    @BeforeEach
    public void setUpClass() throws Exception {
        ApplicationTest.launch(Main.class);
    }
}

