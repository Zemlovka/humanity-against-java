module com.zemlovka.jah.utils {

    requires com.fasterxml.jackson.databind;
    requires reflections;
    exports com.zemlovka.haj.utils;
    exports com.zemlovka.haj.utils.dto;
    exports com.zemlovka.haj.utils.dto.client;
    exports com.zemlovka.haj.utils.dto.server;
    opens com.zemlovka.haj.utils;
    opens com.zemlovka.haj.utils.dto;
    opens com.zemlovka.haj.utils.dto.server;
    opens com.zemlovka.haj.utils.dto.client;
    exports com.zemlovka.haj.utils.dto.secondary;
}