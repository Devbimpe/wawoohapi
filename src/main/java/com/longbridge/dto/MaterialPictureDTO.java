package com.longbridge.dto;

/**
 * Created by Longbridge on 14/12/2017.
 */
public class MaterialPictureDTO {
    public Long id;
    public Long productId;
    public String materialPicture;
    public String materialName;

    public MaterialPictureDTO() {
    }

    public MaterialPictureDTO(Long id, Long productId, String materialPicture) {
        this.id = id;
        this.productId = productId;
        this.materialPicture = materialPicture;
    }
}
