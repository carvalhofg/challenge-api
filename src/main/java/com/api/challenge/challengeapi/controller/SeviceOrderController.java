package com.api.challenge.challengeapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

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

    @GetMapping
    public List<DetailServiceOrderDTO> list(String responsibleName) {

        if(responsibleName == null) {
            List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
            return DetailServiceOrderDTO.convert(serviceOrders);
        } else {
            List<ServiceOrder> serviceOrders = serviceOrderRepository.findByResponsibleName(responsibleName);
            if(serviceOrders.isEmpty()) {
                throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "ORDER NOT FOUND"
              );
            }
            return DetailServiceOrderDTO.convert(serviceOrders);
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ServiceOrderDTO> createServiceOrder(@RequestBody @Valid ServiceOrderForm form, UriComponentsBuilder uriBuilder) {
        ServiceOrder serviceOrder = form.convert(customerRepository, responsibleRepository);
        serviceOrderRepository.save(serviceOrder);

        URI uri = uriBuilder.path("/service-order/{id}").buildAndExpand(serviceOrder.getId()).toUri();
        return ResponseEntity.created(uri).body(new ServiceOrderDTO(serviceOrder));
    }

    @PostMapping("/{idServiceOrder}/followup")
    @Transactional
    public ResponseEntity<OrderFollowUpDTO> responder(@PathVariable Long idServiceOrder, @RequestBody @Valid OrderFollowUpForm form, UriComponentsBuilder uriBuilder) {
        ServiceOrder serviceOrder = serviceOrderRepository.getOne(idServiceOrder);
        OrderFollowUp orderFollowUp = form.convert(serviceOrder);

        orderFollowUpRepository.save(orderFollowUp);

        URI uri = uriBuilder.path("/topicos/{idTopico}/resposta/{idResposta}").buildAndExpand(serviceOrder.getId(), orderFollowUp.getId()).toUri();
        return ResponseEntity.created(uri).body(new OrderFollowUpDTO(orderFollowUp));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DetailServiceOrderDTO> updateServiceOrder(@PathVariable Long id, @RequestBody @Valid UpdateServiceOrderForm form) {
        Optional<ServiceOrder> optional = serviceOrderRepository.findById(id);

        if(optional.isPresent()) {
            ServiceOrder serviceOrder = form.update(id, serviceOrderRepository);
            return ResponseEntity.ok(new DetailServiceOrderDTO(serviceOrder));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<ServiceOrder> optional = serviceOrderRepository.findById(id);

        if(optional.isPresent()) {
            serviceOrderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();   
    }
    
}
