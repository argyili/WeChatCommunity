package com.hfut.community.demo.controller;

import com.hfut.community.demo.domain.*;
import com.hfut.community.demo.service.SuperAdminService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Controller
public class SuperAdminController {
    /**
     */
    @Autowired
    private SuperAdminService superadminservice;
    /**
     * 默认的图片储存位置的绝对路径
     */
    private static final String UPLOAD_DIRECTORY =
            "C:\\File\\gitObject\\wxcommunity\\backdevelop\\"
                    + "classes\\artifacts\\tms_war_exploded\\WEB-INF"
                    + "\\classes\\static\\image\\";
    /**
     *AdvertisementManageController
     * @param advertisementctrlmsg AdvertisementCtrlMsg
     * @param model Model
     * @param attr RedirectAttributes
     * @return String
     */
    @RequestMapping(value = "/superAdmin/advertisementctrl",
            method = RequestMethod.GET)
    public String advertisementManage(
            @ModelAttribute(value = "advertisementctrlmsg")
                    AdvertisementCtrlMsg advertisementctrlmsg,
            Model model, RedirectAttributes attr) {
        String result;
        if (advertisementctrlmsg != null) {
            String deleteId;
            Advertisement advertisement;
            if (advertisementctrlmsg.getViewId() != null
                    && !"".equals(advertisementctrlmsg.getViewId())) {
                advertisement = superadminservice
                        .getAdvertisement(advertisementctrlmsg.getViewId());
                if (advertisement != null) {
                    model.addAttribute("aid", advertisement.getAid());
                    model.addAttribute("img", "../image/" + advertisement.getImg());
                    model.addAttribute(
                            "advcontent", advertisement.getAdvContent());
                    return "admin/superAdmin/viewAdvertisement";
                }
                deleteId = "未查询到ID为:" + advertisementctrlmsg.getViewId() + "的广告！";
                model.addAttribute("Result", deleteId);
            } else if (advertisementctrlmsg.getModificationId() != null
                    && !"".equals(advertisementctrlmsg.getModificationId())) {
                advertisement =
                        superadminservice.getAdvertisement(
                                advertisementctrlmsg.getModificationId());
                if (advertisement != null) {
                    return ("redirect:/superAdmin/advertisementmodificationctrl"
                            + "?aid=" + getUTF8(advertisement.getAid()));
                }
                deleteId = "未查询到ID为:" + advertisementctrlmsg.getModificationId() + "的广告！";
                model.addAttribute("Result", deleteId);
            } else if (advertisementctrlmsg.getDeleteId() != null
                    && !"".equals(advertisementctrlmsg.getDeleteId())) {
                result = "";
                deleteId = advertisementctrlmsg.getDeleteId();
                if (superadminservice.deleteAdvertisement(deleteId)) {
                    result = "ID为:" + deleteId + "的广告已删除成功！";
                } else {
                    result = "ID为:" + deleteId + "的广告删除失败！";
                }
                attr.addAttribute("Result", result);
                return "redirect:/superAdmin/advertisementctrl";
            }
        }
        result = advertisementctrlmsg.getResult();
        if (result != null && !"".equals(result)) {
            model.addAttribute("Result", advertisementctrlmsg.getResult());
        }
        String getLike = null;
        if (advertisementctrlmsg != null) {
            getLike = advertisementctrlmsg.getLikeAdvContent();
        }
        List list;
        if (getLike != null && !"".equals(getLike)) {
            list = superadminservice.getAllAdvertisementLikeIt(getLike);
        } else {
            list = superadminservice.getAllAdvertisement();
        }
        model.addAttribute("list", list);
        return "./admin/superAdmin/advertisementManage";
    }
    /**
     * @param text String
     * @return String
     */
    private String getUTF8(String text) {
        StringBuffer strbuff = new StringBuffer();
        strbuff.append(text);
        String xmString = "";
        String xmlUTF8 = "";
        try {
            xmString = new String(strbuff.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return xmlUTF8;
    }
    /**
     * @param message AdvertisementUpdateMsg
     * @param model Model
     * @param attr RedirectAttributes
     * @return String
     */
    @RequestMapping(
            value = "/superAdmin/advertisementmodificationctrl",
            method = RequestMethod.GET)
    public String advModification(
            @ModelAttribute(value = "advertisementupdatemsg")
                    AdvertisementUpdateMsg message,
            Model model, RedirectAttributes attr) {
        if (message != null && message.getImg() != null
                && message.getAdvContent() != null
                && !"".equals(message.getImg())
                && !"".equals(message.getAdvContent())) {
            String getNewing = message.getNewImg();
            if (getNewing != null && !"".equals(getNewing)) {
                Advertisement temp = new Advertisement();
                temp.setAid(message.getAid());
                temp.setImg("" + message.getNewImg());
                temp.setAdvContent(message.getAdvContent());
                superadminservice.updateAdvertisement1(temp);
            } else {
                superadminservice.updateAdvertisement2(
                        message.getAid(), message.getAdvContent());
            }
        }
        Advertisement advertisement = superadminservice
                .getAdvertisement(message.getAid());
        message.setAid(advertisement.getAid());
        message.setImg("../image/" + advertisement.getImg());
        message.setAdvContent(advertisement.getAdvContent());
        model.addAttribute("aid2", advertisement.getAid());
        return "admin/superAdmin/updateAdvertisement";
    }
    /**
     * @param message AdvertisementUpdateMsg
     * @param model Model
     * @return String
     */
    @RequestMapping(
            value = "/superAdmin/advertisementaddctrl",
            method = RequestMethod.GET)
    public String advAdd(
            @ModelAttribute(value = "advertisementmodificationmsg")
                    AdvertisementUpdateMsg message, Model model) {
        if (message != null && message.getAdvContent() != null
                && message.getImg() != null
                && !"".equals(message.getAdvContent())
                && !"".equals(message.getNewImg())) {
            String advcontent = message.getAdvContent();
            String img = message.getNewImg();
            img = "" + img;
            int num = superadminservice.getAdvertisementMax();
            ++num;
            String aid = String.valueOf(num);
            Advertisement advertisement = new Advertisement();
            advertisement.setAid(aid);
            advertisement.setImg(img);
            advertisement.setAdvContent(advcontent);
            if (superadminservice.addAdvertisement(advertisement)) {
                message.setResult("添加成功！");
                return ("redirect:/superAdmin/advertisementctrl");
            } else {
                message.setResult("添加失败！");
            }
        }
        return "./admin/superAdmin/addAdvertisement";
    }
    /**
     * UpgradePlateAdminController
     * @param message UserUpgradeMsg
     * @param model Model
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     * @return String
     */
    @RequestMapping(
            value = "/superAdmin/userupgrade", method = RequestMethod.GET)
    public String test1(
            @ModelAttribute(value = "userupgrademsg")
                    UserUpgradeMsg message,
            Model model, HttpServletResponse response,
            HttpServletRequest request) {
        if (message != null) {
            String phone = message.getUserPhone();
            String choice = message.getChoice();
            model.addAttribute("userphone", phone);
            ArrayList resultList;
            if ("是".equals(choice)) {
                resultList = new ArrayList();
                User user = null;
                user = superadminservice.getUserByPhone(phone);
                ArrayList region = new ArrayList();

                UpgradePlateAdminHTMLMsg result;
                try {
                    response.setCharacterEncoding("UTF-8");
                    request.setCharacterEncoding("UTF-8");
                    String[] regionlist = request.getParameterValues("region");
                    for (int i = 0; i < regionlist.length; i++) {
                        region.add(regionlist[i]);
                    }
                    if (regionlist.length == 0) {
                        result = new UpgradePlateAdminHTMLMsg("升级用户 "
                                + user.getNickname() + "(" + user.getWxid()
                                + ")为板块管理员失败！请选择所管理的板块。");
                        resultList.add(result);
                    } else {
                        List<Boolean> resultlist = superadminservice
                                .upgradeUser(phone, region);
                        if ((resultlist.size() == 0)
                                || (resultlist.size() == 1
                                && !resultlist.get(0))) {
                            result = new UpgradePlateAdminHTMLMsg("升级用户 "
                                    + user.getNickname() + "(" + user.getWxid()
                                    + ")为板块管理员失败！请检查其是否已为管理员身份。");
                            resultList.add(result);
                        } else {
                            for (int i = 0; i < resultlist.size()
                                    && i < region.size(); i++) {
                                String temp;
                                if (resultlist.get(i)) {
                                    temp = "成功升级用户 "
                                            + user.getNickname() + "("
                                            + user.getWxid() + ")为"
                                            + region.get(i) + "板块管理员！";
                                } else {
                                    temp = "升级用户 " + user.getNickname()
                                            + "(" + user.getWxid() + ")为"
                                            + region.get(i)
                                            + "板块管理员失败！请检查是否已有负责该板块的板块管理员存在。";
                                }
                                result = new UpgradePlateAdminHTMLMsg(temp);
                                resultList.add(result);
                            }
                        }
                    }
                } catch (UnsupportedEncodingException e) {
                    result = new UpgradePlateAdminHTMLMsg("升级用户 "
                            + user.getNickname()
                            + "(" + user.getWxid()
                            + ")为板块管理员失败！请检查网络状态。");
                    resultList.add(result);
                }
                model.addAttribute("upgradeResult", resultList);
                model.addAttribute("hasresult", "0");
            } else {
                resultList = null;
                User user = superadminservice.getUserByPhone(phone);
                if (user != null) {
                    model.addAttribute("hasresult", "-2");
                    model.addAttribute("nameresult", user.getNickname());
                    model.addAttribute("idresult", user.getWxid());
                } else if (phone != null && !"".equals(phone)) {
                    model.addAttribute("hasresult", "2");
                } else {
                    model.addAttribute("hasresult", "0");
                }
            }
        } else {
            model.addAttribute("hasresult", "0");
        }
        return "admin/superAdmin/userUpgrade";
    }
    /**
     * UploadController
     * @param file MultipartFile
     * @return String
     */
    @RequestMapping(value = "/superAdmin/addadvertisement",
            method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                File directory = new File(UPLOAD_DIRECTORY);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                String filePath =  FilenameUtils
                        .concat(UPLOAD_DIRECTORY, file.getOriginalFilename());
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "./admin/superAdmin/test";
    }
    /**
     * @param user User
     * @param model Model
     * @return String
     **/
    @RequestMapping(value = "/superAdmin/banuserstatementctrl",
            method = RequestMethod.GET)
    public String banUserStatement(@ModelAttribute(value = "User")
                                           User user, Model model) {
        String wxid = user.getWxid();
        System.out.println("wxid=" + wxid);
        if (user.getWxid() == null) {
            return "admin/superAdmin/banUserStatement";
        }
        else {
            try {
                int forbidden = 1;
                int result = superadminservice.updatebanUser(wxid, forbidden);
                if (result == 1) {
                    model.addAttribute("message", "禁言成功");
                } else {
                    model.addAttribute("message", "没有找到该用户");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "admin/superAdmin/banUserStatement";
        }
    }
    @RequestMapping(value = "/superAdmin/identifyStudent")
    public String identifyStudent(
            @ModelAttribute(value = "student") StudentView student, Model model) {
        if (student != null && null != student.getWxid()) {
            if(2 != student.getIdentification()) {
                superadminservice.identifyStudent(student.getWxid(), student.getIdentification());
            } else {
                student = superadminservice.searchStudent(student.getWxid());
                student.setImg("../image/" + student.getImg());
                model.addAttribute("student", student);
                return "admin/superAdmin/identifyStudentInfo";
            }
        }
        List<StudentView> students = superadminservice.searchNotIdentifiedStudent();
        model.addAttribute("searchStudents",students);
        return "admin/superAdmin/identifyStudent";
    }
    @RequestMapping(value = "/superAdmin/identifyTeacher")
    public String identifyTeacher(
            @ModelAttribute(value = "teacher") TeacherView teacher, Model model) {
        if (teacher != null && null != teacher.getWxid()) {
            if(2 != teacher.getIdentification()) {
                superadminservice.identifyTeacher(teacher.getWxid(), teacher.getIdentification());
            } else {
                teacher = superadminservice.searchTeacher(teacher.getWxid());
                teacher.setImg("../image/" + teacher.getImg());
                model.addAttribute("teacher", teacher);
                return "admin/superAdmin/identifyTeacherInfo";
            }
        }
        List<TeacherView> teachers = superadminservice.searchNotIdentifiedTeacher();
        model.addAttribute("searchTeachers",teachers);
        return "admin/superAdmin/identifyTeacher";
    }
}
