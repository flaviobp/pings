package com.pingr.Pings.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table
public class Ping {

    @Id
    @SequenceGenerator(
            name = "ping_seq_generator",
            sequenceName = "ping_seq_generator",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ping_seq_generator"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id", foreignKey = @ForeignKey(name = "pings_author_FK",foreignKeyDefinition = "FOREIGN KEY (author) REFERENCES account(id) ON DELETE CASCADE"))
    private Account author;

    @Column(length = 144)
    private String body;

    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "ping_reply",
            joinColumns = @JoinColumn(name = "fk_id_ping", referencedColumnName = "id"),
            foreignKey = @ForeignKey(
                    name = "pings_replies_id_FK",
                    foreignKeyDefinition = "FOREIGN KEY (fk_id_ping) REFERENCES ping(id) ON DELETE CASCADE"
            ),
            inverseJoinColumns = @JoinColumn(name = "fk_id_reply", referencedColumnName = "id"),
            inverseForeignKey = @ForeignKey(
                    name = "pings_replies_reply_FK",
                    foreignKeyDefinition = "FOREIGN KEY (fk_id_reply) REFERENCES ping(id) ON DELETE CASCADE"
            )
    )
    private Set<Ping> replies;



    public Ping() {
    }

    public Ping(Long id, Account author, String body, Set<Ping> replies) {
        this.id = id;
        this.author = author;
        this.body = body;
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<Ping> getReplies() {
        return replies;
    }

    public void setReplies(Set<Ping> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Ping{" +
                "id=" + id +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", replies=" + replies +
                '}';
    }
}
