package com.ElectronicStoreSpringboot.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    private String categoryId;
    @Column(name = "Category_Title", length = 250)
    private String title;
    @Column(name = "Category_Description", length = 500)
    private String description;
    private String coverImage;

   // @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //private List<Product> products = new ArrayList<>();
}
