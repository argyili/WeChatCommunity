package com.hfut.community.demo.controller;

import com.alibaba.fastjson.JSON;
import com.hfut.community.demo.domain.Post;
import com.hfut.community.demo.domain.Student;
import com.hfut.community.demo.domain.Teacher;
import com.hfut.community.demo.domain.User;
import com.hfut.community.demo.domain.Parents;
import com.hfut.community.demo.domain.Comment;
import com.hfut.community.demo.domain.CommentToMe;
import com.hfut.community.demo.domain.Advertisement;
import com.hfut.community.demo.domain.CommentByMyself;
import com.hfut.community.demo.domain.StudentView;
import com.hfut.community.demo.domain.ParentsView;
import com.hfut.community.demo.domain.TeacherView;
import com.hfut.community.demo.domain.ChoiceView;
import com.hfut.community.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
/**
 */
@Controller
public class UserController {
    /**
     * @param INDEXTIME int
     **/
    private static final int INDEX_TIME = 16;
    /**
     */
    private static final int PREPARE_IDENTITY = 2;
    /**
     */
    private static final int NO_IDENTITY = 0;
    /**
     */
    private static final String NOT_FOBIDDEN = "0";
    /**
     */
    private static final int NO_PRIZE = 0;
    /**
     * @param REF int
     **/
    public static final int REF = 2;
    /**
     */
    private static final int FILE_INDEX = -1;
    /**
     */
    private static final int STUDENT = 0;
    /**
     */
    private static final int PARENTS = 1;
    /**
     */
    private static final int SCHOOL_TEACHER = 2;
    /**
     */
    private static final int AGENT_TEACHER = 3;
    /**
     */
    private static final int OTHERS = 4;
    /**
     */
    @Autowired
    private UserService userService;
    /**
     * 判断是否为图片文件
     * @param type String
     * @return boolean
     */
    private boolean isPictrue(String type) {
        if ("GIF".equals(type.toUpperCase())) {
            return true;
        } else if ("PNG".equals(type.toUpperCase())) {
            return true;
        } else if ("JPG".equals(type.toUpperCase())) {
            return true;
        }
        return false;
    }
    /**
     * 添加帖子
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "/User/addPost", method = RequestMethod.GET)
    public void addPost(
            HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        Post post = new Post();
        Timestamp d = new Timestamp(System.currentTimeMillis());
        String time = d.toString();
        post.setPostingTime(time);
        post.setCommentsNum(0);
        post.setCriticismNum(0);
        post.setEyesNum(0);
        post.setFold(0);
        post.setPraiseNum(0);
        post.setForbidComment(0);
        int id = userService.getMaxId() + 1;
        String pid = String.valueOf(id);
        post.setPid(pid);
        String wxid = request.getParameter("wxid");
        String block = request.getParameter("block");
        String content = request.getParameter("content");
		
		/*支持简易markdown语法*/
		int posStart = content.indexOf("<p>") + 3;
		int posEnd = content.indexOf("</p>");
		content.substring(posStart, posEnd);
		
        String title = request.getParameter("title");
        post.setBlock(block);
        post.setWxid(wxid);
        post.setContent(content);
        post.setTitle(title);
        userService.insertPost(post);
    }
     /**
     * 插入老师信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param file String
     * @throws  IOException 抛错
     */
    @RequestMapping(value = "/User/addTeacher")
    public void addTeacher(
            HttpServletResponse response, HttpServletRequest request,
            @RequestParam(value = "file", required = false)MultipartFile file
    ) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String wxid = request.getParameter("wxid");
        String phoneNum = request.getParameter("phoneNum");
        Integer activeIndex =
        Integer.parseInt(request.getParameter("activeIndex"));
        String block = request.getParameter("block");
        String path = addImage(wxid, request, file);
        User user = userService.searchUser(wxid);
        boolean isNewUser = false;
        if (null == user) {
            isNewUser = true;
            user = new User();
        }
        user.setWxid(wxid);
        user.setNickname(wxid);
        user.setRole(activeIndex);
        user.setRegion(block);
        user.setPhoneNum(phoneNum);
        user.setForbidden(NOT_FOBIDDEN);
        Teacher teacher = new Teacher();
        teacher.setWxid(wxid);
        if (null != path) {
            teacher.setIdentification(PREPARE_IDENTITY);
            teacher.setImg(path);
        } else {
            teacher.setIdentification(NO_IDENTITY);
            teacher.setImg(null);
        }
        if (isNewUser) {
            userService.insertTeacher(user, teacher);
        } else {
            userService.updateTeacher(user, teacher);
        }
    }
     /**
     * 插入学生信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param file String
     * @throws  IOException 抛错
     */
    @RequestMapping(value = "/User/addStudent")
    public void addStudent(
            HttpServletResponse response, HttpServletRequest request,
            @RequestParam(value = "file", required = false)MultipartFile file
    )throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String wxid = request.getParameter("wxid");
        String phoneNum = request.getParameter("phoneNum");
        Integer activeIndex =
        Integer.parseInt(request.getParameter("activeIndex"));
        String block = request.getParameter("block");
        Integer priceIndex =
        Integer.parseInt(request.getParameter("priceIndex"));
        String school = request.getParameter("school");
        String path = addImage(wxid, request, file);
        User user = userService.searchUser(wxid);
        boolean isNewUser = false;
        if (null == user) {
            isNewUser = true;
            user = new User();
        }
        user.setWxid(wxid);
        user.setNickname(wxid);
        user.setRole(activeIndex);
        user.setRegion(block);
        user.setPhoneNum(phoneNum);
        user.setForbidden(NOT_FOBIDDEN);
        Student student = new Student();
        student.setWxid(wxid);
        student.setSchool(school);
        if (null != path) {
            student.setIdentification(PREPARE_IDENTITY);
            student.setTopPrize(priceIndex);
            student.setImg(path);
        } else {
            student.setIdentification(NO_IDENTITY);
            student.setTopPrize(NO_PRIZE);
            student.setImg(null);
        }
        if (isNewUser) {
            userService.insertStudent(user, student);
        } else {
            userService.updateStudent(user, student);
        }
    }
    /**
     * 添加其他信息
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "/User/addOther", method = RequestMethod.GET)
    public void addOther(
            HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String wxid = request.getParameter("wxid");
        String phoneNum = request.getParameter("phoneNum");
        Integer activeIndex =
        Integer.parseInt(request.getParameter("activeIndex"));
        String block = request.getParameter("block");
        User user = userService.searchUser(wxid);
        boolean isNewUser = false;
        if (null == user) {
            isNewUser = true;
            user = new User();
        }
        user.setWxid(wxid);
        user.setNickname(wxid);
        user.setRole(activeIndex);
        user.setRegion(block);
        user.setPhoneNum(phoneNum);
        user.setForbidden(NOT_FOBIDDEN);
        if (isNewUser) {
            userService.insertOthers(user);
        } else {
            userService.updateOthers(user);
        }
    }
    /**
     * 插入图片
     * @param request HttpServletRequest
     * @param file String
     * @throws  IOException 抛错
     * @return String
     */
     public String addImage(
             String wxid, HttpServletRequest request,
             @RequestParam(value = "file", required = false)MultipartFile file)
             throws IOException {
        if (null != file && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String type =
                    fileName.indexOf(".") != FILE_INDEX ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {
                if ("GIF".equals(type.toUpperCase())
                        || "PNG".equals(type.toUpperCase())
                        || "JPG".equals(type.toUpperCase())) {
                    String realPath = "C:\\Users\\明明\\Desktop\\修改\\wxcommunity\\backdevelop\\src\\main\\resources\\static\\image/";
                    String mypath = wxid + "." + type;
                    String path = realPath + mypath;
                    file.transferTo(new File(path));
                    return mypath;
                }
            }
        }
        return null;
    }
    /**
     * 查找所有的帖子
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchAllPost", method = RequestMethod.GET)
    public void searchAllPost(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            List<Post> post = userService.searchAllPost();
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查找所有的精华帖子
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(
            value = "/User/searchAllRefinementPost", method = RequestMethod.GET)
    public void searchAllRefinementPost(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            List<Post> post = userService.searchAllRefinementPost();
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照版块查找
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchByBlock", method = RequestMethod.GET)
    public void searchByBlock(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String block = request.getParameter("block");
            List<Post> post = userService.searchByBlock(block);
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照微信号查找
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchByWxid", method = RequestMethod.GET)
    public void searchByWxid(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            List<Post> post = userService.searchByWxid(wxid);
            post = userService.subList(post);
            for (int i = 0; i < post.size(); i++) {
                String postingTime = post.get(i).getPostingTime();
                postingTime = postingTime.substring(0, INDEX_TIME);
                post.get(i).setPostingTime(postingTime);
            }
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 添加评论
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
     @RequestMapping(value = "/User/addComment", method = RequestMethod.GET)
    public void addComment(
            HttpServletResponse response, HttpServletRequest request) {
         response.setContentType("text/html;charset=utf-8");
         response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "GET,POST");
         Timestamp d = new Timestamp(System.currentTimeMillis());
         String time = d.toString();
         int cid = userService.getMaxCid() + 1;
         String wxid = request.getParameter("wxid");
         Integer pid = Integer.parseInt(request.getParameter("pid"));
         String commentContent = request.getParameter("commentContent");
         Comment comment = new Comment();
         comment.setCid(cid);
         comment.setWxid(wxid);
         comment.setCommentTime(time);
         comment.setCommentContent(commentContent);
         comment.setPid(pid);
         userService.insertComment(comment);
     }

    /**
     * 添加家长信息
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     */
    @RequestMapping(value = "/User/addParent", method = RequestMethod.GET)
    public void addParent(
            HttpServletResponse response, HttpServletRequest request) {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String wxid = request.getParameter("wxid");
        String phoneNum = request.getParameter("phoneNum");
        Integer goalIndex = Integer.parseInt(request.getParameter("goalIndex"));
        Integer activeIndex =
                Integer.parseInt(request.getParameter("activeIndex"));
        String block = request.getParameter("block");
        User user = userService.searchUser(wxid);
        boolean isNewUser = false;
        if (null == user) {
            isNewUser = true;
            user = new User();
        }
        user.setWxid(wxid);
        user.setNickname(wxid);
        user.setRole(activeIndex);
        user.setRegion(block);
        user.setPhoneNum(phoneNum);
        user.setForbidden(NOT_FOBIDDEN);
        Parents parents = new Parents();
        parents.setWxid(wxid);
        parents.setPurpose(goalIndex);
        if (isNewUser) {
            userService.insertParents(user, parents);
        } else {
            userService.updateParents(user, parents);
        }
    }
    /**
     * 查看所有关于我的所有帖子的评论 根据我的微信号查找
     * @param request HttpServletRequest
     *前端传来wxid
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/commentToMe", method = RequestMethod.GET)
    public void commentToMe(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            List<CommentToMe> commentToMe = userService.commentToMe(wxid);
            for (int i = 0; i < commentToMe.size(); i++) {
                String commentTime = commentToMe.get(i)
                        .getCommentTime()
                        .substring(0, INDEX_TIME);
                commentToMe.get(i).setCommentTime(commentTime);
            }
            String listJson = JSON.toJSONString(commentToMe);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查找评论人的昵称
     * @param response HttpServletResponse
     * @param request HttpServletRequest
     */
    @RequestMapping(
            value = "/User/commentToMeNickname", method = RequestMethod.GET)
    public void commentToMeNickname(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            List<String> nickname = new ArrayList<String>();
            List<CommentToMe> commentToMe = userService.commentToMe(wxid);
            for (int i = 0; i < commentToMe.size(); i++) {
                int cid = commentToMe.get(i).getCid();
                String name = userService.getNickname(cid);
                nickname.add(name);
            }
            String listJsonNickname = JSON.toJSONString(nickname);
            Writer out = response.getWriter();
            out.write(listJsonNickname);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查看所有我发表的评论按照最新时间排序
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/commentByMyselef", method = RequestMethod.GET)
    public void commentByMyselef(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            List<CommentByMyself> commentByMyself =
                    userService.commentByMyself(wxid);
            for (int i = 0; i < commentByMyself.size(); i++) {
                String postingTime = commentByMyself.get(i)
                        .getCommentTime()
                        .substring(0, INDEX_TIME);
                commentByMyself.get(i).setCommentTime(postingTime);
            }
            String listJson = JSON.toJSONString(commentByMyself);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照指定的版块查找精华帖
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(
            value = "/User/searchRefPostByBlock", method = RequestMethod.GET)
    public void searchRefPostByBlock(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String block = request.getParameter("block");
            List<Post> post = userService.searchRefPostByBlock(block);
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照pid查找该帖子的具体内容
     * @param pid int
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchPostByPid", method = RequestMethod.GET)
    public void searchPostByPid(
            int pid, HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            Post post = userService.searchPostByPid(pid);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照pid查找该帖子的所有评论
     * @param pid int
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(
            value = "/User/searchCommentByPid", method = RequestMethod.GET)
    public void searchCommentByPid(
            int pid, HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            List<Comment> comment = userService.searchCommentByPid(pid);
            String listJson = JSON.toJSONString(comment);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查看所有对我发表的帖子的点赞和踩
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(
            value = "/User/searchChoiceToMe", method = RequestMethod.GET)
    public void searchChoiceToMe(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            List<ChoiceView> choiceToMe = userService.searchChoiceToMe(wxid);
            for (int i = 0; i < choiceToMe.size(); i++) {
                String commentTime = choiceToMe.get(i)
                        .getChoiceTime()
                        .substring(0, INDEX_TIME);
                choiceToMe.get(i).setChoiceTime(commentTime);
            }
            String listJson = JSON.toJSONString(choiceToMe);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查看所有对我发表的帖子的点赞和踩数目
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/achieveInfoNum", method = RequestMethod.GET)
    public void achieveInfoNum(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            int choiceToMeNum = userService.searchChoiceToMeNum(wxid, 0);
            int commentToMeNum = userService.commentToMeNum(wxid, 0);
            int num = choiceToMeNum + commentToMeNum;
            userService.changeChoiceToMeIsView(wxid, 1);
            userService.changeCommentToMeIsView(wxid, 1);
            String strNum = Integer.toString(num);
            String listJson = JSON.toJSONString(strNum);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照版块，加精，排序（综合，时间，点赞，点踩，评论倒叙）
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchPostBy", method = RequestMethod.GET)
    public void searchPostBy(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String block = request.getParameter("block");
            String sequence = request.getParameter("sequence");
            Integer ref = Integer.parseInt(request.getParameter("ref"));
            List<Post> post = userService.searchPostBy(block, sequence, ref);
            post = userService.subTime(post);
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 点赞与点踩
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/makeChoice", method = RequestMethod.GET)
    public void makeChoice(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            Integer pid = Integer.parseInt(request.getParameter("pid"));
            Integer type = Integer.parseInt(request.getParameter("type"));
            int num = userService.makeChoice(wxid, pid, type);
            String listJson = JSON.toJSONString(Integer.toString((num)));
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 模糊搜索
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchVague", method = RequestMethod.GET)
    public void searchVague(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String title = request.getParameter("title");
            List<Post> post = userService.searchVague(title);
            post = userService.subTime(post);
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 24小时热门
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchHot", method = RequestMethod.GET)
    public void searchHot(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            List<Post> post = userService.searchHot("全国", REF);
            post = userService.subList(post);
            String listJson = JSON.toJSONString(post);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 按照微信号查找用户个人信息
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/searchUser", method = RequestMethod.GET)
    public void searchUserInfo(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            String wxid = request.getParameter("wxid");
            User user = userService.searchUser(wxid);
            String json = "";
            if (null != user) {
                switch (user.getRole()) {
                    case STUDENT:
                        StudentView student = userService.searchStudent(wxid);
                        json = JSON.toJSONString(student);
                        break;
                    case PARENTS:
                        ParentsView parents = userService.searchParents(wxid);
                        json = JSON.toJSONString(parents);
                        break;
                    case SCHOOL_TEACHER:
                    case AGENT_TEACHER:
                        TeacherView teacher = userService.searchTeacher(wxid);
                        json = JSON.toJSONString(teacher);
                        break;
                    case OTHERS:
                        json = JSON.toJSONString(user);
                        break;
                    default:
                }
            } else {
                user = new User();
                user.setWxid(null);
                user.setNickname(null);
                user.setRole(0);
                user.setPhoneNum(null);
                user.setRegion("0");
                user.setForbidden("0");
                json = JSON.toJSONString(user);
            }
            Writer out = response.getWriter();
            out.write(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 列出所有广告
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/User/listAdv", method = RequestMethod.GET)
    public void listAdv(
            HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET,POST");
            List<Advertisement> adv = userService.listAdv();
            adv = userService.subListAdv(adv);
            String listJson = JSON.toJSONString(adv);
            Writer out = response.getWriter();
            out.write(listJson);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

