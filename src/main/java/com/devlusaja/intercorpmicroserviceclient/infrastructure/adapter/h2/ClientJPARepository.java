package com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2;

import com.devlusaja.intercorpmicroserviceclient.core.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJPARepository extends CrudRepository<ClientEntity, String> {}
