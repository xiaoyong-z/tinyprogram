package client.gui.util;

import javax.swing.*;
import java.awt.*;

public class frameUtil {

    public static void setCenter(JFrame jf) {
        Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
        Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
        int screenWidth = screenSize.width / 2; // 获取屏幕的宽
        int screenHeight = screenSize.height / 2; // 获取屏幕的高
        int height = jf.getHeight();
        int width = jf.getWidth();
        jf.setLocation(screenWidth - width / 2, screenHeight - height / 2);
    }
}
