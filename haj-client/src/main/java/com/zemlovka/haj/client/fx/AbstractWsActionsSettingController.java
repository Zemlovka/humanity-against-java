package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.LobbyWSActions;


public abstract class AbstractWsActionsSettingController {
    protected LobbyWSActions wsActions;
    void setWsActions(LobbyWSActions wsActions) {
        this.wsActions = wsActions;
    }
}
