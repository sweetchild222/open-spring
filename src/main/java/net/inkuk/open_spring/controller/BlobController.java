package net.inkuk.open_spring.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import net.inkuk.open_spring.util.ImageResize;
import net.inkuk.open_spring.util.ImageSetWriter;
import net.inkuk.open_spring.util.Log;
import net.inkuk.open_spring.util.MultipartToFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

@RestController
@Component
public class BlobController {

    private static final int[][] profileSupportSizeList = {{256, 256}, {128, 128}, {96, 96}, {64, 64}, {48, 48}, {32, 32}, {24, 24}};
    private final String profilePath;
    private final ImageSetWriter profileSetWriter;

    private static final int[][] articleThumbnailSupportSizeList = {{960, 960}, {960, 540}, {512, 320}, {320, 256}, {540, 960}, {170, 170}, {160, 128}, {96, 96}};
    private final String articleThumbnailPath;
    private final ImageSetWriter articleThumbnailSetWriter;

    private final String articleImagePath;
    private final MultipartToFile multipartToFile;

    private static final int[][] blogImageSupportSizeList = {{1920, 1080}, {960, 540}, {1920, 168}, {960, 84}};
    private final String blogImagePath;
    private final ImageSetWriter blogImageSetWriter;

    public BlobController(Environment env) {

        String appName = env.getProperty("spring.application.name");
        boolean isOpenSpring = appName == null || appName.equals("open-spring");

        this.profilePath = isOpenSpring ? "/home/ubuntu/open-spring/blob/profile" : "/home/ubuntu/private-spring/blob/profile";
        this.articleThumbnailPath = isOpenSpring ? "/home/ubuntu/open-spring/blob/article/thumbnail" : "/home/ubuntu/private-spring/blob/article/thumbnail";
        this.articleImagePath = isOpenSpring ? "/home/ubuntu/open-spring/blob/article/image" : "/home/ubuntu/private-spring/blob/article/image";
        this.blogImagePath = isOpenSpring ? "/home/ubuntu/open-spring/blob/blog/image" : "/home/ubuntu/private-spring/blob/blog/image";

        this.profileSetWriter = new ImageSetWriter(profilePath);
        this.articleThumbnailSetWriter = new ImageSetWriter(articleThumbnailPath);
        this.multipartToFile = new MultipartToFile(articleImagePath);
        this.blogImageSetWriter = new ImageSetWriter(blogImagePath);
    }


    @PostMapping("/blob/article")
    public ResponseEntity<?> postArticle(@RequestParam("image") MultipartFile multipartFile) {

        if(multipartFile.getSize() > (1000 * 1000 * 10))
            return new ResponseEntity<>(HttpStatus.CONTENT_TOO_LARGE);

        final String id = multipartToFile.write(multipartFile);

        if(id == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }

    @GetMapping("/blob/article/{id}")
    public ResponseEntity<?> getArticle(@PathVariable String id) {

        final String filePath = articleImagePath + "/" + id;

        if(!(new File(filePath)).exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return createResponse(filePath);
    }


    @PostMapping("/blob/profile")
    public ResponseEntity<?> postProfile(@RequestParam("image") MultipartFile multipartFile){

        if(multipartFile.getSize() > (1000 * 1000 * 10))
            return new ResponseEntity<>(HttpStatus.CONTENT_TOO_LARGE);

        try {

            final byte [] bytes = multipartFile.getBytes();

            return postMutiSizeCore(bytes, profileSupportSizeList, profileSetWriter);
        }
        catch (IOException e){

            Log.error(e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/blob/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable String id, @RequestParam(required=false) String size) {

        return getCore(id, size, profileSupportSizeList, profilePath);
    }




    @PostMapping("/blob/article/thumbnail")
    public ResponseEntity<?> postArticleThumbnail(@RequestParam("image") MultipartFile multipartFile){

        if(multipartFile.getSize() > (1000 * 1000 * 10))
            return new ResponseEntity<>(HttpStatus.CONTENT_TOO_LARGE);

        try {

            final byte [] bytes = multipartFile.getBytes();

            return postMutiSizeCore(bytes, articleThumbnailSupportSizeList, articleThumbnailSetWriter);
        }
        catch (IOException e){

            Log.error(e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/blob/article/thumbnail/{id}")
    public ResponseEntity<?> getArticleThumbnail(@PathVariable String id, @RequestParam(required=false) String size) {

        return getCore(id, size, articleThumbnailSupportSizeList, articleThumbnailPath);
    }




    @PostMapping("/blob/blog/image")
    public ResponseEntity<?> postBlog(@RequestParam("image") MultipartFile multipartFile){

        if(multipartFile.getSize() > (1000 * 1000 * 10))
            return new ResponseEntity<>(HttpStatus.CONTENT_TOO_LARGE);

        try {

            final byte [] bytes = multipartFile.getBytes();

            return postMutiSizeCore(bytes, blogImageSupportSizeList, blogImageSetWriter);
        }
        catch (IOException e){

            Log.error(e.toString());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/blob/blog/image/{id}")
    public ResponseEntity<?> getBlog(@PathVariable String id, @RequestParam(required=false) String size) {

        return getCore(id, size, blogImageSupportSizeList, blogImagePath);
    }




    private String writeImageSet(BufferedImage srcImage, int orientation, int [][] supportSizeList, ImageSetWriter setWriter){

        final BufferedImage [] imageSet = ImageResize.resize(srcImage, orientation, supportSizeList);

        if(imageSet == null)
            return null;

        if(imageSet.length != supportSizeList.length)
            return null;

        return setWriter.write(imageSet);
    }


    public int getOrientation(ByteArrayInputStream stream) {

        try {

            Metadata metadata = ImageMetadataReader.readMetadata(stream);
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            if(directory == null)
                return 1; //== 0 degree

            return directory.getInt(ExifIFD0Directory. TAG_ORIENTATION);

        } catch (ImageProcessingException | IOException | MetadataException e) {

            Log.error(e.toString());
            return 1; //== 0 degree
        }
    }


    private String [] spiltFileName(String fileName){

        int index = fileName.lastIndexOf('.');

        if (index > 0 && index < fileName.length() - 1) {

            String name = fileName.substring(0, index);
            String extension = fileName.substring(index + 1);

            return new String[]{name, extension};

        } else
            return null;
    }


    private int [] parseIntSize(String size){

        String [] split = size.split("x");

        if(split.length == 2){

            try {

                int width = Integer.parseInt(split[0]);
                int height = Integer.parseInt(split[1]);

                return new int [] {width, height};

            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }


    public boolean isSupportSize(String stringSize, int [][] SizeList){

        int [] sizeInt = parseIntSize(stringSize);

        if(sizeInt == null)
            return false;

        for(int [] size: SizeList){

            if(sizeInt[0] == size[0] && sizeInt[1] == size[1])
                return true;
        }

        return false;
    }




    private ResponseEntity<?> createResponse(String filePath) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("image/webp"));

        try {

            final UrlResource resource = new UrlResource("file:" + filePath);

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);

        } catch (MalformedURLException e) {

            Log.error(e.toString());

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private ResponseEntity<?> postMutiSizeCore(byte [] bytes, int [][] supportSizeList, ImageSetWriter setWriter) throws IOException{

        final BufferedImage srcImage = ImageIO.read(new ByteArrayInputStream(bytes));

        if(srcImage == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        int orientation = getOrientation(new ByteArrayInputStream(bytes));

        final String id = writeImageSet(srcImage, orientation, supportSizeList, setWriter);

        if(id == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(Map.of("id", id), HttpStatus.OK);
    }




    private ResponseEntity<?> getCore(String id, String size, int [][] supportSizeList, String prefixPath){

        String [] split = spiltFileName(id);

        if(split == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!(split.length == 2 && split[1].equals("webp")))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(size == null) //set default size
            size = supportSizeList[0][0] + "x" + supportSizeList[0][1];

        if(!isSupportSize(size, supportSizeList))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        final String fileName = size.toLowerCase() + "." + split[1];

        final String filePath = prefixPath + "/" + split[0] + "/" + fileName;

        if(!(new File(filePath)).exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return createResponse(filePath);
    }
}