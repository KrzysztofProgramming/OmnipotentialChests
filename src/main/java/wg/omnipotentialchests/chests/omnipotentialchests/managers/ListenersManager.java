package wg.omnipotentialchests.chests.omnipotentialchests.managers;

import lombok.Getter;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.base.SpinListener;
import wg.omnipotentialchests.chests.omnipotentialchests.managers.chat.ChatManager;

public class ListenersManager {

    @Getter
    private final SpinListener spinListener = new SpinListener();
    @Getter
    private final ChestsManager chestsManager = new ChestsManager();
    @Getter
    private final ChatManager chatManager = new ChatManager();

    public void init() {
        this.spinListener.init();
        this.chestsManager.init();
        this.chatManager.init();
    }

    public void disable(){
        this.chestsManager.disable();
    }
}
