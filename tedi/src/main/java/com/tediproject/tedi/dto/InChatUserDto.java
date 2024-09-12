package com.tediproject.tedi.dto;

import java.util.List;

public class InChatUserDto {
    protected String first_name;
    protected String last_name;
    protected String email;
    protected String image;
    protected long id;
    protected Boolean hasMessaged;
    protected long lastChatUserId;
    
    public long getLastChatUserId() {
        return lastChatUserId;
    }

    public void setLastChatUserId(long lastChatUserId) {
        this.lastChatUserId = lastChatUserId;
    }

    public Boolean getHasMessaged() {
        return hasMessaged;
    }

    public void setHasMessaged(Boolean hasMessaged) {
        this.hasMessaged = hasMessaged;
    }

    protected List<InChatUserDto> connections;

    public InChatUserDto(String first_name, String last_name, String email, String image, long id, Boolean hasMessaged, long lastChatUserId,
            List<InChatUserDto> connections) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.image = image;
        this.id = id;
        this.hasMessaged = hasMessaged;
        this.lastChatUserId = lastChatUserId;
        this.connections = connections;
    }

    public InChatUserDto() {
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<InChatUserDto> getConnections() {
        return connections;
    }

    public void setConnections(List<InChatUserDto> connections) {
        this.connections = connections;
    }

}
