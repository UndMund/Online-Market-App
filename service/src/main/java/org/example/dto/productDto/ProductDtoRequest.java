package org.example.dto.productDto;

import lombok.Builder;
import lombok.Value;
import org.apache.tomcat.util.codec.binary.Base64;
import org.example.dto.statusDto.StatusDto;
import org.example.dto.userDto.UserDtoRequest;

import java.math.BigDecimal;

@Value
@Builder
public class ProductDtoRequest {
    Long id;
    String name;
    BigDecimal price;
    String description;
    StatusDto status;
    String category;
    byte[] image;
    UserDtoRequest user;
    public String generateBase64String() {
        return Base64.encodeBase64String(this.image);
    }
}
