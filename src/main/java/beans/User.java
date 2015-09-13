package beans;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.search.annotations.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Indexed
@XmlRootElement(name = "user")
public class User implements Bean , Serializable {

    @DocumentId
    private int userId;


    private int groupId;


    private int addressId;

    @Field
    @NotBlank
    @Size(min =1 , max = 16)
    private String firstName;

    @Field
    @NotBlank
    @Size(min =1 , max = 16)
    private String lastName;

    @NotBlank
    @Size(min =1 , max = 16)
    private String username;

    @NotBlank
    @Size(min =6 , max = 20)
    private String password;

    @Field
    @Email
    private String email;


    @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES)
    @DateBridge(resolution = Resolution.DAY, encoding = EncodingType.STRING)
    @Past
    private Date birthday;

    private boolean active;


    private Date createTS;


    private Date lastUpdateTS;

    public User() {
        setCreateTS(new Date());
        setLastUpdateTS(new Date());
    }

    public int getUserId() {
        return userId;
    }

    @XmlElement
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    @XmlElement
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAddressId() {
        return addressId;
    }

    @XmlElement
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    @XmlElement
    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getUsername() {
        return username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    @XmlElement
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isActive() {
        return active;
    }

    @XmlElement
    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreateTS() {
        return createTS;
    }

    @XmlElement
    public void setCreateTS(Date createTS) {
        this.createTS = createTS;
    }

    public Date getLastUpdateTS() {
        return lastUpdateTS;
    }

    @XmlElement
    public void setLastUpdateTS(Date lastUpdateTS) {
        this.lastUpdateTS = lastUpdateTS;
    }

}
