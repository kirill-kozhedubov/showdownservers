package iq.ven.showdown.database;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 21.03.2017.
 */
@Entity(name = "clients")
public class ClientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @OneToOne
    @JoinColumn(name = "hero_id", nullable = false)
    private HeroEntity hero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HeroEntity getHero() {
        return hero;
    }

    public void setHero(HeroEntity hero) {
        this.hero = hero;
    }
}
