package io.jayaprabahar.camunda.ecommerce.camundaservice.worker;

import io.jayaprabahar.camunda.ecommerce.camundaservice.util.JobLogger;
import io.jayaprabahar.camunda.ecommerce.common.EmailDto;
import io.jayaprabahar.camunda.ecommerce.common.EmailType;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import io.jayaprabahar.camunda.ecommerce.common.ServiceDiscovery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class WorkerHandler {

    @JobWorker
    public void confirmOrder(final ActivatedJob job) {
        JobLogger.logJob(job);

        Map<String, Object> cartData = job.getVariablesAsMap();
        String productName = (String) ((List<?>) cartData.get("product")).get(0);

        if(!isInventoryAvailable(productName)){
            throw new ZeebeBpmnError("INVENTORY_ERROR-NO_STOCK", "NO_STOCK");
        }
    }

    @JobWorker
    public void emailNoStock(final ActivatedJob job) {
        JobLogger.logJob(job);

        Map<String, Object> cartData = job.getVariablesAsMap();
        String orderId = (String) ((List<?>) cartData.get("orderId")).get(0);
        String emailAddress = (String) ((List<?>) cartData.get("email")).get(0);
        emailNoStockError(new EmailDto(orderId, emailAddress, EmailType.NO_STOCK));
    }


    public boolean isInventoryAvailable(String itemName) {
        HttpStatus httpStatus = WebClient.create().post()
                .uri(ServiceDiscovery.INVENTORY_SERVICE.getUrl() + itemName)
                .retrieve()
                .toBodilessEntity()
                .map(ResponseEntity::getStatusCode).block(Duration.ofSeconds(5));

        return httpStatus == HttpStatus.ACCEPTED;
    }

    public void emailNoStockError(EmailDto emailDto) {
        WebClient.create().post()
                .uri(ServiceDiscovery.EMAIL_SERVICE.getUrl())
                .body(BodyInserters.fromValue(emailDto))
                .retrieve()
                .toBodilessEntity()
                .map(ResponseEntity::getStatusCode).block(Duration.ofSeconds(5));
    }

}