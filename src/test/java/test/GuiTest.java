package test;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class GuiTest extends TestFXBase {

    public static final String START_BUTTON_ID = "#startGame";
    public static final String SELECT_LABEL_ID = "#select";
    public static final String TEXT_FIELD_ONE_ID = "#firatnamefiels";
    public static final String TEXT_FIELD_TWO_ID = "#secondnamefield";
    public static final String CHECK_BOX_ID = "#gamemode";
    public static final String LINCORN_LABEL_ID = "#lincorn";
    public static final String CRUISER_LABEL_ID = "#cruiser";
    public static final String DESTROYER_LABEL_ID = "#destroyer";
    public static final String SUBMARINE_LABEL_ID = "#submarine";
    public static final String MINE_LABEL_ID = "#mine";
    public static final String NEXT_BUTTON_ID = "#nextButton";
    public static final String STATE_BUTTON_ID = "#stateButton";


    @Test
    public void verifySceneStart() {
        clickOn(START_BUTTON_ID);// Для теста уведомления
        setupStartScene();

        verifyThat(SELECT_LABEL_ID, LabeledMatchers.hasText("Select game mode"));
        verifyThat(TEXT_FIELD_ONE_ID, TextInputControlMatchers.hasText("sample1"));
        verifyThat(TEXT_FIELD_TWO_ID, TextInputControlMatchers.hasText("sample2"));
        verifyThat(START_BUTTON_ID, LabeledMatchers.hasText("Start"));
    }

    @Test
    public void verifySceneSetup() {
        setupStartScene();
        clickOn(START_BUTTON_ID);

        verifyThat(LINCORN_LABEL_ID, LabeledMatchers.hasText("Lincorn"));
        verifyThat(CRUISER_LABEL_ID, LabeledMatchers.hasText("Cruiser"));
        verifyThat(DESTROYER_LABEL_ID, LabeledMatchers.hasText("Destroyer"));
        verifyThat(SUBMARINE_LABEL_ID, LabeledMatchers.hasText("Submarine"));
        verifyThat(MINE_LABEL_ID, LabeledMatchers.hasText("Mine"));

        verifyThat(NEXT_BUTTON_ID, LabeledMatchers.hasText("Next"));
        verifyThat(STATE_BUTTON_ID, LabeledMatchers.hasText("Remove"));
    }

    private void setupStartScene() {
        clickOn(TEXT_FIELD_ONE_ID).write("sample1");
        clickOn(TEXT_FIELD_TWO_ID).write("sample2");
        clickOn(CHECK_BOX_ID).clickOn("Classic");
    }

}