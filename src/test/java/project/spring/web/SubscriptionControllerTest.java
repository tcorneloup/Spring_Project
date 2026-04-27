package project.spring.web;

import project.spring.domain.exception.DomainException;
import project.spring.domain.model.Option;
import project.spring.domain.model.OptionType;
import project.spring.domain.model.Subscription;
import project.spring.domain.model.SubscriptionType;
import project.spring.domain.service.SubscriptionService;
import project.spring.web.controller.SubscriptionController;
import project.spring.web.dto.SubscriptionRequest;
import project.spring.web.error.ErrorMessageImpl;
import project.spring.web.error.ErrorMessageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private SubscriptionService subscriptionService;

    @MockitoBean
    private ErrorMessageUtils errorMessageUtils;

    @Test
    void findAllSubscriptions_shouldReturn200WithEmptyList() throws Exception {
        when(subscriptionService.findAllSubscriptionsWithOptions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void findAllSubscriptions_shouldReturn200WithSubscriptions() throws Exception {
        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(1L);
        subscriptionDomain.setType(SubscriptionType.FIX);
        subscriptionDomain.setSubscriptionDateStart(LocalDateTime.now());

        when(subscriptionService.findAllSubscriptionsWithOptions())
                .thenReturn(Collections.singletonList(subscriptionDomain));

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].clientId").value(1))
                .andExpect(jsonPath("$[0].type").value("FIX"));
    }

    @Test
    void newSubscription_shouldReturnError400WhenClientIdIsNull() throws Exception {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(null, SubscriptionType.MOBILE, null);

        mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void newSubscription_shouldReturnError400WhenTypeIsNull() throws Exception {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(1L, null, null);

        mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void newSubscription_shouldReturn200WhenSuccess() throws Exception {
        Subscription savedSubscriptionDomain = new Subscription();
        savedSubscriptionDomain.setId(1L);
        savedSubscriptionDomain.setClientId(42L);
        savedSubscriptionDomain.setType(SubscriptionType.MOBILE);
        savedSubscriptionDomain.setSubscriptionDateStart(LocalDateTime.now());

        when(subscriptionService.addNewSubscription(any(Subscription.class)))
                .thenReturn(savedSubscriptionDomain);

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(42L, SubscriptionType.MOBILE, null);

        mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clientId").value(42))
                .andExpect(jsonPath("$.type").value("MOBILE"));
    }


    @Test
    void addOptionToExistingSubscription_shouldReturn400WhenOptionTypeIsInvalid() throws Exception {
        mockMvc.perform(post("/api/subscriptions/1/options/INVALID_OPTION"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addOptionToExistingSubscription_shouldReturn400WhenIdIsInvalid() throws Exception {
        mockMvc.perform(post("/api/subscriptions/null//options/NETFLIX"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addOptionToExistingSubscription_shouldReturn400WhenSubscriptionNotFound() throws Exception {
        when(errorMessageUtils.buildErrorMessage("subscription.isNull"))
                .thenReturn(new ErrorMessageImpl("subscription.isNull", "The subscription was not registered."));
        when(subscriptionService.getSubscriptionById(99L))
                .thenThrow(new DomainException("subscription.isNull"));

        mockMvc.perform(post("/api/subscriptions/99/options/NETFLIX"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The subscription was not registered."));
    }

    @Test
    void addOptionToExistingSubscription_shouldReturn200WhenSuccess() throws Exception {
        Option optionDomain = new Option();
        optionDomain.setId(1L);
        optionDomain.setOptionType(OptionType.NETFLIX);
        optionDomain.setOptionSubDateStart(LocalDateTime.now());

        Subscription subscriptionDomain = new Subscription();
        subscriptionDomain.setId(1L);
        subscriptionDomain.setClientId(42L);
        subscriptionDomain.setType(SubscriptionType.FIBER);
        subscriptionDomain.setSubscriptionDateStart(LocalDateTime.now());
        subscriptionDomain.setOptionList(List.of(optionDomain));

        Subscription updatedSubscriptionDomain = new Subscription();
        updatedSubscriptionDomain.setId(1L);
        updatedSubscriptionDomain.setClientId(42L);
        updatedSubscriptionDomain.setType(SubscriptionType.FIBER);
        updatedSubscriptionDomain.setSubscriptionDateStart(LocalDateTime.now());
        updatedSubscriptionDomain.setOptionList(List.of(optionDomain));

        when(subscriptionService.getSubscriptionById(1L)).thenReturn(subscriptionDomain);
        when(subscriptionService.addOptionsToExistingSubscription(subscriptionDomain, OptionType.ROAMING))
                .thenReturn(updatedSubscriptionDomain);

        mockMvc.perform(post("/api/subscriptions/1/options/ROAMING"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.clientId").value(42))
                .andExpect(jsonPath("$.type").value("FIBER"))
                .andExpect(jsonPath("$.options").isArray());
    }
}