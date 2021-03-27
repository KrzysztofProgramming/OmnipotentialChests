package wg.omnipotentialchests.chests.omnipotentialchests.managers;

import lombok.Getter;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.base.SpinListener;

public class ListenersManager {

    @Getter
    private final SpinListener spinListener = new SpinListener();

    public void init() {
        this.spinListener.init();
    }
}
