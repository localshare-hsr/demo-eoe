package ch.hsr.epj.localshare.demo.gui.presentation;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

public class StartupViewTest extends ApplicationTest {

  Scene scene;


  @Override
  public void start(Stage stage) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(
                getClass().getClassLoader().getResource("fxml/StartupView.fxml")));
    Scene localScene = new Scene(root, 800, 600);
    scene = localScene;
    stage.setScene(localScene);
    stage.show();
    stage.toFront();
  }

  @Before
  public void setUp() throws Exception{}

  @After
  public void tearDown() throws Exception{
    FxToolkit.hideStage();
    release(new KeyCode[]{});
    release(new MouseButton[]{});
  }


  @Test
  public void contains_finish_button() {
    FxAssert.verifyThat("#finishButton", hasText("Finish and start Application"));
  }

  @Test
  public void contains_changeDir_button() {
    FxAssert.verifyThat("#changeDirectoryButton", hasText("Change Directory"));
  }

  @Test
  public void contains_hello_label() {
    FxAssert.verifyThat("#helloLabel", hasText("Hello and Welcome to LocalShare"));
  }

  @Test
  public void enter_valid_displayName() {
    Button finishButton = (Button) scene.lookup("#finishButton");

    clickOn("#friendlyNameText");
    write("Dominique");
    assertTrue(finishButton.isVisible());
    assertFalse(finishButton.isDisabled());
  }

  @Test
  public void enter_empty_displayName() {
    Button finishButton = (Button) scene.lookup("#finishButton");
    clickOn("#friendlyNameText");
    write("");
    assertTrue(finishButton.isVisible());
    assertTrue(finishButton.isDisabled());
  }

  @Test
  public void save_data_on_finish() {
    clickOn("#friendlyNameText");
    write("Martin");
    clickOn("#finishButton");
  }


}