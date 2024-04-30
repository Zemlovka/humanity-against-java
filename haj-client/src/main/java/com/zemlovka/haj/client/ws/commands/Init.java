package com.zemlovka.haj.client.ws.commands;

import com.zemlovka.haj.utils.AbstractCommand;
import com.zemlovka.haj.utils.dto.AcceptDTO;
import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.client.InitDTO;


public class Init extends AbstractCommand<InitDTO, AcceptDTO> {
    @Override
    public AcceptDTO run(InitDTO argument) {
        return null;
    }
}
