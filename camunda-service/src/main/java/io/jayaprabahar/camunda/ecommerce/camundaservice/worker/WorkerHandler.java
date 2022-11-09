package io.jayaprabahar.camunda.ecommerce.camundaservice.worker;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.jayaprabahar.camunda.ecommerce.camundaservice.util.CustomLogger;
import io.jayaprabahar.camunda.ecommerce.common.dto.CartDataDto;
import io.jayaprabahar.camunda.ecommerce.common.util.ECommerceWebClient;
import io.jayaprabahar.camunda.ecommerce.common.dto.EmailDto;
import io.jayaprabahar.camunda.ecommerce.common.EmailType;
import io.jayaprabahar.camunda.ecommerce.common.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
@SuppressWarnings("unused")
public class WorkerHandler {

    @JobWorker
    public void confirmOrder(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

        Map<String, Object> cartData = job.getVariablesAsMap();
        String productName = (String) ((List<?>) cartData.get("product")).get(0);

        if (!ECommerceWebClient.isPatchResponseAccepted(ServiceDiscovery.INVENTORY_SERVICE.getUrl() + productName)) {
            CustomLogger.logAndThrowZeebeBpmnError(job.getKey(), "NO_STOCK", "Unfortunately all the stocks are sold out");
        }
    }

    @JobWorker
    public void emailNoStock(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

        Map<String, Object> cartData = job.getVariablesAsMap();
        String orderId = (String) ((List<?>) cartData.get("orderId")).get(0);
        String emailAddress = (String) ((List<?>) cartData.get("email")).get(0);

        if (!ECommerceWebClient.isPostResponseAccepted(ServiceDiscovery.EMAIL_SERVICE.getUrl(), new EmailDto(orderId, emailAddress, EmailType.NO_STOCK))) {
            CustomLogger.logAndThrowZeebeBpmnError(job.getKey(), "EMAIL_SERVER_DOWN", "EMAIL_SERVER_DOWN");
        }
    }

    @JobWorker
    public void informSeller(final ActivatedJob job) {
        CustomLogger.logCamundaJob(job);

//        Map<String, Object> cartData = job.getVariablesAsMap();
//        String orderId = (String) ((List<?>) cartData.get("orderId")).get(0);
//        String emailAddress = (String) ((List<?>) cartData.get("email")).get(0);
//        String houseAddress = (String) ((List<?>) cartData.get("address")).get(0);
//        String shoppingAmount = (String) ((List<?>) cartData.get("amount")).get(0);
//        String buyerName = (String) ((List<?>) cartData.get("name")).get(0);
//        String productName = (String) ((List<?>) cartData.get("product")).get(0);

        CartDataDto cartDataDto = job.getVariablesAsType(CartDataDto.class);

        // Place the order file in Seller's AWS SQS queue
    }

}
