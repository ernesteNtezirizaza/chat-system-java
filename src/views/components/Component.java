package views.components;

import utils.CommonUtil;
import utils.ConsoleColor;

import java.util.Locale;

public class Component {
    public static void pageTitleView(String title) {
        title = title.toUpperCase(Locale.ROOT);
        CommonUtil.clearScreen();
        CommonUtil.useColor(ConsoleColor.BoldColor.GREEN_BOLD);
        CommonUtil.addTabs(20, true);
        CommonUtil.useColor(ConsoleColor.UnderlineColor.GREEN_UNDERLINED);

        System.out.println(title);
        CommonUtil.resetColor();
    }
}