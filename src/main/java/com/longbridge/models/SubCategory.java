package com.longbridge.models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by longbridge on 10/18/17.
 */
@Entity
public class SubCategory extends CommonFields {
    @JsonIgnore
    @ManyToOne
    public Category category;

    public String subCategory;

    @OneToMany(mappedBy = "subCategory",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Style> styles;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    public SubCategory(Category category, String subCategory, List<Style> styles) {
        this.category = category;
        this.subCategory = subCategory;
        this.styles = styles;
    }

    public SubCategory() {
    }
}
