package com.zemlovka.haj.client.fx.controllers;

import com.zemlovka.haj.client.ws.WSActions;

/**
 * Abstract class for setting WSActions to controllers.
 * @see WSActions
 */
public abstract class AbstractWsActionsSettingController {
    protected WSActions wsActions;

    /**
     *  Sets the WSActions for the controller
     *
     * @param wsActions wsAction instance to set
     */
    public void setWsActions(WSActions wsActions) {
        this.wsActions = wsActions;
    }
}
