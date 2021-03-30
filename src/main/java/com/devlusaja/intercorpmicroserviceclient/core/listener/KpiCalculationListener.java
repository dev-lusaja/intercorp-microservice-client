package com.devlusaja.intercorpmicroserviceclient.core.listener;

import com.devlusaja.intercorpmicroserviceclient.core.entity.KpiEntity;
import com.devlusaja.intercorpmicroserviceclient.core.event.CreateClientEvent;
import com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2.ClientJPARepository;
import com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2.KpiJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KpiCalculationListener {

    @Autowired
    protected final KpiJPARepository kpiAdapter;

    @Autowired
    protected final ClientJPARepository clientAdapter;

    public KpiCalculationListener(KpiJPARepository kpiAdapter, ClientJPARepository clientAdapter) {
        this.kpiAdapter = kpiAdapter;
        this.clientAdapter = clientAdapter;
    }

    @EventListener
    public void handlerKpiCalculationListener(CreateClientEvent event) {
        List<Integer> ages = new ArrayList<>();
        clientAdapter.findAll().forEach(clientEntity -> {
            ages.add(clientEntity.getAge());
        });
        float median = median(ages);
        double variance = variance(ages, median);
        double standardDeviation = Math.sqrt(variance);

        KpiEntity kpiEntity = new KpiEntity();
        kpiEntity.setStandardDeviation(standardDeviation);
        kpiEntity.setAverageAge(median);

        kpiAdapter.save(kpiEntity);
    }

    protected float median(List<Integer> ages) {
        float sum = 0;
        for (final int age: ages) {
            sum += age;
        }
        return sum / ages.size();
    }

    protected double variance(List<Integer> ages, float median) {
        double variance = 0;
        for (final int age: ages) {
            variance = variance + (Math.pow(age - median, 2f));
        }
        return variance / (float) ages.size();
    }
}