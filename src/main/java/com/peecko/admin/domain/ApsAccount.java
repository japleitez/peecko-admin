package com.peecko.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.peecko.admin.domain.enumeration.Language;
import com.peecko.admin.domain.enumeration.YesNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ApsAccount.
 */
@Entity
@Table(name = "aps_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ApsAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "verified", nullable = false)
    private YesNo verified;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "email")
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_verified", nullable = false)
    private YesNo emailVerified;

    @Column(name = "license")
    private String license;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "active", nullable = false)
    private YesNo active;

    @Column(name = "password")
    private String password;

    @Column(name = "created")
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apsAccount")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "apsAccount" }, allowSetters = true)
    private Set<ApsDevice> apsDevices = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apsAccount")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "videoItems", "apsAccount" }, allowSetters = true)
    private Set<Playlist> playlists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApsAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public ApsAccount name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public ApsAccount username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public YesNo getVerified() {
        return this.verified;
    }

    public ApsAccount verified(YesNo verified) {
        this.setVerified(verified);
        return this;
    }

    public void setVerified(YesNo verified) {
        this.verified = verified;
    }

    public Language getLanguage() {
        return this.language;
    }

    public ApsAccount language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getEmail() {
        return this.email;
    }

    public ApsAccount email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public YesNo getEmailVerified() {
        return this.emailVerified;
    }

    public ApsAccount emailVerified(YesNo emailVerified) {
        this.setEmailVerified(emailVerified);
        return this;
    }

    public void setEmailVerified(YesNo emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getLicense() {
        return this.license;
    }

    public ApsAccount license(String license) {
        this.setLicense(license);
        return this;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public YesNo getActive() {
        return this.active;
    }

    public ApsAccount active(YesNo active) {
        this.setActive(active);
        return this;
    }

    public void setActive(YesNo active) {
        this.active = active;
    }

    public String getPassword() {
        return this.password;
    }

    public ApsAccount password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getCreated() {
        return this.created;
    }

    public ApsAccount created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public ApsAccount updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Set<ApsDevice> getApsDevices() {
        return this.apsDevices;
    }

    public void setApsDevices(Set<ApsDevice> apsDevices) {
        if (this.apsDevices != null) {
            this.apsDevices.forEach(i -> i.setApsAccount(null));
        }
        if (apsDevices != null) {
            apsDevices.forEach(i -> i.setApsAccount(this));
        }
        this.apsDevices = apsDevices;
    }

    public ApsAccount apsDevices(Set<ApsDevice> apsDevices) {
        this.setApsDevices(apsDevices);
        return this;
    }

    public ApsAccount addApsDevice(ApsDevice apsDevice) {
        this.apsDevices.add(apsDevice);
        apsDevice.setApsAccount(this);
        return this;
    }

    public ApsAccount removeApsDevice(ApsDevice apsDevice) {
        this.apsDevices.remove(apsDevice);
        apsDevice.setApsAccount(null);
        return this;
    }

    public Set<Playlist> getPlaylists() {
        return this.playlists;
    }

    public void setPlaylists(Set<Playlist> playlists) {
        if (this.playlists != null) {
            this.playlists.forEach(i -> i.setApsAccount(null));
        }
        if (playlists != null) {
            playlists.forEach(i -> i.setApsAccount(this));
        }
        this.playlists = playlists;
    }

    public ApsAccount playlists(Set<Playlist> playlists) {
        this.setPlaylists(playlists);
        return this;
    }

    public ApsAccount addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
        playlist.setApsAccount(this);
        return this;
    }

    public ApsAccount removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
        playlist.setApsAccount(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApsAccount)) {
            return false;
        }
        return getId() != null && getId().equals(((ApsAccount) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApsAccount{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", username='" + getUsername() + "'" +
            ", verified='" + getVerified() + "'" +
            ", language='" + getLanguage() + "'" +
            ", email='" + getEmail() + "'" +
            ", emailVerified='" + getEmailVerified() + "'" +
            ", license='" + getLicense() + "'" +
            ", active='" + getActive() + "'" +
            ", password='" + getPassword() + "'" +
            ", created='" + getCreated() + "'" +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
