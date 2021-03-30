package com.devlusaja.intercorpmicroserviceclient.core.service;

import com.devlusaja.intercorpmicroserviceclient.core.dto.ClientDTO;
import com.devlusaja.intercorpmicroserviceclient.core.dto.KpiDTO;

import java.util.List;

public interface ClientService {

    ClientDTO createClient(ClientDTO newClientDTO) throws Exception;
    KpiDTO clientKpi();
    List<ClientDTO> clientList();
}
