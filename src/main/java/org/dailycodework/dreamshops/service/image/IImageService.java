package org.dailycodework.dreamshops.service.image;
import org.dailycodework.dreamshops.dto.ImageDto;
import org.dailycodework.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById( Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void deleteImageById( Long id);
    void updateImage(MultipartFile file,Long imageId);

}
