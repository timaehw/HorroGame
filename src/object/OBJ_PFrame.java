package object;

import javax.imageio.ImageIO;

public class OBJ_PFrame extends SuperObject {
    public OBJ_PFrame() {
        name = "PictureFrame";
        interactable = true;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/pictureframe.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
