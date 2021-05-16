package com.hfut.community.demo.dao;
import com.hfut.community.demo.domain.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Mapper
@Repository
public interface UserDao {
    /**
     * @return int
     */
    @Select("select max(pid) from post")
    int getMaxId();
    /**
     * @return int
     */
    @Select("select max(cid) from comment")
    int getMaxCid();
    /**
     * @param comment Comment
     * @return int
     */
    @Insert("insert into comment(cid,wxid,pid,commentTime,commentContent,isView)"
            + " value (#{cid}, #{wxid}, #{pid},"
            + " #{commentTime}, #{commentContent},'0')")
    int insertComment(Comment comment);
    /**
     * @param post Post
     * @return int
     */
    @Insert("insert into post(pid,wxid,title,content,postingTime,block,"
            + "praiseNum,criticismNum,eyesNum,commentsNum,fold,"
            + "refinement,forbidComment ) "
            + "value (#{pid}, #{wxid}, #{title}, #{content}, #{postingTime},"
            + " #{block}, #{praiseNum}, #{criticismNum}, #{eyesNum} ,"
            + "#{commentsNum}, #{fold},#{refinement}, #{forbidComment}) ")
    int insertPost(Post post);
    /**
     * @return list
     */
    @Select("select * from post")
    List<Post> searchAllPost();
    /**
     * @return list
     */
    @Select("select * from post where refinement ='1'")
    List<Post> searchAllRefinementPost();
    /**
     * @param block String
     * @return list
     */
    @Select("select * from post where block=#{block}")
    List<Post> searchByBlock(@Param("block")String block);
    /**
     * @return list
     * @param wxid String
     */
    @Select("select * from post where wxid=#{wxid}")
    List<Post> searchByWxid(@Param("wxid")String wxid);
    /**
     * @return int
     */
    @Select("select max(pid) from post")
    int getPidComment();
    /**
     * @param wxid String
     * @return CommentByMyself
     */
    @Select("select * from commenttomeview "
            + "where wxid=#{wxid} order by commentTime desc")
    List<CommentToMe> commentToMe(@Param("wxid")String wxid);
    /**
     * @param wxid String
     * @return CommentByMyself
     */
    @Select("select * from commentbymyselfview "
            + "where wxid=#{wxid} order by commentTime desc")
    List<CommentByMyself> commentByMyself(@Param("wxid")String wxid);
    /**
     * @param cid int
     * @return String
     */
    @Select("select wxid from comment where cid=#{cid}")
    String getNickname(@Param("cid")int cid);
    /**
     * 按照版块查找精华帖
     * @param block String
     * @return list
     */
    @Select("select * from post where refinement ='1' and block=#{block}")
    List<Post> searchRefPostByBlock(@Param("block")String block);
    /**
     * 按照pid查找该帖子的全部内容
     * @param pid int
     * @return Post
     */
    @Select("select * from post where pid=#{pid}")
    Post searchPostByPid(@Param("pid")int pid);
    /**
     * 按照pid得到该帖子的全部评论
     * @param pid int
     * @return list
     */
    @Select("select * from comment where pid=#{pid}")
    List<Comment> searchCommentByPid(@Param("pid")int pid);
    /**
     * @param user User
     * @return int
     */
    @Insert("insert into user "
            + "values(#{wxid},#{nickname},#{role},#{region},#{phoneNum},'0')")
    int insertUser(User user);
    /**
     * @param student Student
     * @return int
     */
    @Insert("insert into student "
            + "values(#{wxid},#{school},#{topPrize},#{identification},#{img})")
    int insertStudent(Student student);

    /**
     * @param teacher Teacher
     * @return int
     */
    @Insert("insert into teacher values(#{wxid},#{identification},#{img})")
    int insertTeacher(Teacher teacher);

    /**
     * @param parents Parents
     * @return int
     */
    @Insert("insert into parents values(#{wxid},#{purpose})")
    int insertParents(Parents parents);
    /**
     * 按照wxid得到对自己帖子的点赞点踩并按照时间排序
     * @param toWxid String
     * @return list
     */
    @Select("select * from choiceview where toWxid=#{toWxid} "
            + "order by choiceTime desc")
    List<ChoiceView> searchChoiceToMe(@Param("toWxid")String toWxid);
    /**
     * 按照版块查询全国，是否加精，综合排序
     * @param refinement int
     * @return list
     */
    @Select("select * from post where refinement>=#{refinement} and fold='0'")
    List<Post> searchByAllRefGenenal(@Param("refinement")int refinement);
    /**
     * 按照版块查询全国，是否加精，点赞排序
     * @param refinement int
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' "
            + "order by praiseNum desc")
    List<Post> searchByAllRefPraise(@Param("refinement")int refinement);
    /**
     * 按照版块查询全国，是否加精，点踩排序
     * @param refinement int
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' "
            + "order by criticismNum desc")
    List<Post> searchByAllRefCriticis(@Param("refinement")int refinement);
    /**
     * 按照版块查询全国，是否加精，评论排序
     * @param refinement int
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' "
            + "order by commentsNum desc")
    List<Post> searchByAllRefComment(@Param("refinement")int refinement);
    /**
     * 按照版块查询版块，是否加精，综合排序
     * @param refinement int
     * @param block String
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' and block=#{block}")
    List<Post> searchByBlockRef(
            @Param("refinement")int refinement, @Param("block")String block);
    /**
     * 按照版块查询全国，是否加精，点赞排序
     * @param refinement int
     * @param block String
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' and block=#{block} "
            + "order by praiseNum desc")
    List<Post> searchByBlockRefPraise(
            @Param("refinement")int refinement, @Param("block")String block);
    /**
     * 按照版块查询全国，是否加精，点踩排序
     * @param refinement int
     * @param block String
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' and block=#{block} "
            + "order by criticismNum desc")
    List<Post> searchByBlockRefCriticis(
            @Param("refinement")int refinement, @Param("block")String block);
    /**
     * 按照版块查询全国，是否加精，评论排序
     * @param refinement int
     * @param block String
     * @return list
     */
    @Select("select * from post "
            + "where refinement>=#{refinement} and fold='0' and block=#{block} "
            + "order by commentsNum desc")
    List<Post> searchByBlockRefComment(
            @Param("refinement")int refinement, @Param("block")String block);

    /**
     * 按照wxid和isView得到未查看的赞踩数目
     * @param wxid String
     * @param isView int
     * @return int
     */
    @Select("select count(*) from choiceview "
            + "where toWxid=#{param1} and isView=#{param2}")
    int searchChoiceToMeNumImpl(String wxid, int isView);

    /**
     * 按照wxid和isView得到未查看的赞踩数目
     * @param wxid String
     * @param isView int
     * @return int
     */
    @Select("select count(*) from commenttomeview "
            + "where wxid=#{param1} and isView=#{param2}")
    int commentToMeNumImpl(String wxid, int isView);
    /**
     * 点赞
     * @return int
     * @param pid int
     * @param praiseNum int
     */
    @Update("update post set praiseNum=#{praiseNum} where pid=#{pid}")
    int addPraisenum(@Param("praiseNum")int praiseNum, @Param("pid")int pid);
    /**
     * 点踩
     * @return int
     * @param pid int
     * @param criticismNum int
     */
    @Update("update post set criticismNum=#{criticismNum} where pid=#{pid}")
    int addCriticismNum(
            @Param("criticismNum")int criticismNum, @Param("pid")int pid);
    /**
     * @return Post
     * @param pid int
     */
    @Select("select * from post where pid=#{pid}")
    Post getPost(@Param("pid")int pid);
    /**
     * 根据微信号以及帖子号得到选择
     * @param wxid String
     * @param pid int
     * @return Choice
     */
    @Select("select * from choice where wxid=#{wxid} and pid=#{pid} ")
    Choice getChoice(@Param("wxid")String wxid, @Param("pid")int pid);
    /**
     * @param choice Choice
     * @return int
     */
    @Insert("insert into choice(chid,wxid,choiceTime,type,pid,isView) "
            + "value (#{chid}, #{wxid}, #{choiceTime}, "
            + "#{type}, #{pid}, #{isView})")
    int insertChoice(Choice choice);
    /**
     * @return int
     */
    @Select("select max(chid) from choice")
    int getMaxChid();
    /**
     * 模糊搜索
     * @return list
     * @param title String
     */
    @Select("select * from post "
            + "where title like #{title} or content like #{title} and fold='0'")
    List<Post> searchVague(@Param("title")String title);
    /**
     * 24小时热门
     * @return list
     */
    @Select("select * from post "
            + "where postingTime>now()-interval 24 hour and fold='0'")
    List<Post> searchHot();
    /**
     * 按照wxid将未读消息变为已读消息
     * @param wxid String
     * @param isView int
     */
    @Update("update choice set isView=#{isView} where wxid=#{wxid}")
    void changeChoiceToMeIsViewImpl(
            @Param("wxid")String wxid, @Param("isView") int isView);
    /**
     * 按照wxid将未读消息变为已读消息
     * @param wxid String
     * @param isView int
     */
    @Update("update comment set isView=#{isView} where wxid=#{wxid}")
    void changeCommentToMeIsViewImpl(
            @Param("wxid")String wxid, @Param("isView") int isView);
    /**
     * 根据微信号查找User信息
     * @param wxid String
     * @return User
     */
    @Select("select * from user where wxid=#{wxid} ")
    User searchUser(@Param("wxid")String wxid);
    /**
     * 根据微信号查找StudentView信息
     * @param wxid String
     * @return StudentView
     */
    @Select("select * from studentview where wxid=#{wxid} ")
    StudentView searchStudent(@Param("wxid")String wxid);
    /**
     * 根据微信号查找TeacherView信息
     * @param wxid String
     * @return TeacherView
     */
    @Select("select * from teacherview where wxid=#{wxid} ")
    TeacherView searchTeacher(@Param("wxid")String wxid);
    /**
     * 根据微信号查找ParentsView信息
     * @param wxid String
     * @return ParentsView
     */
    @Select("select * from parentsview where wxid=#{wxid} ")
    ParentsView searchParents(@Param("wxid")String wxid);
    /**
     * 在post表中添加评论的条数
     * @param commentsNum String
     * @param pid int
     */
    @Update("update post set commentsNum=#{commentsNum} where pid=#{pid}")
    int addCommentsNum(
            @Param("commentsNum")int commentsNum, @Param("pid")int pid);

    /**
     * @param user User
     * @return int
     */
    @Update("update user "
            + "set nickname=#{nickname},role=#{role},region=#{region},"
            + "phoneNum=#{phoneNum},forbidden=#{forbidden} where wxid=#{wxid}")
    int updateUser(User user);

    /**
     * @param student Student
     * @return int
     */
    @Update("update student "
            + "set school=#{school},topPrize=#{topPrize},"
            + "identification=#{identification},img=#{img} "
            + "where wxid=#{wxid}")
    int updateStudent(Student student);

    /**
     * @param teacher Teacher
     * @return int
     */
    @Update("update teacher "
            + "set identification=#{identification},img=#{img} "
            + "where wxid=#{wxid}")
    int updateTeacher(Teacher teacher);

    /**
     * @param parents Parents
     * @return int
     */
    @Update("update parents set purpose=#{purpose} where wxid=#{wxid}")
    int updateParents(Parents parents);
    /**
     * @param wxid String
     * @return int
     */
    @Delete("delete from student where wxid=#{wxid}")
    int deleteStudent(@Param("wxid")String wxid);
    /**
     * @param wxid String
     * @return int
     */
    @Delete("delete from parents where wxid=#{wxid}")
    int deleteParents(@Param("wxid")String wxid);
    /**
     * @param wxid String
     * @return int
     */
    @Delete("delete from teacher where wxid=#{wxid}")
    int deleteTeacher(@Param("wxid")String wxid);
    /**
     * 广告列表
     * @return list
     */
    @Select("select * from advertisement")
    List<Advertisement> listAdv();
}

