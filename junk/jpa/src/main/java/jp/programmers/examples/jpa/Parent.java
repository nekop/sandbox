package jp.programmers.examples.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "PARENT")
public class Parent {
 
    @Id
    private Long id;

    private String parentName;
     
    @Temporal(value=TemporalType.DATE)
    private Date birthday;
 
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    private List<Child> children = new ArrayList<Child>();

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getParentName() {
        return parentName;
    }
    
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Child> getChildren() {
        return children;
    }
    
    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public boolean equals(Object o) {
        return id.equals(((Parent)o).id);
    }

}
