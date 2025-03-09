package com.clothes.factory.mapper;

import com.clothes.factory.domain.ShipmentHistory;
import com.clothes.factory.domain.ShipmentHistoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ShipmentHistoryMapperTests {

    @InjectMocks
    private ShipmentHistoryMapper shipmentHistoryMapper;

    @Test
    void testMapToShipmentHistoryDto() {
        //Given
        ShipmentHistory shipmentHistory = createShipmentHistory1();

        //When
        ShipmentHistoryDto shipmentHistoryDto = shipmentHistoryMapper.mapToShipmentHistoryDto(shipmentHistory);

        //Then
        assertEquals(LocalDateTime.of(2022, 5, 10, 9, 40), shipmentHistoryDto.getShipmentTime());
        assertEquals(3L, shipmentHistoryDto.getOrderId());
        assertEquals("test@mail.com", shipmentHistoryDto.getUserMail());
        assertEquals("Fedex", shipmentHistoryDto.getShippingCompany());
    }

    @Test
    void testMapToShipmentHistoryDtoList() {
        //Given
        List<ShipmentHistory> shipmentHistoryList = new ArrayList<>();
        ShipmentHistory shipmentHistory1 = createShipmentHistory1();
        ShipmentHistory shipmentHistory2 = createShipmentHistory2();
        shipmentHistoryList.add(shipmentHistory1);
        shipmentHistoryList.add(shipmentHistory2);

        //When
        List<ShipmentHistoryDto> shipmentHistoryDtoList = shipmentHistoryMapper.mapToShipmentHistoryDtoList(shipmentHistoryList);

        //Then
        assertEquals(2, shipmentHistoryDtoList.size());

        assertEquals(LocalDateTime.of(2022, 5, 10, 9, 40), shipmentHistoryDtoList.getFirst().getShipmentTime());
        assertEquals(3L, shipmentHistoryDtoList.getFirst().getOrderId());
        assertEquals("test@mail.com", shipmentHistoryDtoList.getFirst().getUserMail());
        assertEquals("Fedex", shipmentHistoryDtoList.getFirst().getShippingCompany());

        assertEquals(LocalDateTime.of(2022, 4, 11, 18, 20), shipmentHistoryDtoList.get(1).getShipmentTime());
        assertEquals(7L, shipmentHistoryDtoList.get(1).getOrderId());
        assertEquals("test123@mail.com", shipmentHistoryDtoList.get(1).getUserMail());
        assertEquals("DHL", shipmentHistoryDtoList.get(1).getShippingCompany());
    }

    private ShipmentHistory createShipmentHistory1() {
        return ShipmentHistory.builder()
                .shipmentTime(LocalDateTime.of(2022, 5, 10, 9, 40))
                .orderId(3L)
                .userMail("test@mail.com")
                .shippingCompany("Fedex")
                .build();
    }

    private ShipmentHistory createShipmentHistory2() {
        return ShipmentHistory.builder()
                .shipmentTime(LocalDateTime.of(2022, 4, 11, 18, 20))
                .orderId(7L)
                .userMail("test123@mail.com")
                .shippingCompany("DHL")
                .build();
    }

}
