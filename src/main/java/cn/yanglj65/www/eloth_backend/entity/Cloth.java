package cn.yanglj65.www.eloth_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cloth_t")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Cloth {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_cloth_to_user", foreignKeyDefinition = "foreign key (user_id) REFERENCES user_t(id) ON DELETE CASCADE ON UPDATE CASCADE"))
    @JsonIgnore
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "color")
    @NotNull
    private String color;
    @Column(name = "type")
    @NotNull
    private String type;
    @Column(name = "size")
    @NotNull
    private String size;
    @Column(name = "usability")
    @NotNull
    private boolean usability = true;
    public boolean isUsability() {
        return usability;
    }

    public void setUsability(boolean usability) {
        this.usability = usability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
