package wg.omnipotentialchests.chests.omnipotentialchests.managers;

import lombok.Getter;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.ChestsManager;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.base.SpinListener;

public class ListenersManager {

    @Getter
    private final SpinListener spinListener = new SpinListener();
    @Getter
    private final ChestsManager chestsManager = new ChestsManager();

    public void init() {
        this.spinListener.init();
        this.chestsManager.init();
    }

    public void disable(){
        this.chestsManager.disable();
    }
}
