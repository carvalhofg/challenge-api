package com.api.challenge.challengeapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.api.challenge.challengeapi.config.security.TokenService;
import com.api.challenge.challengeapi.dto.DetailServiceOrderDTO;
import com.api.challenge.challengeapi.dto.OrderFollowUpDTO;
import com.api.challenge.challengeapi.dto.ServiceOrderDTO;
import com.api.challenge.challengeapi.form.OrderFollowUpForm;
import com.api.challenge.challengeapi.form.ServiceOrderForm;
import com.api.challenge.challengeapi.form.UpdateServiceOrderForm;
import com.api.challenge.challengeapi.model.OrderFollowUp;
import com.api.challenge.challengeapi.model.ServiceOrder;
import com.api.challenge.challengeapi.repository.CustomerRepository;
import com.api.challenge.challengeapi.repository.OrderFollowUpRepository;
import com.api.challenge.challengeapi.repository.ResponsibleRepository;
import com.api.challenge.challengeapi.repository.ServiceOrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/service-order")
public class SeviceOrderController {

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ResponsibleRepository responsibleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderFollowUpRepository orderFollowUpRepository;

    @Autowired
    private TokenService tokenService;

    @ApiOperation(value = "List All Orders")
    @GetMapping("/all")
    public List<DetailServiceOrderDTO> list() {
 
        List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
        return DetailServiceOrderDTO.convert(serviceOrders);

    }

    @GetMapping
    @ApiOperation(value = "List Orders From Logged Responsible")
    public List<DetailServiceOrderDTO> listByResponsible(HttpServletRequest request) {
        String token = tokenService.retrieveToken(request);
        Long idUsuario = tokenService.getUserId(token);
        String authenticatedResponsible = responsibleRepository.getOne(idUsuario).getName();

        if(authenticatedResponsible != null){
            List<ServiceOrder> serviceOrders = serviceOrderRepository.findByResponsibleName(authenticatedResponsible);

            return DetailServiceOrderDTO.convert(serviceOrders);
        } else {
                throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "NO PERMISSION"
              );
        }
    }

    @ApiOperation(value = "Create a Service Order")
    @PostMapping
    @Transactional
    public ResponseEntity<ServiceOrderDTO> createServiceOrder(@RequestBody @Valid ServiceOrderForm form, UriComponentsBuilder uriBuilder) {
        ServiceOrder serviceOrder = form.convert(customerRepository, responsibleRepository);
        serviceOrderRepository.save(serviceOrder);

        URI uri = uriBuilder.path("/service-order/{id}").buildAndExpand(serviceOrder.getId()).toUri();
        return ResponseEntity.created(uri).body(new ServiceOrderDTO(serviceOrder));
    }

    @ApiOperation(value = "Create a Service Order FollowUp")
    @PostMapping("/{idServiceOrder}/followup")
    @Transactional
    public ResponseEntity<OrderFollowUpDTO> createFollowUp(@PathVariable Long idServiceOrder, @RequestBody @Valid OrderFollowUpForm form, UriComponentsBuilder uriBuilder) {
        ServiceOrder serviceOrder = serviceOrderRepository.getOne(idServiceOrder);
        OrderFollowUp orderFollowUp = form.convert(serviceOrder);

        orderFollowUpRepository.save(orderFollowUp);

        URI uri = uriBuilder.path("/topicos/{idTopico}/resposta/{idResposta}").buildAndExpand(serviceOrder.getId(), orderFollowUp.getId()).toUri();
        return ResponseEntity.created(uri).body(new OrderFollowUpDTO(orderFollowUp));
    }

    @ApiOperation(value = "Update a Service Order")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetailServiceOrderDTO> updateServiceOrder(@PathVariable Long id, @RequestBody @Valid UpdateServiceOrderForm form, HttpServletRequest request) {
        Optional<ServiceOrder> optional = serviceOrderRepository.findById(id);
        String token = tokenService.retrieveToken(request);
        Long idUsuario = tokenService.getUserId(token);
        String authenticatedResponsible = responsibleRepository.getOne(idUsuario).getName();
        String responsibleFromOrder = serviceOrderRepository.getOne(id).getResponsible().getName();

        if(optional.isPresent() && (authenticatedResponsible.equals(responsibleFromOrder))) {
            ServiceOrder serviceOrder = form.update(id, serviceOrderRepository);
            return ResponseEntity.ok(new DetailServiceOrderDTO(serviceOrder));
        }
        else if(optional.isPresent() && (!authenticatedResponsible.equals(responsibleFromOrder))) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN, "NO PERMISSION"
              ); 
        }

        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Delete a Service Order")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id, HttpServletRequest request) {
        Optional<ServiceOrder> optional = serviceOrderRepository.findById(id);
        String token = tokenService.retrieveToken(request);
        Long idUsuario = tokenService.getUserId(token);
        String authenticatedResponsible = responsibleRepository.getOne(idUsuario).getName();
        String responsibleFromOrder = serviceOrderRepository.getOne(id).getResponsible().getName();

        if(optional.isPresent() && (authenticatedResponsible.equals(responsibleFromOrder))) {
            serviceOrderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else if(optional.isPresent() && (!authenticatedResponsible.equals(responsibleFromOrder))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("No Permission");  
        }
        
        return ResponseEntity.notFound().build();   
    }
    
}
