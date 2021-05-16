package com.hfut.community.demo.service;

import com.hfut.community.demo.domain.Advertisement;
import com.hfut.community.demo.domain.User;
import com.hfut.community.demo.domain.Teacher;
import com.hfut.community.demo.domain.TeacherView;
import com.hfut.community.demo.domain.Student;
import com.hfut.community.demo.domain.StudentView;
import com.hfut.community.demo.domain.Parents;
import com.hfut.community.demo.domain.ParentsView;
import com.hfut.community.demo.domain.Comment;
import com.hfut.community.demo.domain.CommentToMe;
import com.hfut.community.demo.domain.CommentByMyself;
import com.hfut.community.demo.domain.Choice;
import com.hfut.community.demo.domain.ChoiceView;
import com.hfut.community.demo.domain.Post;
import com.hfut.community.demo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 */
@Service
public class UserService {
    /**
     */
    @Autowired
    private UserDao userDao;
    /**
     */
    @Autowired
    private PostListService postListService;
    /**
     * 模糊搜索
     * -1
     */
    private static final int VAGUE = -1;
    /**
     * @param INDEX_TIME int
     **/
    private static final int INDEX_TIME = 16;
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
    private static final int LIST_SIZE = 100;
    /**
     * @return int
     */
    public int getMaxId() {
        return userDao.getMaxId();
    }
    /**
     * @return int
     */
    public int getMaxCid() {
        return userDao.getMaxCid();
    }
    /**
     * @param comment Comment
     * @return int
     */
    public int insertComment(Comment comment) {
        Post post = userDao.getPost(comment.getPid());
        int pid = comment.getPid();
        int commentsNum = post.getCommentsNum() + 1;
        userDao.addCommentsNum(commentsNum, pid);
        return userDao.insertComment(comment);
    }
    /**
     * @param post Post
     * @return int
     */
    public int insertPost(Post post) {
         return userDao.insertPost(post);
    }
    /**
     * @return List
     */
    public List<Post> searchAllPost() {
        return userDao.searchAllPost();
    }
    /**
     * @return List
     */
    public List<Post> searchAllRefinementPost() {
        return userDao.searchAllRefinementPost();
    }
    /**
     * @return List
     * @param block String
     */
    public List<Post> searchByBlock(String block) {
        return userDao.searchByBlock(block);
    }
    /**
     * @param wxid Post
     * @return Post
     */
    public List<Post> searchByWxid(String wxid) {
        return userDao.searchByWxid(wxid);
    }
    /**
     * @param wxid String
     * @return List
     */
    public List<CommentToMe> commentToMe(String wxid) {
        return userDao.commentToMe(wxid);
    }
    /**
     * @param wxid String
     * @return List
     */
    public List<CommentByMyself> commentByMyself(String wxid) {
        return userDao.commentByMyself(wxid);
    }
    /**
     * @param cid int
     * @return String
     */
    public String getNickname(int cid) {
        return userDao.getNickname(cid);
    }
    /**
     * 按照指定的版块查找精华帖
     * @param block String
     * @return List
     */
    public List<Post> searchRefPostByBlock(String block) {
        return userDao.searchRefPostByBlock(block);
    }
    /**
     * 按照pid查找该帖子的全部内容
     * @param pid int
     * @return Post
     */
    public Post searchPostByPid(int pid) {
        return userDao.searchPostByPid(pid);
    }
    /**
     * 按照pid得到该帖子的全部评论
     * @param pid int
     * @return list
     */
    public List<Comment> searchCommentByPid(int pid) {
        return userDao.searchCommentByPid(pid);
    }
    /**
     * 按照towxid得到全部对我的帖子的赞或踩
     * @param towxid String
     * @return list
     */
    public List<ChoiceView> searchChoiceToMe(String towxid) {
        return userDao.searchChoiceToMe(towxid);
    }
    /**
     * @param user User
     * @param student Student
     * @return int
     */
    public int insertStudent(User user, Student student) {
        if (STUDENT != user.getRole()) {
            return 0;
        }
        userDao.insertUser(user);
        return userDao.insertStudent(student);
    }
    /**
     * @param user User
     * @param teacher Teacher
     * @return int
     */
    public int insertTeacher(User user, Teacher teacher) {
        if (AGENT_TEACHER != user.getRole()
                && SCHOOL_TEACHER != user.getRole()) {
            return 0;
        }
        userDao.insertUser(user);
        return userDao.insertTeacher(teacher);
    }
    /**
     * @param user User
     * @param parents Parents
     * @return int
     */
    public int insertParents(User user, Parents parents) {
        if (PARENTS != user.getRole()) {
            return 0;
        }
        userDao.insertUser(user);
        return userDao.insertParents(parents);
    }
    /**
     * @param user User
     * @return int
     */
    public int insertOthers(User user) {
        if (OTHERS != user.getRole()) {
            return 0;
        }
        return userDao.insertUser(user);
    }
    /**
     *  按照版块，加精，排序（综合，时间，点赞，点踩，评论倒叙）
     * @param ref int
     * @param block String
     * @param sequence String
     * @return Post
     */
    public List<Post> searchPostBy(String block, String sequence, int ref) {
        List<Post> post = new ArrayList<Post>();
        if (block.equals("全国")) {
            if (sequence.equals("综合排序")) {
                return postListService.getStackOverflowList(block, ref);
            }
            if (sequence.equals("点赞排序")) {
                return userDao.searchByAllRefPraise(ref);
            }
            if (sequence.equals("点踩排序")) {
                return userDao.searchByAllRefCriticis(ref);
            }
            if (sequence.equals("评论排序")) {
                return userDao.searchByAllRefComment(ref);
            }
        } else {
            if (sequence.equals("综合排序")) {
                return postListService.getStackOverflowList(block, ref);
            }
            if (sequence.equals("点赞排序")) {
                return userDao.searchByBlockRefPraise(ref, block);
            }
            if (sequence.equals("点踩排序")) {
                return userDao.searchByBlockRefCriticis(ref, block);
            }
            if (sequence.equals("评论排序")) {
                return userDao.searchByBlockRefComment(ref, block);
            }
        }
        return post;
    }
    /**
     * 将时间长度截取，符合前端需要
     * @param time String
     * @return String
     */
    String time(String time) {
        String postingTime = time.substring(0, INDEX_TIME);
        return postingTime;
    }
    /**
     * 将时间长度截取，符合前端需要
     * @param post List
     * @return String
     */
    public List<Post> subTime(List<Post> post) {
        for (int i = 0; i < post.size(); i++) {
            Post p = post.get(i);
            String postingTime = p.getPostingTime().substring(0, INDEX_TIME);
            post.get(i).setPostingTime(postingTime);
        }
        return post;
    }
    /**
     * 将Post类型list截取，符合前端需要
     * @param post List
     * @return String
     */
    public List<Post> subList(List<Post> post) {
        List<Post> sub = new ArrayList<Post>();
        if (post.size() >= LIST_SIZE) {
            for (int i = 0; i < LIST_SIZE; i++) {
                sub.add(post.get(i));
            }
        } else {
            sub = post;
        }
        return sub;
    }
    /**
     * 将Advertisement类型list长度截取，符合前端需要
     * @param adv List
     * @return List
     */
    public List<Advertisement> subListAdv(List<Advertisement> adv) {
        List<Advertisement> sub = new ArrayList<Advertisement>();
        if (adv.size() >= LIST_SIZE) {
            for (int i = 0; i < LIST_SIZE; i++) {
                sub.add(adv.get(i));
            }
        } else {
            sub = adv;
        }
        return sub;
    }
    /**
     * 按照wxid和isview得到未查看的赞踩数目
     * @param wxid String
     * @param isView int
     * @return int
     */
    public int searchChoiceToMeNum(String wxid, int isView) {
        return userDao.searchChoiceToMeNumImpl(wxid, isView);
    }

    /**
     * 按照wxid和isview得到未查看的评论数目
     * @param wxid String
     * @param isView int
     * @return int
     */
    public int commentToMeNum(String wxid, int isView) {
        return userDao.commentToMeNumImpl(wxid, isView);
    }
    /**
     * 点赞与点踩
     * @param pid int
     * @param wxid String
     * @param type int
     * @return int
     */
    public int makeChoice(String wxid, int pid, int type) {
        int num = 0;
        Post post = userDao.getPost(pid);
        Choice choice = null;
        if (type == 0) {
            choice = userDao.getChoice(wxid, pid);
            if (choice != null) {
                num = post.getCriticismNum();
            } else {
                choice = this.insertChoice(wxid, pid, type);
                userDao.insertChoice(choice);
                userDao.addCriticismNum(post.getCriticismNum() + 1, pid);
                num = post.getCriticismNum() + 1;
            }
        } else {
            choice = userDao.getChoice(wxid, pid);
            if (choice != null) {
                num = post.getPraiseNum();
            } else {
                choice = this.insertChoice(wxid, pid, type);
                userDao.insertChoice(choice);
                userDao.addPraisenum(post.getPraiseNum() + 1, pid);
                num = post.getPraiseNum() + 1;
            }
        }
        return num;
    }
    /**
     * 创建一个Choice对象
     * @param pid int
     * @param wxid String
     * @param type int
     * @return Choice
     */
    public Choice insertChoice(String wxid, int pid, int type) {
        Choice choice = new Choice();
        Timestamp d = new Timestamp(System.currentTimeMillis());
        String time = d.toString();
        int chid = userDao.getMaxChid() + 1;
        choice.setChid(chid);
        choice.setPid(pid);
        choice.setWxid(wxid);
        choice.setIsView(0);
        choice.setChoiceTime(time);
        choice.setType(type);
        return choice;
    }
    /**
     * @return Post
     * @param pid int
     */
    public Post getPost(int pid) {
        return userDao.getPost(pid);
    }
    /**
     * @return List
     * @param title String
     */
    public List<Post> searchVague(String title) {
        return postListService.getStackOverflowList(title, VAGUE);
    }
    /**
     * @param ref int
     * @param block String
     * @return List
     */
    public List<Post> searchHot(String block, int ref) {
        return postListService.getStackOverflowList(block, ref);
    }
    /**
     * 按照wxid将未读消息变为已读消息
     * @param wxid String
     * @param isview int
     */
    public void changeChoiceToMeIsView(String wxid, int isview) {
        userDao.changeChoiceToMeIsViewImpl(wxid, isview);
    }
    /**
     * 按照wxid将未读消息变为已读消息
     * @param wxid String
     * @param isview int
     */
    public void changeCommentToMeIsView(String wxid, int isview) {
        userDao.changeCommentToMeIsViewImpl(wxid, isview);
    }
    /**
     * 查询User信息
     * @param wxid String
     * @return User
     */
    public User searchUser(String wxid) {
        return userDao.searchUser(wxid);
    }
    /**
     * 查询StudentView信息
     * @param wxid String
     * @return StudentView
     */
    public StudentView searchStudent(String wxid) {
        return userDao.searchStudent(wxid);
    }
    /**
     * 查询TeacherView信息
     * @param wxid String
     * @return TeacherView
     */
    public TeacherView searchTeacher(String wxid) {
        return userDao.searchTeacher(wxid);
    }
    /**
     * 查询ParentsView信息
     * @param wxid String
     * @return ParentsView
     */
    public ParentsView searchParents(String wxid) {
        return userDao.searchParents(wxid);
    }
    /**
     * 修改Others信息
     * @param user User
     * @return int
     */
    public int updateOthers(User user) {
        userDao.deleteTeacher(user.getWxid());
        userDao.deleteStudent(user.getWxid());
        userDao.deleteParents(user.getWxid());
        return userDao.updateUser(user);
    }
    /**
     * 修改Student信息
     * @param user User
     * @param student Student
     * @return int
     */
    public int updateStudent(User user, Student student) {
        User oldUser = userDao.searchUser(user.getWxid());
        userDao.updateUser(user);
        if (oldUser.getRole() == user.getRole()) {
            return userDao.updateStudent(student);
        } else {
            userDao.deleteTeacher(oldUser.getWxid());
            userDao.deleteStudent(oldUser.getWxid());
            userDao.deleteParents(oldUser.getWxid());
            return userDao.insertStudent(student);
        }
    }
    /**
     * 修改Teacher信息
     * @param user User
     * @param teacher Teacher
     * @return int
     */
    public int updateTeacher(User user, Teacher teacher) {
        User oldUser = userDao.searchUser(user.getWxid());
        userDao.updateUser(user);
        if (oldUser.getRole() == user.getRole()) {
            return userDao.updateTeacher(teacher);
        } else {
            userDao.deleteTeacher(oldUser.getWxid());
            userDao.deleteStudent(oldUser.getWxid());
            userDao.deleteParents(oldUser.getWxid());
            return userDao.insertTeacher(teacher);
        }
    }
    /**
     * 修改Parents信息
     * @param user User
     * @param parents Parents
     * @return int
     */
    public int updateParents(User user, Parents parents) {
        User oldUser = userDao.searchUser(user.getWxid());
        userDao.updateUser(user);
        if (oldUser.getRole() == user.getRole()) {
            return userDao.updateParents(parents);
        } else {
            userDao.deleteTeacher(oldUser.getWxid());
            userDao.deleteStudent(oldUser.getWxid());
            userDao.deleteParents(oldUser.getWxid());
            return userDao.insertParents(parents);
        }
    }
    /**
     * @return List
     */
    public List<Advertisement> listAdv() {
        return userDao.listAdv();
    }
}


