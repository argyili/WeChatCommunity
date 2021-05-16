package com.hfut.community.demo.dao;

import com.hfut.community.demo.domain.Admin;
import com.hfut.community.demo.domain.Block;
import com.hfut.community.demo.domain.Post;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 */
@Mapper
@Repository
public interface AdminDao {
    /**
     * @return int
     * @param pid int
     */
    @Update("update post set refinement='1' where pid=#{pid}")
    int upgradeRefinement(int pid);
    /**
     * @return Post
     * @param pid int
     */
    @Select("select * from post where pid=#{pid}")
    Post isPost(int pid);
    /**
     * @return int
     * @param pid int
     */
    @Update("update post set forbidcomment='1' where pid=#{pid}")
    int forbidCommentPost(int pid);
    /**
     * @return list
     * @param title String
     */
    @Select("select * from post where title like #{title} or content like #{title}")// or title like #{title}
    List<Post> searchPost(String title);
    /**
     * @return list
     */
    @Select("select * from post")
    List<Post> searchAllPost();
    /**
     * @return list
     */
    @Select("select * from post order by postingtime desc")
    List<Post> searchByTime();
    /**
     * @return list
     */
    @Select("select * from post order by praisenum desc")
    List<Post> searchByPraisenum();
    /**
     * @return list
     */
    @Select("select * from post order by criticismnum desc")
    List<Post> searchByCriticismnum();
    /**
     * @return list
     */
    @Select("select * from post order by commentsnum  desc")
    List<Post> searchByCommentsnum();
    /**
     * @return list
     */
    @Select("select * from post order by eyesnum desc")
    List<Post> searchByEyesnum();
    /**
     * @return Admin
     * @param phonenum String
     */
    @Select("select * from post where phonenum=#{phonenum}")
    Admin login(String phonenum);
    /**
     * @param pid int
     * @return int
     */
    @Delete("delete from post where pid=#{pid}")
    int deletePost(int pid);
    /**
     * @param pid int
     * @return int
     */
    @Delete("delete from comment where pid=#{pid}")
    int deleteComment(int pid);
    /**
     * @param phonenum String
     * @return int
     */
    @Select("select * from admin where phonenum=#{phonenum}")
    Admin judgeIsExistImpl(String phonenum);
    /**
     * @return int
     * @param pid int
     */
    @Update("update post set fold='1' where pid=#{pid}")
    int foldPost(int pid);
    /**
     * @param block String
     * @return list
     */
    @Select("select * from post where block=#{block} and refinement='0'")
    List<Post> searchByBlock(@Param("block") String block);
    /**
     * @return list
     * @param phonenum String
     */
    @Select("select * from block where phonenum=#{phonenum}")
    List<Block> searchBlock(String phonenum);
    /**
     * @param block String
     * @return list
     */
    @Select("select * from post where block=#{block} and forbidcomment='0'")
    List<Post> searchByBlock2(@Param("block") String block);
    /**
     * @param block String
     * @return list
     */
    @Select("select * from post where block=#{block}")
    List<Post> searchByBlock3(@Param("block") String block);
    /**
     * @param block String
     * @return list
     */
    @Select("select * from post where block=#{block} and fold='0'")
    List<Post> searchByBlock4(@Param("block") String block);
    /**
     * @param pid int
     * @return int
     */
    @Delete("delete from choice where pid=#{pid}")
    int deleteChoice(@Param("pid") int pid);
}
