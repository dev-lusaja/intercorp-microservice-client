package com.devlusaja.intercorpmicroserviceclient.core.event;

import com.devlusaja.intercorpmicroserviceclient.core.entity.ClientEntity;

public class CreateClientEvent {

    private ClientEntity entity;

    public CreateClientEvent(ClientEntity entity) {
        this.entity = entity;
    }

    public ClientEntity getEntity() {
        return entity;
    }

    public void setEntity(ClientEntity entity) {
        this.entity = entity;
    }
}
