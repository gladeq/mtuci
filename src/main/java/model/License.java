package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private LicenseType type;

    private Date firstActivationDate;
    private Date endingDate;
    private boolean blocked;
    private Integer deviceCount;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private Integer duration;
    private String description;

    @OneToMany(mappedBy = "license")
    private List<DeviceLicense> deviceLicenses;

    @OneToMany(mappedBy = "license")
    private List<LicenseHistory> licenseHistoryEntries;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LicenseType getType() {
        return type;
    }

    public void setType(LicenseType type) {
        this.type = type;
    }

    public Date getFirstActivationDate() {
        return firstActivationDate;
    }

    public void setFirstActivationDate(Date firstActivationDate) {
        this.firstActivationDate = firstActivationDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Integer getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(Integer deviceCount) {
        this.deviceCount = deviceCount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DeviceLicense> getDeviceLicenses() {
        return deviceLicenses;
    }

    public void setDeviceLicenses(List<DeviceLicense> deviceLicenses) {
        this.deviceLicenses = deviceLicenses;
    }

    public List<LicenseHistory> getLicenseHistoryEntries() {
        return licenseHistoryEntries;
    }

    public void setLicenseHistoryEntries(List<LicenseHistory> licenseHistoryEntries) {
        this.licenseHistoryEntries = licenseHistoryEntries;
    }
}
