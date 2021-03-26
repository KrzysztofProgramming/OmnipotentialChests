package wg.omnipotentialchests.chests.omnipotentialchests.managers;

import lombok.Getter;
import wg.omnipotentialchests.chests.omnipotentialchests.engine.listener.SpinListener;

public class ListenersManager{

    @Getter
    private SpinListener spinListener = new SpinListener();

    public void init(){
        this.spinListener.init();
    }
}
