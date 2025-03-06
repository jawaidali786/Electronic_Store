package com.ElectronicStoreSpringboot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    private String orderID;
    // enum - PENDING,DISPATCHED,DELIVERED
    private String orderStatus;
    private String paymentStatus;
    private String orderAmount;
    private String billingName;
    @Column(length = 250)
    private String billingAddress;
    private String billingPhone;
    private String orderDate;
    private Date orderedDate;
    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "User_Id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

}
