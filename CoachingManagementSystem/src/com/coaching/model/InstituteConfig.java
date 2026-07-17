package com.coaching.model;

public class InstituteConfig {
    private int id;
    private String instituteName;
    private String tagline;
    private String address;
    private String contactPhone;
    private String contactEmail;
    private String logoPath;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getInstituteName() { return instituteName; }
    public void setInstituteName(String instituteName) { this.instituteName = instituteName; }
    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getLogoPath() { return logoPath; }
    public void setLogoPath(String logoPath) { this.logoPath = logoPath; }
}
