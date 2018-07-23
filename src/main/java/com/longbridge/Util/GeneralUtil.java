package com.longbridge.Util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.longbridge.dto.*;
import com.longbridge.exception.WawoohException;
import com.longbridge.models.*;
import com.longbridge.repository.*;
import com.longbridge.respbodydto.ProductRespDTO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Longbridge on 24/01/2018.
 */
@Service
public class GeneralUtil {



    @Autowired
    EventPictureRepository eventPictureRepository;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    PriceSlashRepository priceSlashRepository;

    @Autowired
    ShippingRepository shippingRepository;

    @Autowired
    ZonePriceRepository zonePriceRepository;


    public List<ProductPictureDTO> convertProdPictureEntitiesToDTO(List<ProductPicture> productPictures){
        List<ProductPictureDTO> productPictureDTOS = new ArrayList<ProductPictureDTO>();
        for(ProductPicture p: productPictures){
            ProductPictureDTO pictureDTO = convertProdPictureEntityToDTO(p);
            productPictureDTOS.add(pictureDTO);
        }
        return productPictureDTOS;
    }


    public ProductPictureDTO convertProdPictureEntityToDTO(ProductPicture picture){
        ProductPictureDTO pictureDTO = new ProductPictureDTO();
        pictureDTO.id=picture.id;
        pictureDTO.productId=picture.products.id;
        pictureDTO.picture=picture.pictureName;
        return pictureDTO;

    }

    public List<ArtPictureDTO> convertArtPictureEntitiesToDTO(List<ArtWorkPicture> artWorkPictures){
        List<ArtPictureDTO> artPictureDTOS = new ArrayList<ArtPictureDTO>();
        for(ArtWorkPicture p: artWorkPictures){
            ArtPictureDTO artPictureDTO = convertArtPictureEntityToDTO(p);
            artPictureDTOS.add(artPictureDTO);
        }
        return artPictureDTOS;
    }


    public ArtPictureDTO convertArtPictureEntityToDTO(ArtWorkPicture picture){
        ArtPictureDTO pictureDTO = new ArtPictureDTO();
        pictureDTO.id=picture.id;
        pictureDTO.productId=picture.products.id;
        pictureDTO.artWorkPicture=picture.pictureName;
        return pictureDTO;

    }

    public List<MaterialPictureDTO> convertMatPictureEntitiesToDTO(List<MaterialPicture> materialPictures){
        List<MaterialPictureDTO> materialPictureDTOS = new ArrayList<MaterialPictureDTO>();
        for(MaterialPicture p: materialPictures){
            MaterialPictureDTO materialPictureDTO = convertMatPictureEntityToDTO(p);
            materialPictureDTOS.add(materialPictureDTO);
        }
        return materialPictureDTOS;
    }


    public MaterialPictureDTO convertMatPictureEntityToDTO(MaterialPicture picture){
        MaterialPictureDTO pictureDTO = new MaterialPictureDTO();
        pictureDTO.id=picture.id;
        pictureDTO.productId=picture.products.id;
        pictureDTO.materialPicture=picture.pictureName;
        return pictureDTO;

    }



    public List<ProductRespDTO> convertProdEntToProdRespDTOs(List<Products> products, User user){

        List<ProductRespDTO> productDTOS = new ArrayList<ProductRespDTO>();

        for(Products p: products){
            ProductRespDTO productDTO = convertEntityToDTO(p);
            if(user != null){
                if(wishListRepository.findByUserAndProducts(user,p) != null){
                 productDTO.wishListFlag = "Y";
                }
                else {
                    productDTO.wishListFlag = "N";
                }
            }

            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    public List<ProductRespDTO> convertProdEntToProdRespDTOs(List<Products> products){

        List<ProductRespDTO> productDTOS = new ArrayList<ProductRespDTO>();
        for(Products p: products){
            ProductRespDTO productDTO = convertEntityToDTO(p);
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    public ProductRespDTO convertEntityToDTO(Products products){
        ProductRespDTO productDTO = new ProductRespDTO();
        productDTO.id=products.id;
        productDTO.amount=products.amount;
        productDTO.color=products.color;
        productDTO.description=products.prodDesc;
        productDTO.name=products.name;
        productDTO.productSizes=products.productSizes;
        if(products.style != null) {
            productDTO.styleId = products.style.id.toString();
        }
        productDTO.designerId=products.designer.id.toString();
        productDTO.designerStatus=products.designer.status;
        productDTO.stockNo=products.stockNo;
        productDTO.inStock=products.inStock;
        productDTO.availability=products.availability;
        productDTO.acceptCustomSizes=products.acceptCustomSizes;
        productDTO.designerName=products.designer.storeName;
        productDTO.status=products.status;
        productDTO.sponsoredFlag=products.sponsoredFlag;
        productDTO.verifiedFlag=products.verifiedFlag;
        productDTO.subCategoryId=products.subCategory.id.toString();
        productDTO.categoryId=products.subCategory.category.id.toString();
        productDTO.numOfTimesOrdered = products.numOfTimesOrdered;
        productDTO.numOfDaysToComplete=products.numOfDaysToComplete;
        productDTO.mandatoryMeasurements=products.mandatoryMeasurements;

        PriceSlash priceSlash = priceSlashRepository.findByProducts(products);
        if(priceSlash != null){
            productDTO.slashedPrice = priceSlash.getSlashedPrice();
            productDTO.percentageDiscount=priceSlash.getPercentageDiscount();
        }

        List<ProductPicture> productPictures = products.picture;
        productDTO.picture=convertProdPictureEntitiesToDTO(productPictures);

        List<ArtWorkPicture> artWorkPictures = products.artWorkPicture;
        productDTO.artWorkPicture=convertArtPictureEntitiesToDTO(artWorkPictures);

        List<MaterialPicture> materialPictures = products.materialPicture;
        productDTO.materialPicture=convertMatPictureEntitiesToDTO(materialPictures);
        int sum = 0;
        int deliverySum = 0;
        int serviceSum = 0;

        int noOfUsers = products.reviews.size();

        for (ProductRating productrating: products.reviews) {
            sum = sum+productrating.getProductQualityRating();
            deliverySum += productrating.getDeliveryTimeRating();
            serviceSum += productrating.getServiceRating();
        }
        if(sum != 0){
            Double pQualityRating= (double) sum/noOfUsers;
            productDTO.productQualityRating = pQualityRating.intValue();
        }else {
            productDTO.productQualityRating=0;
        }

        if(deliverySum != 0){
            Double deliveryRating = (double) deliverySum/noOfUsers;
            productDTO.productDeliveryRating = deliveryRating.intValue();
        }else{
            productDTO.productDeliveryRating = 0;
        }

        if(serviceSum != 0){
            Double serviceRating = (double) serviceSum/noOfUsers;
            productDTO.productServiceRating = serviceRating.intValue();
        }else{
            productDTO.productServiceRating = 0;
        }

        return productDTO;
    }






    public ProductRespDTO convertEntityToDTOWithReviews(Products products){
        ProductRespDTO productDTO = new ProductRespDTO();
        productDTO.id=products.id;
        productDTO.amount=products.amount;
        productDTO.color=products.color;
        productDTO.description=products.prodDesc;
        productDTO.name=products.name;
        productDTO.productSizes=products.productSizes;
        if(products.style != null) {
            productDTO.styleId = products.style.id.toString();
        }
        productDTO.designerId=products.designer.id.toString();
        productDTO.stockNo=products.stockNo;
        productDTO.inStock=products.inStock;
        productDTO.acceptCustomSizes=products.acceptCustomSizes;
        productDTO.availability=products.availability;
        productDTO.designerName=products.designer.storeName;
        productDTO.status=products.status;
        productDTO.sponsoredFlag=products.sponsoredFlag;
        productDTO.verifiedFlag=products.verifiedFlag;
        productDTO.subCategoryId=products.subCategory.id.toString();
        productDTO.categoryId=products.subCategory.category.id.toString();
        productDTO.numOfTimesOrdered = products.numOfTimesOrdered;
        productDTO.numOfDaysToComplete=products.numOfDaysToComplete;
        productDTO.mandatoryMeasurements=products.mandatoryMeasurements;
        List<ProductPicture> productPictures = products.picture;
        productDTO.picture=convertProdPictureEntitiesToDTO(productPictures);

        List<ArtWorkPicture> artWorkPictures = products.artWorkPicture;
        productDTO.artWorkPicture=convertArtPictureEntitiesToDTO(artWorkPictures);

        List<MaterialPicture> materialPictures = products.materialPicture;
        productDTO.materialPicture=convertMatPictureEntitiesToDTO(materialPictures);

        productDTO.reviews=products.reviews;
        PriceSlash priceSlash = priceSlashRepository.findByProducts(products);
        if(priceSlash != null){
            productDTO.slashedPrice = priceSlash.getSlashedPrice();
            productDTO.percentageDiscount=priceSlash.getPercentageDiscount();
        }

        return productDTO;

    }





    public String getPicsName(String picsArrayType, String productName){

        String timeStamp = picsArrayType + getCurrentTime();
        System.out.println(productName);
        String fName = productName.replaceAll("\\s","") + timeStamp;
        return  fName;
    }



    public String getCurrentTime(){
        int length = 10;
        SecureRandom random = new SecureRandom();
        BigInteger bigInteger = new BigInteger(130, random);

        String sessionId = String.valueOf(bigInteger.toString(length));
        return sessionId.toUpperCase();
    }



    public EventPicturesDTO convertEntityToDTO(EventPictures eventPictures){
        EventPicturesDTO eventPicturesDTO = new EventPicturesDTO();

        eventPicturesDTO.setId(eventPictures.id);
        eventPicturesDTO.setPicture(eventPictures.pictureName);
        return eventPicturesDTO;

    }



    public CloudinaryResponse uploadToCloud(String base64Image, String fileName, String folder){
        CloudinaryResponse cloudinaryResponse = new CloudinaryResponse();
        try {


            String image = base64Image.split(",")[1];

            byte[] imageByte = javax.xml.bind.DatatypeConverter.parseBase64Binary(image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
//
            File imgfile = File.createTempFile(fileName, "tmp");
            FileUtils.copyInputStreamToFile(bis, imgfile);
            bis.close();
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "har9qnw3d",
                    "api_key", "629146977531321",
                    "api_secret", "wW5HlSfyi-2oTlj6NX60lIGWyG0"));
            Map uploadResult = cloudinary.uploader().upload(base64Image, ObjectUtils.asMap("public_id", fileName, "folder", folder));

            cloudinaryResponse.setPublicId(uploadResult.get("public_id").toString());
            cloudinaryResponse.setUrl(uploadResult.get("url").toString());
        }catch (UnknownHostException ex){
                ex.printStackTrace();
                throw new WawoohException();

        }catch (Exception ex){
            ex.printStackTrace();
            throw new WawoohException();
        }

        return cloudinaryResponse;
    }

    public CloudinaryResponse uploadFileToCloud(File base64Image, String fileName, String folder){
        CloudinaryResponse cloudinaryResponse = new CloudinaryResponse();
        try {

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "har9qnw3d",
                    "api_key", "629146977531321",
                    "api_secret", "wW5HlSfyi-2oTlj6NX60lIGWyG0"));
            Map uploadResult = cloudinary.uploader().upload(base64Image,  ObjectUtils.asMap("public_id",fileName,"folder",folder));

            cloudinaryResponse.setPublicId(uploadResult.get("public_id").toString());
            cloudinaryResponse.setUrl(uploadResult.get("url").toString());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new WawoohException();
        }

        return cloudinaryResponse;
    }


    public Map deleteFromCloud(String publicId, String fileName){

        try {

            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "har9qnw3d",
                    "api_key", "629146977531321",
                    "api_secret", "wW5HlSfyi-2oTlj6NX60lIGWyG0"));
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        }catch (Exception ex){
            ex.printStackTrace();
            throw new WawoohException();
        }

    }


    public List<EventsDTO> convertEntitiesToDTOs(List<Events> events){

        List<EventsDTO> eventsDTOS = new ArrayList<EventsDTO>();

        for(Events events1: events){
            EventsDTO eventsDTO = convertEntityToDTO(events1);
            eventsDTOS.add(eventsDTO);
        }
        return eventsDTOS;
    }


    public List<EventPicturesDTO> convertEntsToDTOs(List<EventPictures> events){

        List<EventPicturesDTO> picturesDTOS = new ArrayList<EventPicturesDTO>();

        for(EventPictures eventsp: events){
            EventPicturesDTO picturesDTO = convertEntityToDTO(eventsp);
            picturesDTOS.add(picturesDTO);
        }
        return picturesDTOS;
    }

    public EventsDTO convertEntityToDTO(Events events){
        EventsDTO eventsDTO = new EventsDTO();
        eventsDTO.setId(events.id);
        eventsDTO.setDescription(events.description);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String stringDate = formatter.format(events.eventDate);
        eventsDTO.setEventDate(stringDate);
        eventsDTO.setEventName(events.getEventName());
        eventsDTO.setLocation(events.location);

        eventsDTO.setMainPicture(events.mainPictureName);
        eventsDTO.setEventPictures(convertEvtPicEntToDTOsMin(eventPictureRepository.findFirst6ByEvents(events)));

        return eventsDTO;

    }

    public List<EventPicturesDTO> convertEvtPicEntToDTOsMin(List<EventPictures> eventPictures){

        List<EventPicturesDTO> eventPicturesDTOS = new ArrayList<EventPicturesDTO>();

        for(EventPictures eventPictures1: eventPictures){
            EventPicturesDTO eventPicturesDTO = convertEntityToDTOMin(eventPictures1);
            eventPicturesDTOS.add(eventPicturesDTO);
        }
        return eventPicturesDTOS;
    }


    public EventPicturesDTO convertEntityToDTOMin(EventPictures eventPictures){
        EventPicturesDTO eventPicturesDTO = new EventPicturesDTO();
        eventPicturesDTO.setEventName(eventPictures.events.eventName);
        eventPicturesDTO.setId(eventPictures.id);
        eventPicturesDTO.setPicture(eventPictures.pictureName);
        eventPicturesDTO.setPictureDesc(eventPictures.getPictureDesc());
        return eventPicturesDTO;

    }

    public List<Products> getRandomProducts(List<Products> products, int numberOfProducts) {
        List<Products> randomProducts = new ArrayList<>();
        List<Products> copy = new ArrayList<>(products);

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < Math.min(numberOfProducts, products.size()); i++) {
            randomProducts.add( copy.remove( rand.nextInt( copy.size() ) ));
        }
        return randomProducts;
    }

    public double getShipping(String designerCity,String userCity,int quantity){

        double shippingPriceGIG = 0;
        List<Shipping> shippings = shippingRepository.getPrice(designerCity,userCity);
        System.out.println(designerCity);
        System.out.println(userCity);
        System.out.println(shippings);

        for (Shipping shipping : shippings){

            //ZonePrice zonePrice = null;
            System.out.println(shipping.getSource());
            if(shipping.getSource() != null) {
                Double zonePrice;
                int currentShipping = 0;
                if (shipping.getZone().equals("1")) {
                    zonePrice = zonePriceRepository.getZoneOnePrice(quantity);
                    currentShipping += zonePrice;
                } else if (shipping.getZone().equals("2")) {
                    zonePrice = zonePriceRepository.getZoneTwoPrice(quantity);
                    currentShipping += zonePrice;
                } else if (shipping.getZone().equals("3")) {
                    zonePrice = zonePriceRepository.getZoneThreePrice(quantity);
                    currentShipping += zonePrice;
                } else if (shipping.getZone().equals("4")) {
                    zonePrice = zonePriceRepository.getZoneFourPrice(quantity);
                    currentShipping += zonePrice;
                }

                if (shipping.getSource().equals("1")) {
                    shippingPriceGIG += currentShipping;
                } else if (shipping.getSource().equals("2")) {
                    shippingPriceGIG += currentShipping;
                } else {
                    return 0;
                }

            }

        }
        //        HashMap hm = new HashMap();
//        hm.put("DHL", new Double(shippingPriceDHL));
//        hm.put("GIG", new Double(shippingPriceGIG));

        return shippingPriceGIG;

    }





}
