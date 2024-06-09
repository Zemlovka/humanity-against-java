package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.WSActions;

/**
 * Abstract class for setting WSActions to controllers
 */
public abstract class AbstractWsActionsSettingController {
    protected WSActions wsActions;
    void setWsActions(WSActions wsActions) {
        this.wsActions = wsActions;
    }
}
