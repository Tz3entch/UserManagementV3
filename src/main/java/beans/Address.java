package beans;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "adress")
public class Address implements Bean , Serializable {


    private int addressId;

    @NotNull
    private int zip;

    @NotBlank
    @Size(min = 2, max = 14)
    private String country;

    @NotBlank
    @Size(min = 2, max = 14)
    private String city;

    private String district;

    @NotBlank
    @Size(min=1, max =30)
    private String street;

    public Address() {
    }

    public int getAddressId() {
        return addressId;
    }

    @XmlElement
    public void setAddressId(int adressId) {
        this.addressId = adressId;
    }

    public int getZip() {
        return zip;
    }

    @XmlElement
    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    @XmlElement
    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    @XmlElement
    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    @XmlElement
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    @XmlElement
    public void setStreet(String street) {
        this.street = street;
    }

}
