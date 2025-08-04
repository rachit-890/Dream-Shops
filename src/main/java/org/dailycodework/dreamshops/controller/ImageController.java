package org.dailycodework.dreamshops.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.dto.ImageDto;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.Image;
import org.dailycodework.dreamshops.response.ApiResponse;
import org.dailycodework.dreamshops.service.image.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
@Tag( name = "Images", description = "Operations about images")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    @Operation(summary = "Upload images", description = "Upload images")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new ApiResponse("Image saved successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    @Transactional
    @Operation(summary = "Download image", description = "Download image")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image=imageService.getImageById(imageId);
        ByteArrayResource resource= new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+image.getFileName()+"\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    @Operation(summary = "Update image", description = "Update image")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestBody MultipartFile file) {
        try {
            Image image=imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ApiResponse("Image updated successfully", null));
            }
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage(),null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Image not found",INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    @Operation(summary = "Delete image", description = "Delete image")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
        try {
            Image image=imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Image deleted successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse( e.getMessage(),null));
        }
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Delete failed!",INTERNAL_SERVER_ERROR));
    }
}
