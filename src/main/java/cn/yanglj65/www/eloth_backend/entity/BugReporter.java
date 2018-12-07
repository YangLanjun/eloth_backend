package cn.yanglj65.www.eloth_backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "bug_t")
public class BugReporter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "uid")
    private int uid;
    @Column(name = "bug")
    private String bug;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getBug() {
        return bug;
    }

    public void setBug(String bug) {
        this.bug = bug;
    }
}
