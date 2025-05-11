package com.clothes.factory.repository;

import com.clothes.factory.BaseTest;
import com.clothes.factory.domain.Cloth;
import com.clothes.factory.domain.Order;
import com.clothes.factory.exception.order.OrderNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.clothes.factory.auxiliary.ShipmentMethod.FEDEX;
import static com.clothes.factory.object_mother.ClothMother.createCloth1;
import static com.clothes.factory.object_mother.ClothMother.createCloth2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class OrderRepositoryTests extends BaseTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClothRepository clothRepository;

    @Test
    public void testSaveOrderWithClothes() throws OrderNotFoundException {
        //Given
        Cloth cloth1 = createCloth1();
        cloth1.setId(null);

        Cloth cloth2 = createCloth2();
        cloth2.setId(null);

        Order order = Order.builder()
                .orderDate(LocalDate.of(2022, 4, 23))
                .clothesList(new ArrayList<>())
                .totalOrderPrice(new BigDecimal(50))
                .paid(true)
                .sent(false)
                .shipmentMethod(FEDEX)
                .shippingPrice(new BigDecimal(15))
                .deliveryDays(4)
                .address("address")
                .build();
        order.getClothesList().add(cloth1);
        order.getClothesList().add(cloth2);

        //When
        orderRepository.save(order);
        Long orderId = order.getId();
        clothRepository.save(cloth1);
        clothRepository.save(cloth2);

        Order orderFromDb = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        //Then
        assertEquals(1, orderRepository.findAll().size());
        assertEquals(2, clothRepository.findAll().size());
        assertTrue(orderFromDb.getClothesList().contains(cloth1));
        assertTrue(orderFromDb.getClothesList().contains(cloth2));
    }

}
