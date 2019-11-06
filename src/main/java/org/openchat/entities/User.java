package org.openchat.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class User {

    private final String id;
    private final String username;
    private final String password;
    private final String about;

    public User(String id, String username, String password, String about) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAbout() {
        return about;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(id, user.id)
                .append(username, user.username)
                .append(password, user.password)
                .append(about, user.about)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(username)
                .append(password)
                .append(about)
                .toHashCode();
    }
}
