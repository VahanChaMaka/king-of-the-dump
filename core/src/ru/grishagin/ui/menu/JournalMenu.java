package ru.grishagin.ui.menu;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import ru.grishagin.GameModel;
import ru.grishagin.quest.Quest;
import ru.grishagin.quest.QuestController;
import ru.grishagin.quest.QuestStage;
import ru.grishagin.ui.Utils.AssetManager;
import ru.grishagin.ui.Utils.LayoutUtils;

/**
 * Created by Admin on 07.08.2018.
 */
public class JournalMenu extends BasicMenu {

    public JournalMenu() {
        super();
        createMainLayout();
    }

    @Override
    protected Table createLeftPanel() {
        Table layout = super.createLeftPanel();

        Tree questTree = new Tree(AssetManager.getInstance().getDefaultSkin());
        LayoutUtils.applyTreeIconSize(questTree);

        QuestController questController = GameModel.getInstance().getQuestController();
        for (Quest quest : questController.getActiveQuests()) {
            questTree.add(makeOneQuest(quest));
        }

        ScrollPane scrollPane = new ScrollPane(questTree);
        layout.add(scrollPane).expand().fill();
        return layout;
    }

    private Tree.Node makeOneQuest(Quest quest){
        Tree.Node questNode = new Tree.Node(new Label(quest.getTitle(), AssetManager.getInstance().getDefaultSkin())); //main node with quest name

        //draw all complete and currently active stages
        for (QuestStage stage : quest.getStages()) {
            Container container = new Container();
            Label label = new Label(stage.toString(), AssetManager.getInstance().getDefaultSkin());
            label.setWrap(true);
            container.setActor(label);
            container.fill();
            questNode.add(new Tree.Node(container));
            if(!stage.isComplete()){
                break;
            }
        }

        return questNode;
    }


}
