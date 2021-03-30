package com.devlusaja.intercorpmicroserviceclient.core.service.impl;

import com.devlusaja.intercorpmicroserviceclient.core.dto.ClientDTO;
import com.devlusaja.intercorpmicroserviceclient.core.entity.ClientEntity;
import com.devlusaja.intercorpmicroserviceclient.core.service.ClientService;
import com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2.ClientJPARepository;
import com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2.KpiJPARepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import static org.mockito.ArgumentMatchers.*;
import org.springframework.context.ApplicationEventPublisher;

class ClientServiceImplTest {

    private ClientJPARepository clientRepository = Mockito.mock(ClientJPARepository.class);
    private KpiJPARepository kpiRepository = Mockito.mock(KpiJPARepository.class);
    private ApplicationEventPublisher publisher = Mockito.mock(ApplicationEventPublisher.class);

    private ModelMapper modelMapper = new ModelMapper();
    private ClientEntity entity = new ClientEntity();
    private ClientDTO clientDTO = new ClientDTO();

    private ClientService service;

    @BeforeEach
    void setUp() {
        service = new ClientServiceImpl(clientRepository, kpiRepository, modelMapper, publisher);

        Mockito.when(clientRepository.save(any(ClientEntity.class))).thenReturn(entity);
    }

    @BeforeEach
    void setClientEntity() {
        entity.setUuid("56d0f086-6ddb-4a9e-a9e3-4d3e858c0ad3");
        entity.setName("Test");
        entity.setLastName("Test");
        entity.setAge(18);
        entity.setDateOfBirth("01/01/1920");
        entity.setUuid("01/01/1994");
    }

    @BeforeEach
    void setClientDTO() {
        clientDTO.setName("Test");
        clientDTO.setLastName("Test");
        clientDTO.setDateOfBirth("01/01/1920");
        clientDTO.setAge(18);
    }

    @Test
    @DisplayName("Este test verifica la creación de un cliente de manera exitosa")
    void createValidClient() throws Exception {
        assert service.createClient(clientDTO).getUuid().equals(entity.getUuid());
    }

    @Test
    @DisplayName("Este test valida que el sistema de un Exception si el campo dateOfBirth tiene un formato diferente a dd/MM/yyyy")
    void createInvalidDateOfBirthClient() {
        ClientDTO modifiedClientDTO = clientDTO;
        modifiedClientDTO.setDateOfBirth("01/01/199090");
        Assertions.assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
            service.createClient(modifiedClientDTO);
        });
    }

    @Test
    @DisplayName("Este test valida que el sistema de un Exception si el campo age tiene un valor menor a 1")
    void createInvalidAgeClient() {
        ClientDTO modifiedClientDTO = clientDTO;
        modifiedClientDTO.setAge(0);
        Assertions.assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
            service.createClient(modifiedClientDTO);
        });
    }
}