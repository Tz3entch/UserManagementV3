package beans;

import org.hibernate.validator.constraints.NotBlank;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "group")
public class Group implements Bean, Serializable {

    private int groupId;

    @NotBlank
    private String groupName;

    public Group() {
    }

    public int getGroupId() {
        return groupId;
    }

    @XmlElement
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    @XmlElement
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
