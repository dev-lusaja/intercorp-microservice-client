package com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2;

import com.devlusaja.intercorpmicroserviceclient.core.entity.KpiEntity;
import org.springframework.data.repository.CrudRepository;

public interface KpiJPARepository extends CrudRepository<KpiEntity, Long> { }
