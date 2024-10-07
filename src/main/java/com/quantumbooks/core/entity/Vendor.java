package com.quantumbooks.core.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Vendors")
@Data
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VendorID")
    private Long vendorId;

    @Column(name = "VendorName", nullable = false, length = 100)
    private String vendorName;

    @Column(name = "ContactPerson", length = 100)
    private String contactPerson;

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "Phone", length = 20)
    private String phone;

    @Column(name = "Address", columnDefinition = "TEXT")
    private String address;
}