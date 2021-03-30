package com.devlusaja.intercorpmicroserviceclient.presentation.controller;

import com.devlusaja.intercorpmicroserviceclient.core.dto.ClientDTO;
import com.devlusaja.intercorpmicroserviceclient.core.dto.KpiDTO;
import com.devlusaja.intercorpmicroserviceclient.core.service.ClientService;
import com.devlusaja.intercorpmicroserviceclient.presentation.request.ClientRequest;
import com.devlusaja.intercorpmicroserviceclient.presentation.response.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

    @Autowired
    protected final ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/doc")
    @ApiOperation("Redirige a la documentación de Swagger")
    public ModelAndView redirectToSwaggerUI(ModelMap model) {
        return new ModelAndView("redirect:/swagger-ui/", model);
    }

    @PostMapping("/creacliente")
    @ApiOperation("Crea un nuevo cliente y retorna la data creada")
    public Response createClient(
            @ApiParam("Información para crear un nuevo cliente")
            @RequestBody ClientRequest clientRequest) {
        Response response = new Response();
        try {
            ClientDTO clientDTO = modelMapper.map(clientRequest, ClientDTO.class);
            ClientDTO data = clientService.createClient(clientDTO);
            response.setData(data);
        } catch (Exception e) {
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/kpideclientes")
    @ApiOperation("Muestra la media y desviación estandar de los clientes")
    public Response clientKpi() {
        Response response = new Response();
        try {
            KpiDTO data = clientService.clientKpi();
            response.setData(data);
        } catch (Exception e) {
            response.setError(e.getMessage());
        }
        return response;
    }

    @GetMapping("/listclientes")
    @ApiOperation("Lista todos los clientes registrados")
    public Response clientList() {
        Response response = new Response();
        try {
            List<ClientDTO> data = clientService.clientList();
            response.setData(data);
        } catch (Exception e) {
            response.setError(e.getMessage());
        }
        return response;
    }
}
