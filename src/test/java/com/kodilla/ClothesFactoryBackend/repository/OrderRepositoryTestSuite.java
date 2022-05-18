package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.ShipmentCompany;
import com.kodilla.ClothesFactoryBackend.auxiliary.shipment.strategy.companies.Fedex;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
import com.kodilla.ClothesFactoryBackend.object_mother.ClothMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@Transactional
public class OrderRepositoryTestSuite {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClothRepository clothRepository;

    @Test
    public void testSaveOrderWithClothes() {
        //Given
        Cloth cloth1 = ClothMother.createCloth1();
        cloth1.setId(null);

        Cloth cloth2 = ClothMother.createCloth2();
        cloth2.setId(null);

        ShipmentCompany fedex = new Fedex();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2022, 4, 23))
                .clothesList(new ArrayList<>())
                .totalOrderPrice(new BigDecimal(50))
                .paid(true)
                .sent(false)
                .shipmentCompany(fedex)
                .shipmentCompanyName(fedex.getName())
                .shippingPrice(new BigDecimal(20))
                .deliveryDays(fedex.getShipmentSetting().getDeliveryDays())
                .address("address")
                .build();
        order.getClothesList().add(cloth1);
        order.getClothesList().add(cloth2);

        //When
        orderRepository.save(order);
        Long orderId = order.getId();
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);

        //Then
        assertEquals(1, orderRepository.findAll().size());
        assertEquals(2, clothRepository.findAll().size());
        assertTrue(orderRepository.findById(orderId).get().getClothesList().contains(cloth1));
        assertTrue(orderRepository.findById(orderId).get().getClothesList().contains(cloth2));
    }
}