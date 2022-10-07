package ec.com.edimca.authentication.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@Entity
@Table(name = "user_role", schema = "public")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Getter(AccessLevel.NONE)
    private String id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "\"user\"", referencedColumnName = "id")
    @Getter(AccessLevel.NONE)
    private User user;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString());
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    public User getUser() {
        return user;
    }
    
}
