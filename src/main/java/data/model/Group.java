package data.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Group {
    private int groupId;
    private String groupName;
    private String groupDescription;
    private List<User> users;

    @Id
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Basic
    @Column(name = "group_description")
    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                Objects.equals(groupName, group.groupName) &&
                Objects.equals(groupDescription, group.groupDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(groupId, groupName, groupDescription);
    }

    @ManyToMany
    @JoinTable(name = "user_group", schema = "production_order", joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "group_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false))
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
