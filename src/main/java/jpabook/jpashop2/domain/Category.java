package jpabook.jpashop2.domain;

import jpabook.jpashop2.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "items")
    private List<Item> items = new ArrayList<>();


}
