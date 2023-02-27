import controller.Controller;
import model.Model;
import view.ViewC;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller(new Model(), new ViewC());
        controller.processingSubtitlesWords();
    }

}


