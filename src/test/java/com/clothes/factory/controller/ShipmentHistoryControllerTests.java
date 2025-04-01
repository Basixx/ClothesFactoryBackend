package com.clothes.factory.controller;

import com.clothes.factory.domain.ShipmentHistoryDto;
import com.clothes.factory.facade.ShipmentHistoryFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig
@WebMvcTest(ShipmentHistoryController.class)
public class ShipmentHistoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ShipmentHistoryFacade shipmentHistoryFacade;

    @Test
    void testGetAllShipmentHistory() throws Exception {
        //Given
        List<ShipmentHistoryDto> shipmentHistoryDtoList = new ArrayList<>();

        ShipmentHistoryDto shipmentHistoryDto1 = ShipmentHistoryDto.builder()
                .id(1L)
                .shipmentTime(LocalDateTime.of(2022, 5, 12, 12, 0))
                .orderId(2L)
                .userMail("test@mail.com")
                .shippingCompany("Fedex")
                .build();
        ShipmentHistoryDto shipmentHistoryDto2 = ShipmentHistoryDto.builder()
                .id(3L)
                .shipmentTime(LocalDateTime.of(2022, 4, 15, 15, 30))
                .orderId(4L)
                .userMail("test123@mail.com")
                .shippingCompany("Dhl")
                .build();

        shipmentHistoryDtoList.add(shipmentHistoryDto1);
        shipmentHistoryDtoList.add(shipmentHistoryDto2);

        when(shipmentHistoryFacade.getAllShipmentHistory())
                .thenReturn(shipmentHistoryDtoList);

        //When & Then
        mockMvc.perform(
                        get("/shipmentHistory")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].shipmentTime", is("2022-05-12T12:00:00")))
                .andExpect(jsonPath("$[0].orderId", is(2)))
                .andExpect(jsonPath("$[0].userMail", is("test@mail.com")))
                .andExpect(jsonPath("$[0].shippingCompany", is("Fedex")))
                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].shipmentTime", is("2022-04-15T15:30:00")))
                .andExpect(jsonPath("$[1].orderId", is(4)))
                .andExpect(jsonPath("$[1].userMail", is("test123@mail.com")))
                .andExpect(jsonPath("$[1].shippingCompany", is("Dhl")));
    }

}
