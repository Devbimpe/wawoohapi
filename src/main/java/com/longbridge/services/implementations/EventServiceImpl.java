package com.longbridge.services.implementations;

import com.longbridge.dto.*;
import com.longbridge.exception.WawoohException;
import com.longbridge.models.*;
import com.longbridge.repository.*;
import com.longbridge.security.repository.UserRepository;
import com.longbridge.services.EventService;
import com.longbridge.services.ProductService;
import org.hibernate.type.descriptor.sql.SmallIntTypeDescriptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

/**
 * Created by Longbridge on 06/11/2017.
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventPictureRepository eventPictureRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PictureTagRepository pictureTagRepository;


    private ModelMapper modelMapper;

    @Autowired
    public EventServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Value("${event.mainpicture.folder}")
    private String eventMainPictureImagePath;

    @Value("${event.picture.folder}")
    private String eventPicturesImagePath;

    @Value("${s.event.mainpicture.folder}")
    private String eventMainPictureFolder;

    @Value("${s.event.picture.folder}")
    private String eventPicturesFolder;

    @Override
    public void createEvent(Events e) {
       // Map<String,Object> responseMap = new HashMap();
        try {

            String time = "evtmpic" +getCurrentTime();
            String fileName = e.eventName.replaceAll("\\s","") + time;
            //fileName = fileName.replaceAll("\\s","");

            String base64Image = e.mainPicture.split(",")[1];
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            File imgfile =new File(eventMainPictureFolder + fileName);
            FileOutputStream ff = new FileOutputStream(imgfile);
            int read = 0;
            final byte[] bytes = new byte[3072];
            while ((read = bis.read(bytes)) != -1) {
                ff.write(bytes, 0, read);
            }

            Date date = new Date();
            e.setCreatedOn(date);
            e.setUpdatedOn(date);
            e.setMainPictureName(fileName);
            e.getEventPictures().forEach(pictures -> {
                pictures.events=e;
                pictures.createdOn=date;
                pictures.setUpdatedOn(date);
                String timeStamp = "evtpic" + getCurrentTime();
                String fName = e.eventName.replaceAll("\\s","") + timeStamp;
                //fName = fName.replaceAll("\\s","");
                while (!nameExists(fName)){
                    try {
                    String base64Img = pictures.picture.split(",")[1];
                    byte[] imgBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Img);
                    ByteArrayInputStream bs = new ByteArrayInputStream(imgBytes);
                    File imgfilee =new File(eventPicturesFolder + fName);
                    pictures.pictureName=fName;
                    FileOutputStream f = new FileOutputStream(imgfilee);
                    int rd = 0;
                    final byte[] byt = new byte[1024];
                    while ((rd = bs.read(byt)) != -1) {
                        f.write(byt, 0, rd);
                    }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    break;
                }

            });
            eventRepository.save(e);
        }
        catch (Exception ex){
            ex.printStackTrace();
            throw new WawoohException();
        }
    }

    @Override
    public List<EventsDTO> getTopFiveEventMainPictures() {
            try {
                List<Events> firstFiveEvent = eventRepository.findTop5ByOrderByEventDateDesc();
                List<EventsDTO> eventsDTOS = convertEntitiesToDTOs(firstFiveEvent);
                return eventsDTOS;
            }catch (Exception e){
                throw new WawoohException();
            }
    }

    @Override
    public List<EventsDTO> getEventByDate(EventDateDTO eventDateDTO) {

        String mnt = eventDateDTO.getMonth();
        String yr = eventDateDTO.getYear();
        int year = Integer.parseInt(yr);
        int month = Integer.parseInt(mnt);
        int page = Integer.parseInt(eventDateDTO.getPage());
        int size = Integer.parseInt(eventDateDTO.getSize());

        YearMonth date = YearMonth.of(year,month);
        try {
            //getting date interval from ist day of month to last day e.g 2017-12-01 to 2017-12-31
            LocalDate startDateMonth = date.atDay(1);
            LocalDate endDateMonth = date.atEndOfMonth();

            //converting local date to date format
            Date date1 = Date.from(startDateMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date date2 = Date.from(endDateMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Page<Events> events = eventRepository.findByEventDateBetween(date1,date2,new PageRequest(page,size));
            if(page > events.getTotalPages()){
                //throw new WawoohException("events not found");
            }
            List<EventsDTO> eventsDTOS = convertEntitiesToDTOs(events.getContent());
            return eventsDTOS;
        }catch (Exception ex){
            ex.printStackTrace();
            throw new WawoohException();
        }

    }


    @Override
    public List<EventsDTO> getEvents(EventDateDTO eventDateDTO) {

        int page = Integer.parseInt(eventDateDTO.getPage());
        int size = Integer.parseInt(eventDateDTO.getSize());
        List<EventsDTO> eventsDTOS = new ArrayList<>();

        try {
            Page<Events> events = eventRepository.findAll(new PageRequest(page,size));

            if(page > events.getTotalPages()){
               // throw new WawoohException("events not found");
            }
            eventsDTOS = convertEntitiesToDTOs(events.getContent());

            return eventsDTOS;

        }catch (Exception ex){
            ex.printStackTrace();
            throw new WawoohException();
        }
    }

    @Override
    public List<EventPicturesDTO> getEventById(Long id) {

        try {
            Events event = eventRepository.findOne(id);
            List<EventPictures> e = event.getEventPictures();
            List<EventPicturesDTO> edto = new ArrayList<>();
            for(EventPictures eventPictures : e){
                EventPicturesDTO picturesDTO = convertEntityToDTO(eventPictures);
                edto.add(picturesDTO);
            }


            return edto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WawoohException();
        }

    }


    @Override
    public List<EventPicturesDTO> getEventById(Long id, User user) {

        try {
            Events event = eventRepository.findOne(id);
            List<EventPictures> e = event.getEventPictures();
            List<EventPicturesDTO> edto = new ArrayList<>();

            for(EventPictures eventPictures : e){
                EventPicturesDTO picturesDTO = convertEntityToDTO(eventPictures);
                Likes likes = likeRepository.findByUserAndEventPictures(user,eventPictures);
                if(likes != null){
                    picturesDTO.setLiked("true");
                    edto.add(picturesDTO);
                }
                else {
                    picturesDTO.setLiked("false");
                    edto.add(picturesDTO);
                }
            }
            return edto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WawoohException();
        }
    }


    @Override
    public EventPicturesDTO getEventPictureById(Long id) {
        Map<String,Object> responseMap = new HashMap();
        try {
            EventPictures eventPictures = eventPictureRepository.findOne(id);

            EventPicturesDTO edto = convertEntityToDTO(eventPictures);

            return edto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new WawoohException();
        }

    }


    @Override
    public EventPicturesDTO getEventPictureById(Long id, User user) {
        try {
            EventPictures eventPictures = eventPictureRepository.findOne(id);
            EventPicturesDTO edto = convertEntityToDTO(eventPictures);
            Likes likes = likeRepository.findByUserAndEventPictures(user,eventPictures);
            if(likes != null){
                edto.setLiked("true");
                return edto;
            }
            else {
                edto.setLiked("false");
                return edto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new WawoohException();
        }

    }

    private String getFileName(String file){
        File f = new File(file);
        String fileName = f.getName();
        return fileName;
    }

    private String getCurrentTime(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        int millis = now.get(Calendar.MILLISECOND);
        String cTime = year+""+month+""+day+""+hour+""+minute+""+second+""+millis;
        return cTime;
    }



    @Override
    public Boolean nameExists(String fileName) {
        EventPictures eventPictures = eventPictureRepository.findByPictureName(fileName);
        return (eventPictures != null) ? true : false;
    }


    @Override
    public List<CommentsDTO> addComment(CommentLikesDTO commentLikesDTO, User user) {

        try {
            Date date = new Date();
            String comment = commentLikesDTO.getComment();
            Long eventPictureId = Long.parseLong(commentLikesDTO.getEventPictureId());
            EventPictures e = eventPictureRepository.findOne(eventPictureId);

            if(user != null && e != null){
                Comments c = new Comments();
                c.eventPictures = e;
                c.comment = comment;
                c.user=user;
                c.setCreatedOn(date);
                c.setUpdatedOn(date);
                commentRepository.save(c);
                List<Comments> comments=commentRepository.findByEventPictures(e);
                List<CommentsDTO> commentsDTOS = convEntsToDTOs(comments);
                return commentsDTOS;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
       throw new WawoohException();
    }

    @Override
    public String addLike(CommentLikesDTO commentLikesDTO, User user) {
        Map<String,Object> responseMap = new HashMap();
        Date date = new Date();
        try {
            Long eventPictureId = Long.parseLong(commentLikesDTO.getEventPictureId());
            EventPictures e = eventPictureRepository.findOne(eventPictureId);
            if(user != null && e !=null){
                Likes likes = likeRepository.findByUserAndEventPictures(user,e);
                if(likes != null){
                    likeRepository.delete(likes);
                    Long count = likeRepository.countByEventPictures(e);
                    return count.toString();
                }
                else {
                    Likes l = new Likes();
                    l.eventPictures = e;
                    l.user=user;
                    l.setCreatedOn(date);
                    l.setUpdatedOn(date);
                    likeRepository.save(l);
                    Long count = likeRepository.countByEventPictures(e);
                    return count.toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        throw new WawoohException();
    }



    private UserDTO convertUserEntityToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.email);
        userDTO.setId(user.id);
        userDTO.setFirstName(user.firstName);
        userDTO.setLastName(user.lastName);
        userDTO.setPhoneNo(user.phoneNo);
        userDTO.setGender(user.gender);
        userDTO.setRole(user.role);
        return userDTO;
    }


    private EventsDTO convertEntityToDTO(Events events){
        EventsDTO eventsDTO = new EventsDTO();
        eventsDTO.setId(events.id);
        eventsDTO.setDescription(events.description);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String stringDate = formatter.format(events.eventDate);
        eventsDTO.setEventDate(stringDate);
        eventsDTO.setEventName(events.getEventName());
        eventsDTO.setLocation(events.location);

        eventsDTO.setMainPicture(eventMainPictureImagePath+events.mainPictureName);
        eventsDTO.setEventPictures(convertEvtPicEntToDTOsMin(eventPictureRepository.findFirst6ByEvents(events)));

        return eventsDTO;

    }

    private CommentsDTO convertEntityToDTO(Comments c){
        CommentsDTO commentsDTO = new CommentsDTO();
        commentsDTO.setComment(c.comment);
        commentsDTO.setId(c.id);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String stringDate = formatter.format(c.createdOn);
        commentsDTO.setCreatedDate(stringDate);
        commentsDTO.setUser(convertUserEntityToUserDTO(c.user));

        return commentsDTO;

    }

    private List<CommentsDTO> convEntsToDTOs(List<Comments> c){
        List<CommentsDTO> commentsDTOS = new ArrayList<CommentsDTO>();

        for(Comments comments: c){
            CommentsDTO commentsDTO = convertEntityToDTO(comments);
            commentsDTOS.add(commentsDTO);
        }
        return commentsDTOS;
    }

    private LikesDTO convEntityToDTO(Likes l){
        LikesDTO likesDTO = new LikesDTO();

        likesDTO.setId(l.id);
        likesDTO.setUser(convertUserEntityToUserDTO(l.user));
        return likesDTO;

    }

    private List<LikesDTO> convertEntsToDTOs(List<Likes> l){
        List<LikesDTO> likesDTOS = new ArrayList<LikesDTO>();

        for(Likes likes: l){
            LikesDTO likesDTO = convEntityToDTO(likes);
            likesDTOS.add(likesDTO);
        }
        return likesDTOS;

    }


    private List<EventsDTO> convertEntitiesToDTOs(List<Events> events){

        List<EventsDTO> eventsDTOS = new ArrayList<EventsDTO>();

        for(Events events1: events){
            EventsDTO eventsDTO = convertEntityToDTO(events1);
            eventsDTOS.add(eventsDTO);
        }
        return eventsDTOS;
    }



    private EventPicturesDTO convertEntityToDTO(EventPictures eventPictures){
        EventPicturesDTO eventPicturesDTO = new EventPicturesDTO();

        List<CommentsDTO> cmts = convEntsToDTOs(eventPictures.comments);
        List<LikesDTO> likes = convertEntsToDTOs(eventPictures.likes);
        eventPicturesDTO.setComments(cmts);
        eventPicturesDTO.setLikes(likes);
        eventPicturesDTO.setPictureDesc(eventPictures.getPictureDesc());
        eventPicturesDTO.setEventName(eventPictures.events.eventName);
        eventPicturesDTO.setId(eventPictures.id);
        List<PictureTag> pictureTags = pictureTagRepository.findPictureTagsByEventPictures(eventPictures);
        List<PicTagDTO> pictureTagDTOS = convertPictureTagEntityToDTO(pictureTags);
        eventPicturesDTO.setTags(pictureTagDTOS);
        eventPicturesDTO.setPicture(eventPicturesImagePath+eventPictures.pictureName);
        return eventPicturesDTO;

    }


    private EventPicturesDTO convertEntityToDTOMin(EventPictures eventPictures){
        EventPicturesDTO eventPicturesDTO = new EventPicturesDTO();
        eventPicturesDTO.setEventName(eventPictures.events.eventName);
        eventPicturesDTO.setId(eventPictures.id);
        eventPicturesDTO.setPicture(eventPicturesImagePath+eventPictures.pictureName);
        eventPicturesDTO.setPictureDesc(eventPictures.getPictureDesc());
        return eventPicturesDTO;

    }


    private List<EventPicturesDTO> convertEvtPicEntToDTOsMin(List<EventPictures> eventPictures){

        List<EventPicturesDTO> eventPicturesDTOS = new ArrayList<EventPicturesDTO>();

        for(EventPictures eventPictures1: eventPictures){
            EventPicturesDTO eventPicturesDTO = convertEntityToDTOMin(eventPictures1);
            eventPicturesDTOS.add(eventPicturesDTO);
        }
        return eventPicturesDTOS;
    }

    private List<PicTagDTO> convertPictureTagEntityToDTO(List<PictureTag> pictureTags){

        List<PicTagDTO> pictureTagDTOS = new ArrayList<PicTagDTO>();

        for(PictureTag p: pictureTags){
            PicTagDTO picTagDTO = convertPicTagEntityToDTO(p);
            pictureTagDTOS.add(picTagDTO);
        }
        return pictureTagDTOS;
    }


    private PicTagDTO convertPicTagEntityToDTO(PictureTag pictureTag){
        PicTagDTO pictureTagDTO = new PicTagDTO();
        pictureTagDTO.id=pictureTag.id;
        pictureTagDTO.topCoordinate=pictureTag.topCoordinate;
        pictureTagDTO.leftCoordinate=pictureTag.leftCoordinate;
        pictureTagDTO.imageSize=pictureTag.imageSize;
        pictureTagDTO.subcategoryId = pictureTag.subCategory.id;
        if(pictureTag.designer != null){
            pictureTagDTO.designerId = pictureTag.designer.id;
            pictureTagDTO.designerName = pictureTag.designer.storeName;
        }

        return pictureTagDTO;

    }

}
