package com.kodilla.ClothesFactoryBackend.repository;

import com.kodilla.ClothesFactoryBackend.auxiliary.*;
import com.kodilla.ClothesFactoryBackend.domain.Cloth;
import com.kodilla.ClothesFactoryBackend.domain.Order;
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
        Cloth cloth1 = Cloth.builder()
                .fashion(Fashion.HOODIE)
                .color(Color.RED)
                .print("hello")
                .font(Font.ARIAL)
                .printColor(Color.BLACK)
                .size(Size.M)
                .quantity(2)
                .price(new BigDecimal(200))
                .build();

        Cloth cloth2 = Cloth.builder()
                .fashion(Fashion.T_SHIRT)
                .color(Color.BLACK)
                .print("drama")
                .font(Font.COMIC_SANS)
                .printColor(Color.WHITE)
                .size(Size.XXL)
                .quantity(3)
                .price(new BigDecimal(150))
                .build();

        Order order = Order.builder()
                .orderDate(LocalDate.of(2022, 4, 23))
                .clothesList(new ArrayList<>())
                .totalOrderPrice(new BigDecimal(50))
                .paid(true)
                .sent(false)
                .shipmentMethod(ShipmentMethod.FEDEX)
                .shippingPrice(new BigDecimal(20))
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
