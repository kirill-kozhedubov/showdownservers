package iq.ven.showdown.database;

import iq.ven.showdown.fighting.model.HeroArchetype;
import iq.ven.showdown.fighting.model.Imagible;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */
@Entity(name = "hero_archetype")
public class HeroArchetypeEntity implements HeroArchetype, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "imglink", nullable = false)
    private String imgLink = "http://i.imgur.com/CGLROZC.jpg";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "HeroArchetypeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }
}
