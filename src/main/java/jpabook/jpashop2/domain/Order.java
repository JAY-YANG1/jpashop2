package jpabook.jpashop2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 여러개의 오더가 하나의 멤버에 들어갈 수 있음 (다대일의 관계)
    private Member member;

    // FetchType.EAGER의 문제점
    // JPQL : select o From order o; -> SQL : select * from order (n+1 문제)
    // xToOne 은 기본이 EAGER 로 설정되어있음

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 하나의 오더가 여러개의 아이템을 가질수 있음 (일대다의 관계)
    private List<OrderItem> orderItems = new ArrayList<>();

    // JPA 에서 cascade 사용 전 퍼시스트 되는 순서
    // persist(orderItemA) -> persist(orderItemB) -> persist(orderItemC) -> persist(order)

    // JPA 에서 cascade 사용 후 퍼시스트 되는 순서
    // persist(order) 한번에 모두 퍼시스트

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder((this));
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
