package com.devlusaja.intercorpmicroserviceclient.core.service.impl;

import com.devlusaja.intercorpmicroserviceclient.core.entity.ClientEntity;
import com.devlusaja.intercorpmicroserviceclient.core.dto.ClientDTO;
import com.devlusaja.intercorpmicroserviceclient.core.dto.KpiDTO;
import com.devlusaja.intercorpmicroserviceclient.core.event.CreateClientEvent;
import com.devlusaja.intercorpmicroserviceclient.core.service.ClientService;
import com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2.ClientJPARepository;
import com.devlusaja.intercorpmicroserviceclient.infrastructure.adapter.h2.KpiJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements ClientService {

    // Source of information: https://data.worldbank.org/indicator/SP.DYN.LE00.MA.IN
    private final int LIFE_EXPECTANCY_ON_PERU = 74;
    private final static String FORMAT_BIRTHDAY_INVALID = "El campo dateOfBirth debe tener el formato dd/MM/yyyy";
    private final static String FORMAT_AGE_INVALID = "El campo age debe ser mayor o igual a 1";

    @Autowired
    private final ClientJPARepository clientAdapter;

    @Autowired
    private final KpiJPARepository kpiAdapter;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    public ClientServiceImpl(ClientJPARepository clientAdapter, KpiJPARepository kpiAdapter, ModelMapper modelMapper, ApplicationEventPublisher publisher) {
        this.clientAdapter = clientAdapter;
        this.kpiAdapter = kpiAdapter;
        this.modelMapper = modelMapper;
        this.publisher = publisher;
    }

    public ClientDTO createClient(ClientDTO newClientDTO) throws Exception {
        // Validation Rules
        validateDateOfBirthFormat(newClientDTO.getDateOfBirth());
        validateAgeFormat(newClientDTO.getAge());

        ClientEntity entity = modelMapper.map(newClientDTO, ClientEntity.class);
        entity.setUuid(UUID.randomUUID().toString());
        entity.setProbableDateOfDeath(calculateProbableDateOfDeath(entity));

        ClientEntity entitySaved = clientAdapter.save(entity);

        publisher.publishEvent(new CreateClientEvent(entitySaved));

        return modelMapper.map(entitySaved, ClientDTO.class);
    }

    public KpiDTO clientKpi() {
        final KpiDTO[] schema = {null};
        kpiAdapter.findAll().forEach( kpiEntity -> {
            schema[0] = modelMapper.map(kpiEntity, KpiDTO.class);
        });
        return schema[0];
    }

    public List<ClientDTO> clientList() {
        final List<ClientDTO> schemaList = new ArrayList<>();
        clientAdapter.findAll().forEach( clientEntity -> {
            ClientDTO client = modelMapper.map(clientEntity, ClientDTO.class);
            schemaList.add(client);
        });
        return schemaList;
    }

    /***
     * This method calculates the possible date of death based on life expectancy in Peru.
     * @param entity
     * @return
     * @throws ParseException
     */
    private String calculateProbableDateOfDeath(ClientEntity entity) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateOfBirth = df.parse(entity.getDateOfBirth());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfBirth);
        calendar.add(Calendar.YEAR, LIFE_EXPECTANCY_ON_PERU);
        Date dateOfProbableDateOfDeath = calendar.getTime();
        return df.format(dateOfProbableDateOfDeath);
    }

    /***
     * We validate the date format for data consistency
     * @param birthday
     * @throws Exception
     */
    private void validateDateOfBirthFormat(String birthday) throws Exception {
        Pattern pattern = Pattern.compile("^([0-9]{2}\\/[0-9]{2}\\/[0-9]{4})$");
        Matcher matcher = pattern.matcher(birthday);
        boolean matchFound = matcher.find();
        if (!matchFound) {
            throw new Exception(FORMAT_BIRTHDAY_INVALID);
        }
    }

    /***
     * We validate the age to not allow values less than or equal to 0
     * @param age
     * @throws Exception
     */
    private void validateAgeFormat(int age) throws Exception {
        if (age < 1) {
            throw new Exception(FORMAT_AGE_INVALID);
        }
    }
}
