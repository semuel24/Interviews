package machine.database.entity;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User implements java.io.Serializable
{
    private static final long serialVersionUID = -2393420889013247416L;

    private Long objId;

    private String gender;

    private String name;

    private Date registered;

    private String nationality;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getObjId()
    {
        return this.objId;
    }

    public void setObjId(Long objId)
    {
        this.objId = objId;
    }

    @Column(name = "gender")
    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    @Column(name = "name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "registered")
    public Date getRegistered()
    {
        return registered;
    }

    public void setRegistered(Date registered)
    {
        this.registered = registered;
    }

    @Column(name = "nationality")
    public String getNationality()
    {
        return nationality;
    }

    public void setNationality(String nationality)
    {
        this.nationality = nationality;
    }
}
