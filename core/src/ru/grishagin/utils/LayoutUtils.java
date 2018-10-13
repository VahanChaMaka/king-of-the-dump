package ru.grishagin.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;

/**
 * Created by Admin on 23.02.2018.
 */
public class LayoutUtils {
    private static int TEXT_BUTTON_WIDTH = 100;
    private static int TEXT_BUTTON_HEIGHT = 30;
    private static final int TREE_ICON_SIZE = 15;

    public static void applyButtonSize(Cell<Button> cell){
        cell.width(TEXT_BUTTON_WIDTH).height(TEXT_BUTTON_HEIGHT);
    }

    public static void applyTreeIconSize(Tree tree){
        tree.getStyle().plus.setMinWidth(TREE_ICON_SIZE);
        tree.getStyle().plus.setMinHeight(TREE_ICON_SIZE);
        tree.getStyle().minus.setMinWidth(TREE_ICON_SIZE);
        tree.getStyle().minus.setMinHeight(TREE_ICON_SIZE);
    }
}
