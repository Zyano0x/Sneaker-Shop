package com.example.sneaker_shop.Entities;

import com.example.sneaker_shop.Validators.annotation.ValidCategoryId;
import com.example.sneaker_shop.Validators.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "shoes")
public class Shoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    @NotEmpty(message = "Name must not be empty")
    @Size(max = 50,min =1, message = "Name must be less than 50 characters")
    private String name;

    @Column(name = "price")
    @NotNull(message = "Price is required")
    private double price;

    @Column(nullable = true, length = 255)
    private String photos;

    @Column(nullable = true)
    private String description;

    @Column(name="deleted",columnDefinition = "boolean default false")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;

    @ManyToOne
    @JoinColumn(name = "ueser_id")
    @ValidUserId
    private User user;

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null)
            return null;

        return "/photos/" + photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photoURL) {
        this.photos = photoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
