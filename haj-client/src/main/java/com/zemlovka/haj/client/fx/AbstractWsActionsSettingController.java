package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.LobbyWSActions;

/**
 * Abstract class for setting WSActions to controllers
 */
public abstract class AbstractWsActionsSettingController {
    protected LobbyWSActions wsActions;
    void setWsActions(LobbyWSActions wsActions) {
        this.wsActions = wsActions;
    }
}
